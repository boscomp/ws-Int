package proyecto;
import javax.swing.JLabel;

import proyecto.modelo.Usuario;

import java.awt.Font;

public class BienvenidaView extends View{

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	public BienvenidaView(App appController, Usuario user) {
		super(appController);
		setLayout(null);
		
		JLabel lblHola = new JLabel("Hola, "+user.getNombre());
		lblHola.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblHola.setBounds(147, 116, 135, 42);
		add(lblHola);
		
	}

}
