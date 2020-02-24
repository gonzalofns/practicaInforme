package practicaInforme;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase con el conextor de la bbdd.
 * 
 * @author usertar
 *
 */
public class Conexion {
	/**
	 * Conector con la bbdd.
	 */
	private static String url = "jdbc:hsqldb:hsql://localhost/testdb";
	/**
	 * Driver de la bbdd.
	 */
	private static String driverName = "org.hsqldb.jdbcDriver";
	/**
	 * Usuario de la bbdd
	 */
	private static String username = "sa";
	/**
	 * Contraseña de la bbdd
	 */
	private static String password = "";
	/**
	 * Guarda la conexion para retornarla mediante el metodo
	 */
	private static Connection con;

	/**
	 * Crea una conexion a la bbdd
	 * @return conexion a la bbdd
	 */
	
	public static Connection getConnection() {
		try {
			Class.forName(driverName);
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException ex) {
				// log an exception. fro example:
				System.out.println("Failed to create the database connection.");
			}
		} catch (ClassNotFoundException ex) {
			// log an exception. for example:
			System.out.println("Driver not found.");
		}
		return con;
	}
}
