package com.skuan.tool;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel{

	private Class<?>[] columnTypes;
	private boolean[] columnEditable;

	public TableModel(Object[][] data, Object[] columnNames, Class<?>[] columnTypes, boolean[] columnEditable){
		super(data, columnNames);
		this.columnTypes = columnTypes;
		this.columnEditable = columnEditable;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return columnEditable[column];
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return columnTypes[columnIndex];
	}
}