package gr.daemon.squarerobin.gui;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {

	public NonEditableTableModel(final int rowCount, final int columnCount) {
		super(rowCount, columnCount);
	}

	@Override
	public boolean isCellEditable(final int row, final int column) {
		return false;
	}

}
