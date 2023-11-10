package proyecto;

import javax.swing.JTextField;



import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class LoginView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textoEmail;
	private JPasswordField passPass;
	private String textoPass;
	private JButton btnEntrar;
private JButton btnAcceso;
	private JButton btnSalir;

	public LoginView(App appController, String email, String pass) {
		super(appController);
		setLayout(null);

		textoEmail = new JTextField();
		textoEmail.setBounds(168, 120, 86, 20);
		add(textoEmail);
		textoEmail.setColumns(10);
		textoEmail.setText(email);

		passPass = new JPasswordField();
		passPass.setColumns(10);
		passPass.setBounds(168, 174, 86, 20);
		passPass.setText(pass);
		add(passPass);
		//Añado KeyListener para no tener que usar el ratón
		keyList();

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(168, 103, 46, 14);
		add(lblEmail);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(168, 159, 86, 14);
		add(lblPass);

		btnEntrar = new JButton("Entrar");
		btnEntrar.setBounds(208, 217, 89, 23);
		btnEntrar.setActionCommand("Entrar");
		add(btnEntrar);

		btnAcceso = new JButton("Solicitar acceso");
		btnAcceso.setBounds(208, 266, 133, 23);
		btnAcceso.setActionCommand("Acceso");
		add(btnAcceso);

		btnSalir = new JButton("Salir");
		btnSalir.setBounds(351, 266, 89, 23);
		btnSalir.setActionCommand("Salir");
		add(btnSalir);
		actionList();
	}
	
	public void keyList () {
	KeyListener kl = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()== KeyEvent.VK_ENTER) {
					//Transfiero foco a siguiente campo
					if (e.getSource().equals(textoEmail)) {
						textoEmail.transferFocus();
					}
					// Lo uso como botón Entrar
					if (e.getSource().equals(passPass)) {
						consultarUser();
					}
				}
				
			}
		};
		textoEmail.addKeyListener(kl);
		passPass.addKeyListener(kl);
	}
	// Con este actionlistner controlo los 3 botones del login
	public void actionList() {

		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String command = e.getActionCommand();
				// Método para consultar usuario al pulsar entrar
				if (command.contentEquals("Entrar")) {
					//El botón entrar es otro método para poder usarlo en el KeyListener
					consultarUser();

				}
				// Entro a la pantalla del alta
				if (command.contentEquals("Acceso")) {
					appController.irAAltaUserV();

				}
				
				//Salgo de la App
				if (e.getSource().equals(btnSalir)) {
					appController.salirApp();
				}
			}
		};
		btnEntrar.addActionListener(al);
		btnAcceso.addActionListener(al);
		btnSalir.addActionListener(al);
	
	}
	// Conecto con la BBDD, donde compruebo que los datos sean correctos
	public void consultarUser() {

		textoPass=new String (passPass.getPassword());

		// Compruebo que los campos tengan texto
		if (textoEmail.getText().isEmpty() || textoPass.isEmpty()) {
			
			JOptionPane.showMessageDialog(null, "El email y la contraseña no pueden estar vacíos",
	                "ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			appController.login(textoEmail.getText(), textoPass);
			
		}
	}

	

}
