package proyecto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import proyecto.modelo.Registro;

public class TableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Registro> registros;
	private List<String> columnas;

	public TableModel() {
		registros = new ArrayList<Registro>();
		columnas = new ArrayList<>();
		columnas.add("Fecha");
		columnas.add("Horas");
		columnas.add("Tareas");

	}

	@Override
	public int getRowCount() {

		return registros.size();
	}

	@Override
	public int getColumnCount() {
		return columnas.size();
	}

	@Override
	public String getColumnName(int column) {
		return columnas.get(column);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		Registro filaRegistros = registros.get(rowIndex);
		String colum = columnas.get(columnIndex);
		if (colum.equals("Fecha")) {
			return formatter.format(filaRegistros.getFecha());
		}
		if (colum.equals("Horas")) {
			return filaRegistros.getNumHoras();
		}
		return filaRegistros.getDescripcion();
	}

	public void setReg(List<Registro> reg) {

		this.registros = reg;
	}

	public List<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<String> columnas) {
		this.columnas = columnas;
	}

}
