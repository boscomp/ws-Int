
package proyecto;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import proyecto.modelo.Fecha;
import proyecto.modelo.Registro;
import proyecto.modelo.Usuario;
import proyecto.service.AutenticarException;
import proyecto.service.FCTException;
import proyecto.service.FechaService;
import proyecto.service.RegistroService;
import proyecto.service.UsuarioService;

public class App {

	private JFrame frame;
	private JMenuBar menu;
	private LoginView loginView;
	private AltaUserView altaUV;
	private BienvenidaView bienvenidaV;
	private NuevoRegistroView nuevoRegistroV;
	private ConsultarRegistroView consultarRegistroV;

	private Usuario usuarioConectado; // set usuarioCon en el login y metodo get en el resto

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();

		frame.setBounds(100, 100, 550, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		altaUV = new AltaUserView(this);

		menu = new JMenuBar();
		frame.setJMenuBar(menu);

		JMenu menuApp = new JMenu("App");
		menu.add(menuApp);

		JMenuItem menuItemCerrar = new JMenuItem("Cerrar sesión");
		menuApp.add(menuItemCerrar);

		JMenuItem menuItemSalir = new JMenuItem("Salir");
		menuApp.add(menuItemSalir);

		JMenu menuReg = new JMenu("Registros");
		menu.add(menuReg);

		JMenuItem menuItemNuevoReg = new JMenuItem("Crear nuevo registro");
		menuReg.add(menuItemNuevoReg);

		JMenuItem menuItemConsultarReg = new JMenuItem("Consultar registros");
		menuReg.add(menuItemConsultarReg);

		irALoginView();
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(menuItemNuevoReg)) {
					irANuevoRegistro();
				}
				if (e.getSource().equals(menuItemConsultarReg)) {
					irAConsultarRegistro();
				}
				if (e.getSource().equals(menuItemCerrar)) {
					irALoginView();
					

				}
				if (e.getSource().equals(menuItemSalir)) {
					salirApp();
				}

			}
		};
		menuItemSalir.addActionListener(al);
		menuItemCerrar.addActionListener(al);
		menuItemConsultarReg.addActionListener(al);
		menuItemNuevoReg.addActionListener(al);
	}

	public void irALoginView() {
		loginView = new LoginView(this, "", "");
		menu.setVisible(false);
		frame.setContentPane(loginView);
		frame.revalidate();

	}

	public void irAAltaUserV() {

		menu.setVisible(false);
		frame.setContentPane(altaUV);
		frame.revalidate();

	}

	public void irABienvenida() {
		menu.setVisible(true);
		bienvenidaV = new BienvenidaView(this, usuarioConectado);
		frame.setContentPane(bienvenidaV);
		frame.revalidate();
	}

	public void irANuevoRegistro() {
		menu.setVisible(true);
		nuevoRegistroV = new NuevoRegistroView(this);
		frame.setContentPane(nuevoRegistroV);
		frame.revalidate();
	}

	public void irAConsultarRegistro() {
		menu.setVisible(true);
		consultarRegistroV = new ConsultarRegistroView(this);
		frame.setContentPane(consultarRegistroV);
		frame.revalidate();
	}

	public Usuario getUsuarioConectado() {
		return usuarioConectado;
	}

	public void setUsuarioConectado(Usuario usuarioConectado) {
		this.usuarioConectado = usuarioConectado;

	}

//Salir de la App
	public void salirApp() {
		int i = JOptionPane.showConfirmDialog(null, "¿Estás seguro?", "Salir", JOptionPane.YES_NO_OPTION,
				JOptionPane.INFORMATION_MESSAGE);
		if (i == 0) {
			System.exit(0);
		}
	}

	public void login(String email, String pass) {
		UsuarioService us = new UsuarioService();
		Usuario user = new Usuario();
		try {
			user = us.login(email, pass);
		} catch (AutenticarException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		} catch (FCTException e1) {

			e1.printStackTrace();
		}

		// Si el usuario y pass coinciden entra en Bienvenida
		if (user.getEmail() != null) {
			setUsuarioConectado(user);

			irABienvenida();
		}
	}
	
	// Lo llamo desde AltaUserView
	public void altaUsuario(Usuario user) {
	
		UsuarioService us = new UsuarioService();
		try {
			//doy de alta al usuario y se lo paso a la app
			us.altaUsuario(user);
			setUsuarioConectado(user);
			irALoginView();
		} catch (FCTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
 // Devuelve la lista de fechas disponibles
	public List <String> fechas () {
		 DateTimeFormatter format1 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		FechaService fs = new FechaService();	
		List <Fecha> lista = fs.consultarFechasActuales();
		List <String> listaFechas = new ArrayList<>();
		for (Fecha fecha : lista) {
			listaFechas.add(format1.format(fecha.getFecha()));
		}
		
//		retrun format1.format(fs.consultarFechasActuales());
		
		return listaFechas;
		
		
	}
	
	//Creo un nuevo registro con los datos que recibo
	public void crearRegistro(Registro r) {
		RegistroService rs = new RegistroService();
		try {
			r.setIdUsuario(usuarioConectado.getIdUsuario());
			rs.crearRegistro(r);
			irANuevoRegistro();
		} catch (FCTException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "Ya existe un registro para este usuario en la fecha elegida", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		
	}
	//Consulta si hay registros en la fecha indicada con ese usuario
	public List <Registro> verificarRegistro (Usuario user) {
		RegistroService rs = new RegistroService();
		
		
			try {
				return rs.consultarRegistrosUsuario(user.getIdUsuario());
			} catch (FCTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		
	}
	
	public List <Registro> consultarRegistro() {
		RegistroService rs = new RegistroService();
		List <Registro> registros = new ArrayList<>();
		
		try {
			registros =rs.consultarRegistrosUsuario(usuarioConectado.getIdUsuario());
		} catch (FCTException e1) {
			JOptionPane.showMessageDialog(null, "Ya existe un registro para este usuario en la fecha elegida", "ERROR",
					JOptionPane.ERROR_MESSAGE);
		}
		return registros;
	}
}