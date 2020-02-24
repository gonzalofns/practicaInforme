package practicaInforme;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Genera las tabla de las facturas y se puede filtar por cliente
 * @author Gonzalo
 *
 */
public class PanelFacturas extends JPanel {
	/**
	 * Conecxion con la bbdd
	 */
	private Connection con;
	/**
	 * Statemen para ejecutar la query
	 */
	private Statement stmt;
	/**
	 * Guarda el resultado de la query
	 */
	private ResultSet rs;
	/**
	 * Campo que recoge el id del cliente
	 */
	private JTextField textField;
	/**
	 * Tabla que contiene las facturas
	 */
	private JTable table;
	/**
	 * Aniade parametros a la query
	 */
	private PreparedStatement pt;

	/**
	 * Crea el panel
	 */
	public PanelFacturas() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel texto = new JLabel("Id del cliente");
		panel.add(texto);

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Consultar Facturas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().toString().equals("")) {
					cargarTabla();
					
				}else {
					generarFacturas();					
				}
			}
		});
		panel.add(btnNewButton);

		cargarTabla();

	}
	
	
	/**
	 * Carga la tabla de las facturas sin nungun filtro y ordenadas por el id de los clientres
	 */
	public void cargarTabla() {
		// TODO Auto-generated method stub
		
		eliminar();
		try {
			con = Conexion.getConnection();
			if (con == null)
				System.out.println("ERROOOOOOOOOOOOR!!!");
			
			stmt = con.createStatement();
			
			String sql = "SELECT * FROM INVOICE ORDER BY CUSTOMERID";
			
			rs = stmt.executeQuery(sql);
			
			// TableModel definition
			String[] tableColumnsName = { "ID FACTURA", "ID CLIENTE", "TOTAL" };
			DefaultTableModel aModel = (DefaultTableModel) table.getModel();
			aModel.setColumnIdentifiers(tableColumnsName);
			
			// Loop through the ResultSet and transfer in the Model
			java.sql.ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while (rs.next()) {
				Object[] objects = new Object[colNo];
				for (int i = 0; i < colNo; i++) {
					objects[i] = rs.getObject(i + 1);
				}
				aModel.addRow(objects);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	/**
	 * Carga la tabla de las facturas del cliente que le indicas por el textField
	 */
	public void generarFacturas() {
		try {
			con = Conexion.getConnection();

			eliminar();
			String sql = "SELECT * FROM invoice WHERE customerid = ?";

			pt = con.prepareStatement(sql);

			pt.setString(1, textField.getText().toString());

			ResultSet rs = pt.executeQuery();
			String[] tableColumnsName = { "ID", "CUSTOMERID", "TOTAL" };
			DefaultTableModel aModel = (DefaultTableModel) table.getModel();
			aModel.setColumnIdentifiers(tableColumnsName);
			ResultSetMetaData rsmd = rs.getMetaData();
			int colNo = rsmd.getColumnCount();
			while (rs.next()) {
				Object[] objects = new Object[colNo];
				for (int i = 0; i < colNo; i++) {
					objects[i] = rs.getObject(i + 1);
				}
				aModel.addRow(objects);
			}
			table.setModel(aModel);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**
	 * Limpia el contenido de una tabla para cargarlos de nuevo
	 */
	public void eliminar() {
		DefaultTableModel tb = (DefaultTableModel) table.getModel();
		int a = table.getRowCount() - 1;
		for (int i = a; i >= 0; i--) {
			tb.removeRow(tb.getRowCount() - 1);
		}
	}

}
