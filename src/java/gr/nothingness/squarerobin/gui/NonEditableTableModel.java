package gr.nothingness.squarerobin.gui;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	public NonEditableTableModel(int rowCount, int columnCount) {
		super(rowCount, columnCount);
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}