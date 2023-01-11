package br.com.gabriel.sysagenda;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import br.com.gabriel.sysagenda.ui.UiPrincipal;

@SuppressWarnings("all")
public class SysAgenda {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		// Seta o tema
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Exibe a tela principal
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UiPrincipal window = new UiPrincipal();
					window.mostrar();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
