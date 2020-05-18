package analyse_indicateur;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.image.*;
import javax.swing.ImageIcon;
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
import java.awt.Color;

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
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		this.setVisible(true);

		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(338, 86, 89, 23);
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
		contentPane.setLayout(null);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Compare");
		btnNewButton_1.setBounds(437, 86, 89, 23);
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
		getContentPane().add(btnNewButton_1);

		textFieldNum1 = new JTextField();
		textFieldNum1.setBounds(312, 24, 86, 20);
		getContentPane().add(textFieldNum1);
		textFieldNum1.setColumns(10);

		textFieldNum2 = new JTextField();
		textFieldNum2.setBounds(450, 24, 86, 20);
		getContentPane().add(textFieldNum2);
		textFieldNum2.setColumns(10);

		textFieldAns = new JTextField();
		textFieldAns.setBounds(371, 130, 219, 35);
		textFieldAns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Please Enter Valid Number");
			}
		});
		getContentPane().add(textFieldAns);
		textFieldAns.setColumns(10);

		JLabel lblNewLabel = new JLabel("  The Answer is :");
		lblNewLabel.setBounds(265, 131, 89, 32);
		lblNewLabel.setForeground(new Color(240, 255, 240));

		getContentPane().add(lblNewLabel);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 229, 374);
		panelMenu.setBackground(new Color(123, 104, 238));
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
		
		
	}

	public Page1() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 413);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(0, 0, 139));
		setContentPane(contentPane);
		this.setVisible(true);

		getContentPane().setLayout(null);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setBounds(338, 86, 89, 23);
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
		contentPane.setLayout(null);
		getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Compare");
		btnNewButton_1.setBounds(437, 86, 89, 23);
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
		getContentPane().add(btnNewButton_1);

		textFieldNum1 = new JTextField();
		textFieldNum1.setBounds(312, 24, 86, 20);
		getContentPane().add(textFieldNum1);
		textFieldNum1.setColumns(10);

		textFieldNum2 = new JTextField();
		textFieldNum2.setBounds(450, 24, 86, 20);
		getContentPane().add(textFieldNum2);
		textFieldNum2.setColumns(10);

		textFieldAns = new JTextField();
		textFieldAns.setBounds(371, 130, 219, 35);
		textFieldAns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Please Enter Valid Number");
			}
		});
		getContentPane().add(textFieldAns);
		textFieldAns.setColumns(10);

		JLabel lblNewLabel = new JLabel("  The Answer is :");
		lblNewLabel.setBounds(265, 131, 89, 32);
		lblNewLabel.setForeground(new Color(240, 255, 240));

		getContentPane().add(lblNewLabel);
		
		JPanel panelMenu = new JPanel();
		panelMenu.setBounds(0, 0, 229, 374);
		panelMenu.setBackground(new Color(123, 104, 238));
		contentPane.add(panelMenu);
		panelMenu.setLayout(null);
	}}