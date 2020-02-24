package practicaInforme;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 * Introduces el id del formulario y genera un pdf del mismo
 * @author Gonzalo
 *
 */
public class PanelCrearInforme extends JPanel {
	/**
	 * Campo que recoge el id de la factura
	 */
	private JTextField textField;
	/**
	 * Para crea la conexion
	 */
	private Connection con;

	/**
	 * Creacion del panel
	 */
	public PanelCrearInforme() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblIdDeLa = new JLabel("ID de la factua a generar informe");
		add(lblIdDeLa);

		textField = new JTextField();
		add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("Generar informe");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generarFactura();

			}
		});
		add(btnNewButton);
	}

	/**
	 * Genera un informe en base a un numero de factura
	 */
	public void generarFactura() {
		// TODO Auto-generated method stub
		JFileChooser fc = new JFileChooser();

		int seleccion = fc.showSaveDialog(this);

		if (seleccion == JFileChooser.APPROVE_OPTION) {
			File fichero = fc.getSelectedFile();
			try {
				con = Conexion.getConnection();
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("id", Integer.valueOf(textField.getText()));
				JasperReport jr = (JasperReport) JRLoader.loadObject(PanelCrearInforme.class.getResource("/informes/proveedores.jasper"));
				JasperReport subreport = (JasperReport) JRLoader.loadObject(PanelCrearInforme.class.getResource("/informes/clientes.jasper"));
				parametros.put("iddocumento", subreport);		
				JasperPrint print2 = JasperFillManager.fillReport(jr, parametros, con);
				System.out.println("asdf");
				String ruta = fichero.toString();
				JasperExportManager.exportReportToPdfFile(print2, ruta);
				JOptionPane.showMessageDialog(null, "Fichero guardado");		
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error al guardar");
				e.printStackTrace();
			}

		}
	}

}
