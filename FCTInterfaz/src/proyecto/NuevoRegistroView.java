package proyecto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;

import javax.swing.JButton;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import proyecto.modelo.Registro;
import proyecto.modelo.Usuario;

import javax.swing.JSlider;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import javax.swing.JScrollPane;

public class NuevoRegistroView extends View {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private JSlider sliderHoras;
	private JComboBox<String> comboBoxFechas;
	private JScrollPane scrollTareas;
	private JTextArea textoTareas;

	public NuevoRegistroView(App appController) {
		super(appController);
		setLayout(null);

		JLabel lblFecha = new JLabel("Fecha:");
		lblFecha.setBounds(132, 37, 46, 14);
		add(lblFecha);

		JLabel lblHoras = new JLabel("Horas:");
		lblHoras.setBounds(132, 88, 46, 14);
		add(lblHoras);

		JLabel lblTareas = new JLabel("Tareas realizadas:");
		lblTareas.setBounds(132, 156, 111, 14);
		add(lblTareas);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(248, 297, 89, 23);
		add(btnAceptar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(351, 297, 89, 23);
		add(btnCancelar);
		JLabel lblNumHoras = new JLabel("");
		lblNumHoras.setBounds(370, 102, 46, 14);
		add(lblNumHoras);
		sliderHoras = new JSlider();
		sliderHoras.setMinorTickSpacing(1);
		sliderHoras.setMajorTickSpacing(1);
		sliderHoras.setToolTipText("");
		sliderHoras.setPaintTicks(true);
		sliderHoras.setValue(0);
		sliderHoras.setMaximum(16);
		sliderHoras.setBounds(124, 102, 236, 43);
		add(sliderHoras);
		sliderHoras.addChangeListener(new ChangeListener() {

			@Override

			public void stateChanged(ChangeEvent e) {

				BigDecimal valor = new BigDecimal(sliderHoras.getValue()).divide(new BigDecimal(2));

				lblNumHoras.setText(String.valueOf(valor));

			}

		});

		comboBoxFechas = new JComboBox<String>();
		comboBoxFechas.setBounds(132, 55, 128, 22);
		add(comboBoxFechas);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(329, 249, -166, -59);
		add(scrollPane);

		scrollTareas = new JScrollPane();
		scrollTareas.setBounds(142, 181, 229, 97);
		add(scrollTareas);

		textoTareas = new JTextArea();
		scrollTareas.setViewportView(textoTareas);
		
	
		// Añado fechas a comboBox
		addFechas();

		// Controlo el botón pulsado
		actionList();
	}

	private void addFechas() {
		// Recibo la lista de fechas

		List<String> fechas = appController.fechas();
		// Añado la lista al comboBox
		for (String fecha : fechas) {
			comboBoxFechas.addItem(fecha);
		}

	}

	private void actionList() {
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource().equals(btnCancelar)) {
					
					appController.irABienvenida();
				}
				if (e.getSource().equals(btnAceptar)) {
					// Llamo al metodo comprobar
					if (comprobar() == true) {
//						introducirDatos();

						Registro r = new Registro();
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
						LocalDate localDate = LocalDate.parse(comboBoxFechas.getSelectedItem().toString(), formatter);
						r.setFecha(localDate);
						r.setNumHoras(new BigDecimal(sliderHoras.getValue()).divide(new BigDecimal(2)));
						r.setDescripcion(textoTareas.getText());
						

						
							appController.crearRegistro(r);
							
						

					}
				}

			}
		};
		btnAceptar.addActionListener(al);
		btnCancelar.addActionListener(al);
	}

	// Compruebo que todos los campos estén rellenos
	private Boolean comprobar() {
		if (sliderHoras.getValue() == 0) {
			JOptionPane.showMessageDialog(null, "Las horas tienen que ser superiores a 0", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (textoTareas.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Las tareas realizadas no pueden estar vacías", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;

	}


}
