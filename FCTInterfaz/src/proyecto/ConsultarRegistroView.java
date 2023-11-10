package proyecto;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.util.List;
import java.awt.event.ActionEvent;

import proyecto.modelo.Registro;
import proyecto.modelo.Usuario;

import javax.swing.JScrollPane;

public class ConsultarRegistroView extends View{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnRefrescar;
	private TableModel model;
	private JTable table;
	
	public ConsultarRegistroView(App appController) {
		super(appController);
		setLayout(null);
		model = new TableModel();
		
		btnRefrescar = new JButton("Refrescar");
		
		btnRefrescar.setBounds(274, 266, 110, 23);
		add(btnRefrescar);
		
		JScrollPane scrollReg = new JScrollPane();
		scrollReg.setBounds(104, 53, 264, 187);
		add(scrollReg);
		
		table = new JTable();
		scrollReg.setViewportView(table);
		table.setModel(model);
		actionBtn();
	}
	private void actionBtn() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List <Registro> registros= appController.consultarRegistro();
				
				
				model.setReg(registros);
				model.fireTableDataChanged();
				
			}
		};
		btnRefrescar.addActionListener(al);
	}
}
