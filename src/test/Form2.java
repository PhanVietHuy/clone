package test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class Form2 extends JFrame {

	private JPanel contentPane;
	private JTextField txtUser;
	private JTextField txtPass;
	private JTextField txtUrl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form2 frame = new Form2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Form2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][][grow]", "[][][][][][]"));
		
		JLabel lblUsername = new JLabel("Username");
		contentPane.add(lblUsername, "cell 1 1,alignx trailing");
		
		txtUser = new JTextField();
		txtUser.setText("UserText");
		contentPane.add(txtUser, "cell 2 1,growx");
		txtUser.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "cell 1 2,alignx trailing");
		
		txtPass = new JTextField();
		txtPass.setText("Pass");
		contentPane.add(txtPass, "cell 2 2,growx");
		txtPass.setColumns(10);
		
		JLabel lblUrl = new JLabel("Url");
		contentPane.add(lblUrl, "cell 1 3,alignx trailing");
		
		txtUrl = new JTextField();
		txtUrl.setText("Url");
		contentPane.add(txtUrl, "cell 2 3,growx");
		txtUrl.setColumns(10);
		
		JLabel lblClass = new JLabel("Class");
		contentPane.add(lblClass, "cell 1 4,alignx trailing");
		
		JComboBox comboBox = new JComboBox();
		contentPane.add(comboBox, "cell 2 4,growx");
		
		JButton btnSubmit = new JButton("Submit");
		contentPane.add(btnSubmit, "cell 2 5");
	}

}
