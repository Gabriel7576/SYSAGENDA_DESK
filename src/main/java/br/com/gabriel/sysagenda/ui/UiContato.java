package br.com.gabriel.sysagenda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import br.com.gabriel.sysagenda.business.ContatoBss;
import br.com.gabriel.sysagenda.domain.Contato;
import br.com.gabriel.sysagenda.util.Funcoes;

public class UiContato {

	private Contato contatoAtual;
	private List<Contato> contatos;
	private int index = 0;

	private ContatoBss contatoBss = new ContatoBss();

	private JPanel raiz;
	private JTextField txfCodigo;
	private JTextField txfNome;
	private JTextField txfTelefone;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnApagar;
	private JButton btnFechar;
	private JButton btnNovo;
	private JButton btnAlterar;
	private JButton btnUltimo;
	private JButton btnProximo;
	private JButton btnAnterior;
	private JButton btnPrimeiro;
	private boolean salvar;

	/**
	 * Create the dialog.
	 */
	public UiContato() {

		inicializar();

		carregaLista();

		exibirContato();
	}

	private void inicializar() {

		raiz = new JPanel();
		raiz.setPreferredSize(new Dimension(408, 342));
		raiz.setLayout(new BorderLayout(0, 0));

		JPanel pnlInferior = new JPanel();
		pnlInferior.setBorder(new LineBorder(new Color(0, 0, 0)));
		raiz.add(pnlInferior, BorderLayout.SOUTH);

		// vai para o primeiro
		btnPrimeiro = new JButton("<<");
		btnPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = 0;
				contatoAtual = contatos.get(index);
				exibirContato();
			}
		});
		pnlInferior.add(btnPrimeiro);

		// vai para o contato anterior
		btnAnterior = new JButton("<");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index != 0) {
					index = index - 1;
					contatoAtual = contatos.get(index);
					exibirContato();
				}
			}
		});
		pnlInferior.add(btnAnterior);

		// vai para o proximo contato
		btnProximo = new JButton(">");
		btnProximo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (index != contatoBss.getLista().size() - 1) {
					index = index + 1;
					contatoAtual = contatos.get(index);
					exibirContato();
				}
			}
		});
		pnlInferior.add(btnProximo);

		// vai para o ultimo contato
		btnUltimo = new JButton(">>");
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				index = contatoBss.getLista().size() - 1;
				contatoAtual = contatos.get(index);
				exibirContato();
				contatoBss.adiciona(contatoAtual);
			}
		});
		pnlInferior.add(btnUltimo);

		JPanel pnlDireito = new JPanel();
		pnlDireito.setBorder(new LineBorder(new Color(0, 0, 0)));
		raiz.add(pnlDireito, BorderLayout.EAST);
		pnlDireito.setLayout(new BoxLayout(pnlDireito, BoxLayout.Y_AXIS));

		// limpa os campos e desativa os botões desnecessários
		btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				txfCodigo.setText("");
				txfNome.setText("");
				txfTelefone.setText("");
				txfCodigo.setText(contatoBss.getCodContato() + 1 + "");
				desativaBtn();
				salvar = true;
			}
		});
		btnNovo.setMargin(new Insets(0, 11, 0, 10));
		pnlDireito.add(btnNovo);

		btnAlterar = new JButton("Alterar");
		btnAlterar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				desativaBtn();
				salvar = false;
			}
		});
		btnAlterar.setMargin(new Insets(0, 7, 0, 7));
		pnlDireito.add(btnAlterar);

		JLabel lblEspaco0 = new JLabel(" ");
		pnlDireito.add(lblEspaco0);

		// salva se o contato foi alterado ou é novo e ativa os botões necessários
		btnSalvar = new JButton("Salvar");
		btnSalvar.setEnabled(false);
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txfTelefone.getText().length() <= 11) {

					if (salvar) {
						adicionaContato();
						index = contatoBss.getLista().size() - 1;
					} else {
						alteraContato();
						carregaLista();
					}
					ativaBtn();

				} else {
					JButton btnOk = new JButton("OK");
					JOptionPane.showMessageDialog(btnOk, "telefone pode ter no maximo 11 caracteres");
				}
			}

		});
		btnSalvar.setMargin(new Insets(0, 9, 0, 9));
		btnNovo.setPreferredSize(new Dimension(50, 30));
		pnlDireito.add(btnSalvar);

		// cancela e retorna para a lista
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ativaBtn();
				exibirContato();
			}
		});
		btnCancelar.setEnabled(false);
		pnlDireito.add(btnCancelar);

		JLabel lblEspaco1 = new JLabel(" ");
		pnlDireito.add(lblEspaco1);

		btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Funcoes.showMensagem("Tem certeza, essa ação vai apagar todos as suas ligações ?")) {
					deletaContato(index);

					if (index != 0)
						index = index--;
					else
						index = index++;
					exibirContato();
				}
			}
		});
		btnApagar.setMargin(new Insets(0, 5, 0, 5));
		pnlDireito.add(btnApagar);

		JLabel lblEspaco2 = new JLabel(" ");
		pnlDireito.add(lblEspaco2);

		btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fecha(e);
			}
		});
		btnFechar.setMargin(new Insets(0, 5, 0, 5));
		pnlDireito.add(btnFechar);

		JPanel pnlCentral = new JPanel();
		pnlCentral.setBorder(new MatteBorder(1, 1, 0, 0, (Color) new Color(0, 0, 0)));
		raiz.add(pnlCentral, BorderLayout.CENTER);
		pnlCentral.setLayout(null);

		JLabel lblCodContato = new JLabel("Codigo");
		lblCodContato.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblCodContato.setBounds(6, 25, 50, 20);
		pnlCentral.add(lblCodContato);

		txfCodigo = new JTextField();
		txfCodigo.setEditable(false);
		txfCodigo.setBounds(6, 47, 66, 28);
		pnlCentral.add(txfCodigo);
		txfCodigo.setColumns(10);

		JLabel lblNomeContato = new JLabel("Nome");
		lblNomeContato.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblNomeContato.setBounds(6, 95, 50, 20);
		pnlCentral.add(lblNomeContato);

		txfNome = new JTextField();
		txfNome.setEditable(false);
		txfNome.setColumns(10);
		txfNome.setBounds(6, 117, 178, 28);
		pnlCentral.add(txfNome);

		JLabel lblTelefoneContato = new JLabel("Telefone");
		lblTelefoneContato.setFont(new Font("SansSerif", Font.PLAIN, 15));
		lblTelefoneContato.setBounds(6, 168, 66, 20);
		pnlCentral.add(lblTelefoneContato);

		txfTelefone = new JTextField();
		txfTelefone.setEditable(false);
		txfTelefone.setColumns(10);
		txfTelefone.setBounds(6, 190, 178, 28);
		pnlCentral.add(txfTelefone);
	}

	public void mostrar(JFrame frmSysagenda) {

		JDialog dialog = new JDialog(frmSysagenda, "Tela de Contato", true);
		dialog.setContentPane(raiz);
		dialog.pack();
		dialog.setLocationRelativeTo(frmSysagenda);
		dialog.setModal(true);
		dialog.setVisible(true);
	}

	private void exibirContato() {

		contatoAtual = contatos.get(index);
		txfCodigo.setText(contatoAtual.getCodContato() + "");
		txfNome.setText(contatoAtual.getNome());
		txfTelefone.setText(contatoAtual.getTelefone() + "");
	}

	private void carregaLista() {
		contatos = contatoBss.getLista();
	}

	// metodo para adicionar o contato
	private void adicionaContato() {

		Contato contato = dados();

		contatos.add(contato);

		contatoBss.adiciona(contato);

	}

	// metodo para alterar o contato
	private void alteraContato() {

		Contato contato = dados();
		contatoBss.alteraContato(contato);
	}

	// metodo para deletar o contato
	private void deletaContato(int index) {
		Contato contato = contatos.get(index);
		contatos.remove(index);
		contatoBss.deletaContato(contato);

	}

	// metodo para pegar os dados do contato
	private Contato dados() {

		Contato contato = new Contato();
		contato.setCodContato(Integer.parseInt(txfCodigo.getText()));
		contato.setNome(txfNome.getText());
		contato.setTelefone(Long.parseLong(txfTelefone.getText()));
		return contato;
	}

	// metodo para fechar a janela
	private void fecha(ActionEvent e) {
		JComponent comp = (JComponent) e.getSource();
		Window win = SwingUtilities.getWindowAncestor(comp);
		win.dispose();
	}

	private void desativaBtn() {

		txfNome.setEditable(true);
		txfTelefone.setEditable(true);
		btnSalvar.setEnabled(true);
		btnCancelar.setEnabled(true);
		btnApagar.setEnabled(false);
		btnAlterar.setEnabled(false);
		btnNovo.setEnabled(false);
		btnFechar.setEnabled(false);
		btnAnterior.setEnabled(false);
		btnPrimeiro.setEnabled(false);
		btnProximo.setEnabled(false);
		btnUltimo.setEnabled(false);
	}

	private void ativaBtn() {

		txfNome.setEditable(false);
		txfTelefone.setEditable(false);
		btnSalvar.setEnabled(false);
		btnCancelar.setEnabled(false);
		btnApagar.setEnabled(true);
		btnAlterar.setEnabled(true);
		btnNovo.setEnabled(true);
		btnFechar.setEnabled(true);
		btnAnterior.setEnabled(true);
		btnPrimeiro.setEnabled(true);
		btnProximo.setEnabled(true);
		btnUltimo.setEnabled(true);
	}
}
