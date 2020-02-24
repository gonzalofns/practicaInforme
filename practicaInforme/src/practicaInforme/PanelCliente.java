package practicaInforme;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/**
 * Contiene las tabla de todos los clientes.
 * @autor Gonzalo
 */
public class PanelCliente extends JPanel {
	/**
	 * Tabla que contiene los clientes
	 */
	private JTable table;
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
	 * Crea el panel con la conexion a la bbdd y carga los datos en la tabla
	 */
	public PanelCliente() {
		setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);

		JLabel lblNewLabel = new JLabel("Tabla de los clientes");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblNewLabel, BorderLayout.NORTH);

		try {
			con = Conexion.getConnection();
			if (con == null)
				System.out.println("ERROOOOOOOOOOOOR!!!");
			stmt = con.createStatement();
			String sql = "SELECT * FROM CUSTOMER";
			rs = stmt.executeQuery(sql);
			String[] tableColumnsName = { "ID", "FIRSTNAME", "LASTNAME", "STREET", "CITY" };
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
