package com.skuan.tool;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class SwingComponentsUtils {

    public static void tabSetColumnWidth(int width,int columIdx,JTable table){
        TableColumn column = table.getColumnModel().getColumn(columIdx);
		column.setPreferredWidth(width);
		column.setMinWidth(width);
		column.setMaxWidth(width);
		column.setWidth(width);
    }

    public static void tabSetColumnWidth(int width,TableColumn column){
		column.setPreferredWidth(width);
		column.setMinWidth(width);
		column.setMaxWidth(width);
		column.setWidth(width);
    }
    

    public static void tabInit(JTable table,String[] header,Class<?>[] columnTypes,boolean[] columnEditable){
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(true);
		table.setModel(new TableModel(null, header, columnTypes, columnEditable));
    }

	public  static void tabDataAdd(JTable table,Vector<Object> rowData){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(rowData);
	}

	//Failed to issue method call: Unit vncserver-virtual.service failed to load: No such file or directory.
	public static <T> void tabSetColumnComboBoxModel(JTable table,int columnIdx,T[] data){
		TableColumnModel cm = table.getColumnModel();
		cm.getColumn(columnIdx).setCellEditor( new DefaultCellEditor( new JComboBox<T>( new DefaultComboBoxModel<>( data ) ) ) );
	}

	public static void scrollPanelInit(JScrollPane scrollPanel, Component view, boolean vbaralways, boolean hbaralways){
		if(vbaralways)
			scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		else
			scrollPanel.setVerticalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		if(vbaralways)
			scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		else
			scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPanel.setViewportView(view);
	}

	public static void componentSetSize(Dimension size, JComponent component){
		component.setPreferredSize(size);
		component.setMaximumSize(size);
		component.setMinimumSize(size);
		component.setSize(size);
	}
}


