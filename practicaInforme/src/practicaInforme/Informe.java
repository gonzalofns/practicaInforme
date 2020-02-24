package practicaInforme;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;

/**
 * Clase principal que contiene todos paneles
 * @autor Gonzalo
 * 
 */

public class Informe extends JFrame {
	/**
	 * Panel principal
	 */
	private JPanel contentPane;
	/**
	 * Panel del card
	 */
	private JPanel panel;
	/**
	 * Panel con la tabla de los clientes
	 */
	private PanelCliente cliente;
	/**
	 * Panel con la tabla de las facturas
	 */
	private PanelFacturas facturas;	
	/**
	 * Panel que crea los informes
	 */
	private PanelCrearInforme informe;

	/*
	 * 
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Informe frame = new Informe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructor por defecto
	 */
	public Informe() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Colsultas");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Consultar clientes");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cambiarPanel("cliente");
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Consultar facturas");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cambiarPanel("facturas");
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmGenerarFactura = new JMenuItem("Generar factura");
		mntmGenerarFactura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cambiarPanel("informe");
			}
		});
		mnNewMenu.add(mntmGenerarFactura);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new CardLayout(0, 0));
		
		cliente = new PanelCliente();
		facturas = new PanelFacturas();
		informe = new PanelCrearInforme();
		
		panel.add(cliente,"cliente");
		panel.add(facturas,"facturas");
		panel.add(informe,"informe");
		
	}
	/**
	 * Cambia entre paneles del card
	 * @param nombre del panel
	 */
	public void cambiarPanel(String nombre) {
		// TODO Auto-generated method stub
		CardLayout cl = (CardLayout) panel.getLayout();
		cl.show(panel, nombre);
	}

}
