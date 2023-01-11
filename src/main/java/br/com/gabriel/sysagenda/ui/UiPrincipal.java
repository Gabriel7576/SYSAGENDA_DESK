package br.com.gabriel.sysagenda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class UiPrincipal {

	private JFrame frmSysagenda;

	/**
	 * Create the application.
	 */
	public UiPrincipal() {

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		frmSysagenda = new JFrame();
		frmSysagenda.setTitle("SysAgenda");
		frmSysagenda.setBounds(100, 100, 721, 474);
		frmSysagenda.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar mnbMenu = new JMenuBar();
		frmSysagenda.setJMenuBar(mnbMenu);

		JMenu mnmCadastro = new JMenu("Cadastros");
		mnbMenu.add(mnmCadastro);

		JMenuItem mniContato = new JMenuItem("Contato");
		mniContato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UiContato ui = new UiContato();
				ui.mostrar(frmSysagenda);
			}
		});
		mnmCadastro.add(mniContato);

		JMenuItem mniLigacao = new JMenuItem("Ligação");
		mniLigacao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				UiLigacao ui = new UiLigacao();
				ui.mostrar(frmSysagenda);
			}
		});
		mnmCadastro.add(mniLigacao);

		JSeparator separator = new JSeparator();
		mnmCadastro.add(separator);

		JMenuItem mniSair = new JMenuItem("Sair");
		mniSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnmCadastro.add(mniSair);

		JMenu mnmAjuda = new JMenu("Ajuda");
		mnbMenu.add(mnmAjuda);

		JMenuItem mniSobre = new JMenuItem("Sobre");
		mnmAjuda.add(mniSobre);

		JPanel pnlInferior = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) pnlInferior.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		pnlInferior.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmSysagenda.getContentPane().add(pnlInferior, BorderLayout.SOUTH);

		SwingUtilities.invokeLater(new Runnable() {

			public void run() {

				// Obtém a data e hora atuais
				LocalDateTime now = LocalDateTime.now();

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

				// Cria um SpinnerDateModel usando a data e hora atuais
				JLabel model = new JLabel(
						sdf.format(java.util.Date.from(now.atZone(java.time.ZoneId.systemDefault()).toInstant())));
				pnlInferior.add(model);

			}
		});

		JPanel pnlCentral = new JPanel();
		frmSysagenda.getContentPane().add(pnlCentral, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("SYSAGENDA");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		pnlCentral.add(lblNewLabel);
	}

	public void mostrar() {

		frmSysagenda.setVisible(true);
	}

}
