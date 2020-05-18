package gestion_borne.vue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import functionality_server.functionalityServer;
import gestion_borne.crud.MotionSensorDAO;
import motionSensor.MotionSensor;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class MotionSensorAdd extends JDialog {
	public functionalityServer server;

	private final JPanel contentPanel = new JPanel();
	private JLabel resultat;
	private JTextField tfLongitude;
	private JTextField tfLatitude;

	private MotionSensorDAO data;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			MotionSensorAdd dialog = new MotionSensorAdd();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public MotionSensorAdd() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			tfLongitude = new JTextField();
			tfLongitude.setBounds(142, 16, 96, 19);
			contentPanel.add(tfLongitude);
			tfLongitude.setColumns(10);
		}

		tfLatitude = new JTextField();
		tfLatitude.setBounds(142, 64, 96, 19);
		contentPanel.add(tfLatitude);
		tfLatitude.setColumns(10);

		JLabel lblNewLabel = new JLabel("Entrez la longitude");
		lblNewLabel.setBounds(21, 16, 97, 19);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Entrez la latitude");
		lblNewLabel_1.setBounds(21, 60, 108, 26);
		contentPanel.add(lblNewLabel_1);
		{
			this.resultat = new JLabel("--------------------------------------------------------------------------------------------");
			this.resultat.setBounds(21, 121, 394, 13);
			contentPanel.add(this.resultat);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new java.awt.event.ActionListener(){
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						try {
							okButtonActionPerformed(evt);
						} catch (ClassNotFoundException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, ClassNotFoundException {                                           
		// TODO add your handling code here:
		this.data = new MotionSensorDAO(server);
		MotionSensor captor = new MotionSensor();
		captor.setLongitude(Integer.parseInt(tfLongitude.getText()));
		captor.setLatitude(Integer.parseInt(tfLatitude.getText()));
		
		boolean result=data.create(captor);
		if(result) {
			this.resultat.setText("Ajout Reussi");
		}else {
			this.resultat.setText("Ajout Echoué: veuillez recommencer");
		}
	}                       
}
