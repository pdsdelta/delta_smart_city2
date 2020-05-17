package analyse_indicateur;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Page1 extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNum1;
	private JTextField textFieldNum2;
	private JTextField textFieldAns;

	/**
	 * Launch the application.
	 */

	public void affichePage1() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					caracteristiquePage1();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * public static void main(String[] args) { EventQueue.invokeLater(new
	 * Runnable() { public void run() { try { Page1 frame = new Page1();
	 * frame.setVisible(true); } catch (Exception e) { e.printStackTrace(); } } });
	 * }
	 */
	/**
	 * Create the frame.
	 */
	public void caracteristiquePage1() {


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 413);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		this.setVisible(true);

		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int num1, num2, ans;
				try {
					num1 = Integer.parseInt(textFieldNum1.getText());
					num2 = Integer.parseInt(textFieldNum2.getText());
					ans = num1 + num2;
					textFieldAns.setText(Integer.toString(ans));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "please enter valid number");
				}
			}
		});
		btnNewButton.setBounds(56, 70, 89, 23);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Compare");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int num1, num2, ans;
				try {
					num1 = Integer.parseInt(textFieldNum1.getText());
					num2 = Integer.parseInt(textFieldNum2.getText());
					ans = num1 - num2;
					if (ans < 0) {
						textFieldAns.setText(num1 + " est plus petit que " + num2);
					} else if(ans>0) {
						textFieldAns.setText(num1 + " est plus grand que " + num2);
					}else {
						textFieldAns.setText("Il n y a pas d'amelioration ");
							
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "please enter valid number");
				}

			}
		});
		btnNewButton_1.setBounds(175, 70, 89, 23);
		getContentPane().add(btnNewButton_1);

		textFieldNum1 = new JTextField();
		textFieldNum1.setBounds(59, 24, 86, 20);
		getContentPane().add(textFieldNum1);
		textFieldNum1.setColumns(10);

		textFieldNum2 = new JTextField();
		textFieldNum2.setBounds(178, 24, 86, 20);
		getContentPane().add(textFieldNum2);
		textFieldNum2.setColumns(10);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		rdbtnNewRadioButton.setBounds(70, 231, 109, 23);
		getContentPane().add(rdbtnNewRadioButton);

		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("New radio button");
		rdbtnNewRadioButton_1.setBounds(206, 231, 109, 23);
		getContentPane().add(rdbtnNewRadioButton_1);

		textFieldAns = new JTextField();
		textFieldAns.setBounds(144, 120, 219, 35);
		getContentPane().add(textFieldAns);
		textFieldAns.setColumns(10);

		JLabel lblNewLabel = new JLabel("  the answer is ");
		lblNewLabel.setBounds(44, 121, 73, 32);

		getContentPane().add(lblNewLabel);

	}

	public Page1() {


	}
}
