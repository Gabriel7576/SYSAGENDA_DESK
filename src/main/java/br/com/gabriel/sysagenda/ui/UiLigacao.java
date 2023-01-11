package br.com.gabriel.sysagenda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import javax.swing.text.MaskFormatter;

import br.com.gabriel.sysagenda.business.ContatoBss;
import br.com.gabriel.sysagenda.business.LigacaoBss;
import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.domain.Ligacao;
import br.com.gabriel.sysagenda.domain.LigacaoId;
import br.com.gabriel.sysagenda.util.Funcoes;

public class UiLigacao {

	private Ligacao ligacaoAtual;
	private List<Ligacao> ligacoes;
	private int index = 0;
	private boolean salva;

	private LigacaoBss ligacaoBss = new LigacaoBss();

	private JPanel raiz;
	private JTextField txfCodLigacao;
	private JFormattedTextField txfData;
	private JTextArea txaObs;
	private JSpinner spnHora;
	private SpinnerDateModel model;
	private JButton btnPrimeiro;
	private JButton btnAnterior;
	private JButton btnProximo;
	private JButton btnUltimo;
	private JButton btnNovo;
	private JButton btnAlterar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnApagar;
	private JButton btnFechar;
	private JComboBox<Contato> cbbContato;

	/**
	 * Create the dialog.
	 */
	public UiLigacao() {

		inicializar();

		carregaLista();

		exibirLigacao();

		ativaBtn();
	}

	private void inicializar() {

		raiz = new JPanel();
		raiz.setBorder(new MatteBorder(2, 1, 1, 1, (Color) new Color(0, 0, 0)));
		raiz.setPreferredSize(new Dimension(510, 444));
		raiz.setLayout(new BorderLayout(0, 0));

		JPanel pnlDireito = new JPanel();
		pnlDireito.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(0, 0, 0)));
		raiz.add(pnlDireito, BorderLayout.EAST);
		pnlDireito.setLayout(new BoxLayout(pnlDireito, BoxLayout.Y_AXIS));

		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					txfCodLigacao.setText("");
					txfData.setText("");
					spnHora.setValue(new SimpleDateFormat("HH:mm").parse("00:00"));
					txaObs.setText("");
					desativaBtn();
					salva = true;
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}

		});
		btnNovo.setMargin(new Insets(0, 15, 0, 15));
		pnlDireito.add(btnNovo);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desativaBtn();
				cbbContato.setEnabled(false);
				salva = false;
			}
		});
		btnAlterar.setMargin(new Insets(0, 11, 0, 11));
		pnlDireito.add(btnAlterar);

		JLabel lblNewLabel = new JLabel(" ");
		pnlDireito.add(lblNewLabel);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (verificaData(txfData.getText())) {
					// verifica se você quer adicionar um novo ou so salvar o alterado
					if (salva) {
						index = ligacoes.size();
						adicionaLigacao();
					} else {
						alteraLigacao();
						carregaLista();
					}

					ativaBtn();
				}
			}
		});
		btnSalvar.setMargin(new Insets(0, 12, 0, 12));
		pnlDireito.add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ativaBtn();
				exibirLigacao();
			}
		});
		btnCancelar.setMargin(new Insets(0, 5, 0, 5));
		pnlDireito.add(btnCancelar);

		JLabel lblNewLabel_1 = new JLabel(" ");
		pnlDireito.add(lblNewLabel_1);

		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Funcoes.showMensagem("Tem certeza??, essa porra não volta!!!")) {
					deletaLigacao();
					ligacoes.remove(index);

					if (index != 0)
						index = index-1;
					else
						index = index+1;
					exibirLigacao();
				}
			}
		});
		btnApagar.setMargin(new Insets(0, 10, 0, 10));
		pnlDireito.add(btnApagar);

		JLabel lblNewLabel_2 = new JLabel(" ");
		pnlDireito.add(lblNewLabel_2);

		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecha(e);
			}
		});
		btnFechar.setMargin(new Insets(0, 10, 0, 10));
		pnlDireito.add(btnFechar);

		JPanel pnlInferior = new JPanel();
		pnlInferior.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		raiz.add(pnlInferior, BorderLayout.SOUTH);

		// vai para o primeiro
		btnPrimeiro = new JButton("<<");
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = 0;
				exibirLigacao();
				ligacaoAtual = ligacoes.get(index);
			}
		});
		pnlInferior.add(btnPrimeiro);

		// vai para a ligação anterior
		btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index != 0) {
					index = index - 1;
					exibirLigacao();
					ligacaoAtual = ligacoes.get(index);
				}
			}
		});
		pnlInferior.add(btnAnterior);

		// vai para a proxima ligação
		btnProximo = new JButton(">");
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index != ligacaoBss.getLista().size() - 1) {
					index = index + 1;
					exibirLigacao();
					ligacaoAtual = ligacoes.get(index);
				}
			}
		});
		pnlInferior.add(btnProximo);

		// vai para a ultima ligação
		btnUltimo = new JButton(">>");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = ligacoes.size() - 1;
				exibirLigacao();
				ligacaoAtual = ligacoes.get(index);
			}
		});
		pnlInferior.add(btnUltimo);

		JPanel pnlCentral = new JPanel();
		pnlCentral.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		raiz.add(pnlCentral, BorderLayout.CENTER);
		pnlCentral.setLayout(null);

		JLabel lblContato = new JLabel("Contato");
		lblContato.setBounds(6, 25, 81, 16);
		pnlCentral.add(lblContato);

		JLabel lblCodligao = new JLabel("Cod_Ligação");
		lblCodligao.setBounds(6, 93, 81, 16);
		pnlCentral.add(lblCodligao);

		JLabel lblDataHora = new JLabel("Data_Hora");
		lblDataHora.setBounds(6, 161, 66, 16);
		pnlCentral.add(lblDataHora);

		JLabel lblObservao = new JLabel("Observação");
		lblObservao.setBounds(6, 229, 81, 16);
		pnlCentral.add(lblObservao);

		model = spnHora();
		spnHora = new JSpinner(model);
		spnHora.setEnabled(false);
		spnHora.setBounds(130, 189, 72, 28);
		JSpinner.DateEditor de_spnHora = new JSpinner.DateEditor(spnHora, "HH:mm");
		spnHora.setEditor(de_spnHora);
		pnlCentral.add(spnHora);

		txfCodLigacao = new JTextField();
		txfCodLigacao.setEditable(false);
		txfCodLigacao.setColumns(10);
		txfCodLigacao.setBounds(6, 121, 112, 28);
		pnlCentral.add(txfCodLigacao);

		txaObs = new JTextArea();
		txaObs.setLineWrap(true);
		txaObs.setBounds(6, 257, 411, 123);
		pnlCentral.add(txaObs);

		JScrollPane scrollPane = new JScrollPane(txaObs, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(6, 257, 411, 123);
		pnlCentral.add(scrollPane);

		txfData = new JFormattedTextField(mascara());
		txfData.setBounds(6, 189, 112, 28);
		pnlCentral.add(txfData);

		cbbContato = new JComboBox<>();
		cbbContato.setBounds(6, 53, 270, 26);
		preencheContatos();
		pnlCentral.add(cbbContato);

	}

	public void mostrar(JFrame frmSysagenda) {

		JDialog dialog = new JDialog(frmSysagenda, "Tela de Ligação", true);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(frmSysagenda);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	// exibi a ligação na tela
	private void exibirLigacao() {

		try {
			if (index <= ligacoes.size()) {

				ligacaoAtual = ligacoes.get(index);
				txfCodLigacao.setText(ligacaoAtual.getId().getCodLigacao() + "");
				String[] dataHoraSep = Funcoes.separa(Funcoes.dateToStr(ligacaoAtual.getDataHora()), " ");
				txfData.setText(dataHoraSep[0]);
				spnHora.setValue(new SimpleDateFormat("HH:mm").parse(dataHoraSep[1]));

				// vai dar um for passando pelo comboBox
				for (int i = 0; i < cbbContato.getItemCount(); i++) {
					Contato contato = cbbContato.getItemAt(i);

					// vai comparar se os cod_contatos são iguais
					if (ligacaoAtual.getId().getCodContato() == contato.getCodContato()) {
						cbbContato.setSelectedIndex(i);
						break;
					}
				}

				txaObs.setText(ligacaoAtual.getObs());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	// metodo para adicionar ligação
	private void adicionaLigacao() {

		Ligacao ligacao = dados();

		ligacoes.add(ligacao);

		ligacaoBss.adiciona(ligacao);
	}

	// metodo para alterar ligacao
	private void alteraLigacao() {

		LigacaoId id = new LigacaoId();
		Ligacao ligacao = new Ligacao();

		ligacao.setId(id);
		id.setCodLigacao(ligacaoAtual.getId().getCodLigacao());
		id.setCodContato(ligacaoAtual.getId().getCodContato());
		ligacao.setDataHora(Funcoes.strToDate(
				txfData.getText() + " " + new SimpleDateFormat("HH:mm").format(spnHora.getModel().getValue())));
		ligacao.setObs(txaObs.getText());

		ligacaoBss.alteraLigacao(ligacao);
	}

	// metodo para deleta a ligacao
	private void deletaLigacao() {

		ligacaoBss.deletaLigacao(ligacaoAtual);
	}

	// metodo para pegar os dados da ligação
	private Ligacao dados() {

		Ligacao ligacao = new Ligacao();

		LigacaoId id = new LigacaoId();
		ligacao.setId(id);

		Contato contato = (Contato) cbbContato.getSelectedItem();
		id.setCodContato(contato.getCodContato());
		id.setCodLigacao(ligacaoBss.getCodLigacao(id.getCodContato()) + 1);

		txfCodLigacao.setText(id.getCodLigacao()+"");
		ligacao.setDataHora(Funcoes.strToDate(
				txfData.getText() + " " + new SimpleDateFormat("HH:mm").format(spnHora.getModel().getValue())));
		ligacao.setObs(txaObs.getText());

		return ligacao;
	}

	// carrega a lista de Ligações
	private void carregaLista() {

		ligacoes = ligacaoBss.getLista();
	}

	// pega a lista de contato e passa para o comboBox
	private void preencheContatos() {

		List<Contato> contatos = new ContatoBss().getLista();
		for (Contato contato : contatos) {
			cbbContato.addItem(contato);
		}
	}

	private void desativaBtn() {

		spnHora.setEnabled(true);
		cbbContato.setEnabled(true);
		txfData.setEditable(true);
		spnHora.setEnabled(true);
		txaObs.setEditable(true);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnNovo.setEnabled(false);
		btnAlterar.setEnabled(false);
		btnApagar.setEnabled(false);
		btnFechar.setEnabled(false);
		btnPrimeiro.setEnabled(false);
		btnAnterior.setEnabled(false);
		btnProximo.setEnabled(false);
		btnUltimo.setEnabled(false);
	}

	private void ativaBtn() {

		spnHora.setEnabled(false);
		cbbContato.setEnabled(false);
		txfData.setEditable(false);
		txaObs.setEditable(false);
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnNovo.setEnabled(true);
		btnAlterar.setEnabled(true);
		btnApagar.setEnabled(true);
		btnFechar.setEnabled(true);
		btnPrimeiro.setEnabled(true);
		btnAnterior.setEnabled(true);
		btnProximo.setEnabled(true);
		btnUltimo.setEnabled(true);
	}

	// faz a ação de fechar a janela
	private void fecha(ActionEvent e) {

		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		win.dispose();
	}

	// verifica de a data está correta
	private boolean verificaData(String data) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			// não aceita datas erradas
			sdf.setLenient(false);
			// se a data não estiver errada ele converte para date
			sdf.parse(data);
			return true;
		} catch (ParseException e) {
			Component JButton = null;
			JOptionPane.showInternalConfirmDialog(JButton, "A data está errada corno, corrige agora!!");
			return false;
		}
	}

	// configurando a mascara do jformater
	private MaskFormatter mascara() {

		MaskFormatter dateMask = null;
		try {
			dateMask = new MaskFormatter("##/##/####");
			dateMask.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(raiz, this, "Erro ao criar o campo de data", JOptionPane.ERROR_MESSAGE);
		}
		return dateMask;
	}

	// configuração do spinner
	private SpinnerDateModel spnHora() {

		Date initDate = null;
		Date finalDate = null;
		try {
			initDate = new SimpleDateFormat("HH:mm").parse("00:00");
			finalDate = new SimpleDateFormat("HH:mm").parse("23:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SpinnerDateModel model = new SpinnerDateModel(initDate, initDate, finalDate, Calendar.MINUTE);
		return model;
	}
}
