package sassy.asm.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class ConfigWindow {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigWindow window = new ConfigWindow();
					window.frame.setTitle("Sassy UML Generator!");
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ConfigWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(149, 12, 114, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(149, 43, 114, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(149, 85, 114, 19);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(149, 131, 114, 19);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(149, 177, 114, 19);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblInputDirectory = new JLabel("Input Directory");
		lblInputDirectory.setBounds(12, 14, 121, 15);
		frame.getContentPane().add(lblInputDirectory);
		
		JLabel lblInputClasses = new JLabel("Input Classes");
		lblInputClasses.setBounds(12, 45, 108, 15);
		frame.getContentPane().add(lblInputClasses);
		
		JLabel lblOutputDirectory = new JLabel("Output Directory");
		lblOutputDirectory.setBounds(12, 87, 121, 15);
		frame.getContentPane().add(lblOutputDirectory);
		
		JLabel lblDotPath = new JLabel("Dot Path");
		lblDotPath.setBounds(12, 133, 70, 15);
		frame.getContentPane().add(lblDotPath);
		
		JLabel lblNewLabel = new JLabel("Phases");
		lblNewLabel.setBounds(12, 179, 70, 15);
		frame.getContentPane().add(lblNewLabel);
	}
}
