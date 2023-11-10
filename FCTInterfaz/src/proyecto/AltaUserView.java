package proyecto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import proyecto.modelo.Usuario;


import javax.swing.JComboBox;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AltaUserView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textoEmail;
	private JPasswordField passPass;
	private String textoRepeat;
	private JPasswordField passRepeat;
	private String textoPass;
	private JTextField textoNom;
	private JTextField textoApe;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JComboBox<String> comboCiclo;

	public AltaUserView(App appController) {
		super(appController);
		setLayout(null);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(149, 12, 80, 14);
		add(lblEmail);

		JLabel lblPass = new JLabel("Contraseña:");
		lblPass.setBounds(149, 54, 80, 14);
		add(lblPass);

		JLabel lblRepeat = new JLabel("Repetir contraseña:");
		lblRepeat.setBounds(149, 93, 80, 14);
		add(lblRepeat);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(149, 137, 80, 14);
		add(lblNombre);

		JLabel lblApellido = new JLabel("Apellidos:");
		lblApellido.setBounds(149, 181, 80, 14);
		add(lblApellido);

		JLabel lblCiclo = new JLabel("Ciclo formativo:");
		lblCiclo.setBounds(149, 228, 80, 14);
		add(lblCiclo);

		textoEmail = new JTextField();
		textoEmail.setBounds(149, 26, 86, 20);
		add(textoEmail);
		textoEmail.setColumns(10);

		passPass = new JPasswordField();
		passPass.setColumns(10);
		passPass.setBounds(149, 68, 86, 20);
		add(passPass);
		
		

		passRepeat = new JPasswordField();
		passRepeat.setColumns(10);
		passRepeat.setBounds(149, 107, 86, 20);
		add(passRepeat);
		
		

		textoNom = new JTextField();
		textoNom.setColumns(10);
		textoNom.setBounds(149, 151, 86, 20);
		add(textoNom);

		textoApe = new JTextField();
		textoApe.setColumns(10);
		textoApe.setBounds(149, 195, 86, 20);
		add(textoApe);

		comboCiclo = new JComboBox<String>();
		comboCiclo.setBounds(149, 247, 86, 22);
		comboCiclo.addItem("DAM");
		comboCiclo.addItem("DAW");
		comboCiclo.setSelectedIndex(-1);
		add(comboCiclo);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(254, 292, 89, 23);
		add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(353, 292, 89, 23);
		add(btnCancelar);
		actionList();

	}

	// Controlo los botones
	public void actionList() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textoRepeat=new String (passRepeat.getPassword());
				textoPass=new String (passPass.getPassword());
				// Compruebo que botón se pulsa
				if (e.getSource().equals(btnAceptar)) {
					// Controlo que todos los campos se usen
					if (textoEmail.getText().isEmpty() || textoPass.isEmpty()
							|| textoRepeat.isEmpty() || textoNom.getText().isEmpty() || comboCiclo.getSelectedItem()==null) {
						JOptionPane.showMessageDialog(null, "Todos los campos deben estar completos", "ERROR",
								JOptionPane.ERROR_MESSAGE);
					} else {
						
						
						if (textoPass.equals(textoRepeat)) {
							Usuario user = new Usuario();
							user.setActivo(true);
							user.setEmail(textoEmail.getText());

							user.setContraseña(textoPass);
							user.setNombre(textoNom.getText());
							user.setApellidos(textoApe.getText());
							user.setCiclo(comboCiclo.getSelectedItem().toString());
							
							//Doy de alta al usuario
							appController.altaUsuario(user);
						}
						else {
							JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "ERROR",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					appController.irALoginView();
				}

			}
		};
		btnAceptar.addActionListener(al);
		btnCancelar.addActionListener(al);

	}
	}

