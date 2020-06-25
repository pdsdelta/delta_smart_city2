package gestion_borne.vue;


import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.MotionSensorDAO;
import gestion_borne.crud.TerminalDAO;
import motionSensor.MotionSensor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import java.awt.Font;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author anaelle Donfack
 */
public class GestionTerminalVue extends javax.swing.JFrame {

	/**
	 * Creates new form Gestion_des_Motion_Sensor
	 */
	private functionalityServer server;
	TerminalDAO data = new TerminalDAO(server);

	Statement stm;
	ResultSet Rs;
	DefaultTableModel model=new DefaultTableModel();
	public GestionTerminalVue() throws ClassNotFoundException, SQLException, UnknownHostException, IOException {
		initComponents();

		model.addColumn("numero");
		model.addColumn("longitude");
		model.addColumn("latitude");
		model.addColumn("Fonctionnel");
		model.addColumn("statut");

		List<Terminal> lt = data.getAll();
		data.startConnection("172.31.249.22", 2400);
		Terminal borne = null; 
		int col = 5;
		int lig = lt.size();

		Object [][] listeUtil = new Object [lig][col]; 
		for (int i = 0; i < lig; i++) {
			borne = lt.get(i);
			listeUtil[i][0] = borne.getNumero();
			listeUtil[i][1] = borne.getLongitude();
			listeUtil[i][2] = borne.getLatitude();
			listeUtil[i][3] = borne.isActive();
			listeUtil[i][4] = borne.getStatus();
		}

		tble.setModel(new javax.swing.table.DefaultTableModel(
				listeUtil,
				new String [] {
						"NUMERO", "LONGITUDE", "LATITUDE", "FONCTIONNEL", "STATUS"
				}
				) {
			boolean[] canEdit = new boolean [] {
					false, false, false, false,false
			};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		});
		
		JLabel jLabel8 = new JLabel("Statut");
		jLabel8.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jLabel8.setBounds(40, 286, 60, 17);
		getContentPane().add(jLabel8);
		
		txtsta = new JTextField();
		txtsta.setBounds(174, 285, 96, 19);
		getContentPane().add(txtsta);
		txtsta.setColumns(10);
		
		
		
		
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		jButton5 = new javax.swing.JButton();
		btnTrafic = new javax.swing.JButton();
		//jButton6 = new javax.swing.JButton();
		txtre = new javax.swing.JTextField();
		jLabel6 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tble = new javax.swing.JTable();
		jLabel2 = new javax.swing.JLabel();
		jLabel1 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel10 = new javax.swing.JLabel();
		txtbr = new javax.swing.JComboBox();
		txtpr = new javax.swing.JTextField();
		txtno = new javax.swing.JTextField();
		txtid = new javax.swing.JTextField();
		txtsta = new javax.swing.JTextField();
		jLabel7 = new javax.swing.JLabel();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenuItem1 = new javax.swing.JMenuItem();
		jMenuItem2 = new javax.swing.JMenuItem();
		jMenuItem3 = new javax.swing.JMenuItem();
		jMenuItem4 = new javax.swing.JMenuItem();
		jMenuItem5 = new javax.swing.JMenuItem();
		menuItem = new javax.swing.JMenuItem();
		jMenu2 = new javax.swing.JMenu();
		jMenuItem6 = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(770, 530));
		getContentPane().setLayout(null);

		jButton1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		//jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/supprimer.png"))); // NOI18N
		jButton1.setText("Supprimer");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		getContentPane().add(jButton1);
		jButton1.setBounds(180, 400, 143, 40);

		jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		//jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/ajouter.png"))); // NOI18N
		jButton2.setText("Ajouter");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		getContentPane().add(jButton2);
		jButton2.setBounds(40, 350, 130, 40);

		jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		//jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/rechercher.png"))); // NOI18N
		jButton3.setText("recherche ");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		getContentPane().add(jButton3);
		jButton3.setBounds(373, 350, 150, 40);

		jButton4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		//jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/modifier.png"))); // NOI18N
		jButton4.setText("actualiser");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jButton4ActionPerformed(evt);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		getContentPane().add(jButton4);
		jButton4.setBounds(180, 350, 140, 40);

		jButton5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		//jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/actualiser.png"))); // NOI18N
		jButton5.setText("modifier");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		getContentPane().add(jButton5);
		jButton5.setBounds(40, 400, 130, 40);
		
		btnTrafic.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnTrafic.setText("Trafic");
		btnTrafic.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					btnTraficActionPerformed(evt);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnTrafic.setBounds(380, 400, 150, 33);
		getContentPane().add(btnTrafic);

		/*jButton6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
		jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/nouveau.png"))); // NOI18N
		jButton6.setText("r�aliser par");
		jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				jButton6MouseClicked(evt);
			}
		});*/
		//getContentPane().add(jButton6);
		//jButton6.setBounds(90, 450, 160, 40);

		txtre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		txtre.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtreActionPerformed(evt);
			}
		});
		txtre.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				txtreKeyPressed(evt);
			}
		});
		getContentPane().add(txtre);
		txtre.setBounds(560, 380, 130, 30);

		jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
		jLabel6.setText("Bornes Retractables");
		getContentPane().add(jLabel6);
		jLabel6.setBounds(182, 10, 430, 70);
		
		//Terminal borne= this.data.find(3);
		//int voitureAdmis = borne.getCity().getNombreMaxVoiture();
		
		jLabel9.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jLabel9.setText("Voiture Admis : ");
		jLabel9.setBounds(40, 72, 230, 23);
		getContentPane().add(jLabel9);

		
		jLabel10.setFont(new Font("Times New Roman", Font.BOLD, 14));
		jLabel10.setText("Voitures Pr\u00E9sents :");
		jLabel10.setBounds(332, 72, 238, 28);
		getContentPane().add(jLabel10);
		
		tble.setModel(new javax.swing.table.DefaultTableModel(
				new Object [][] {
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null},
					{null, null, null, null, null}
				},
				new String [] {
						"NUMERO", "LONGITUDE", "LATITUDE", "FONCTIONNEL", "STATUS"
				}
				)
			

			
		);
		tble.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tbleMouseClicked(evt);
			}
		});
		
		jScrollPane1.setViewportView(tble);

		getContentPane().add(jScrollPane1);
		jScrollPane1.setBounds(340, 110, 406, 218);

		jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
		jLabel2.setText("Numero :");
		getContentPane().add(jLabel2);
		jLabel2.setBounds(40, 110, 60, 23);

		jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
		jLabel1.setText("Longitude :");
		getContentPane().add(jLabel1);
		jLabel1.setBounds(40, 150, 81, 17);

		jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
		jLabel3.setText("Latitude :");
		getContentPane().add(jLabel3);
		jLabel3.setBounds(40, 190, 81, 17);

		jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
		jLabel4.setText("Fonctionnel  :");
		getContentPane().add(jLabel4);
		jLabel4.setBounds(21, 240, 100, 17);

		txtbr.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "true", "false" }));
		getContentPane().add(txtbr);
		txtbr.setBounds(170, 240, 100, 22);

		txtpr.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		getContentPane().add(txtpr);
		txtpr.setBounds(170, 190, 100, 23);

		txtno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		getContentPane().add(txtno);
		txtno.setBounds(170, 150, 100, 23);

		txtid.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
		txtid.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtidActionPerformed(evt);
			}
		});
		getContentPane().add(txtid);
		txtid.setBounds(170, 110, 100, 23);

		//jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icone/wallpaper7.jpg"))); // NOI18N
		getContentPane().add(jLabel7);
		jLabel7.setBounds(0, 0, 760, 500);

		
		menuItem = new JMenuItem("Voiture Admis");
		menuItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					SetMaxVoiture(evt);
				
				} catch (ClassNotFoundException | SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jMenu1.add(menuItem);
		
		jMenu1.setText("File");

		jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
		jMenuItem1.setText("ajouter");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jMenuItem1ActionPerformed(evt);
					
				} catch (SQLException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jMenu1.add(jMenuItem1);

		jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.ALT_MASK));
		jMenuItem2.setText("modifier");
		jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem2ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem2);

		jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
		jMenuItem3.setText("supprimer");
		jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem3ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem3);

		jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F5, 0));
		jMenuItem4.setText("actualiser");
		jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				try {
					jMenuItem4ActionPerformed(evt);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		jMenu1.add(jMenuItem4);

		jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
		jMenuItem5.setText("rechercher");
		jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jMenuItem5ActionPerformed(evt);
			}
		});
		jMenu1.add(jMenuItem5);

		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");

		jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
		jMenuItem6.setText("realiser par");
		jMenu2.add(jMenuItem6);

		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		pack();
	}// </editor-fold>//GEN-END:initComponents
	private void deplace(int i){
		try {
			this.txtid.setText(this.model.getValueAt(i, 0).toString());
			this.txtno.setText(this.model.getValueAt(i,1).toString());
			this.txtpr.setText(this.model.getValueAt(i,2).toString());
			this.txtbr.setSelectedItem(this.model.getValueAt(i,3).toString());

		}catch (Exception e){System.err.println(e);
		JOptionPane.showMessageDialog(null,"erreur de deplacement"+e.getLocalizedMessage());}
	}

	private void afficher() throws UnknownHostException, IOException, SQLException{	
		List<Terminal> lt = data.getAll();
		data.startConnection("172.31.249.22", 2400);
		Terminal borne = null; 
		int col = 5;
		int lig = lt.size();

		Object [][] listeUtil = new Object [lig][col]; 
		for (int i = 0; i < lig; i++) {
			borne = lt.get(i);
			listeUtil[i][0] = borne.getNumero();
			listeUtil[i][1] = borne.getLongitude();
			listeUtil[i][2] = borne.getLatitude();
			listeUtil[i][3] = borne.isActive();
			listeUtil[i][4] = borne.getStatus();
		}

		tble.setModel(new javax.swing.table.DefaultTableModel(
				listeUtil,
				new String [] {
						"NUMERO", "LONGITUDE", "LATITUDE", "FONCTIONNEL", "STATUS"
				}
				) {
			boolean[] canEdit = new boolean [] {
					false, false, false, false,false
			};

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit [columnIndex];
			}
		});
	}
	private void txtidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_txtidActionPerformed

	private void txtreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtreActionPerformed
		// TODO add your handling code here:
	}//GEN-LAST:event_txtreActionPerformed

	private void tbleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbleMouseClicked
		try{
			int i=this.tble.getSelectedRow();deplace(i);
		}catch(Exception e){JOptionPane.showMessageDialog(null,"erreur de deplacement "+e.getLocalizedMessage());}
	}//GEN-LAST:event_tbleMouseClicked

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
		Terminal captor = new Terminal();
		captor.setLongitude(Integer.parseInt(txtno.getText()));
		captor.setLatitude(Integer.parseInt(txtpr.getText()));
		captor.setStatus(Integer.parseInt(txtsta.getText()));
		captor.setActive(Boolean.parseBoolean(txtbr.getSelectedItem().toString()));

		try{
			String result=this.data.create(captor);
			this.data.startConnection("172.31.249.22", 2400);
			if((result!=null)) {
				JOptionPane.showMessageDialog(null,"l'objet est bien ajoute");
				txtno.setText("");
				afficher();
			}else {
				JOptionPane.showMessageDialog(null,"Ajout Echoue , Veuillez Recommencer");
				txtno.setText("");

			}




		}catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage());}
	}//GEN-LAST:event_jButton2ActionPerformed

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) throws UnknownHostException, IOException, SQLException {//GEN-FIRST:event_jButton4ActionPerformed
		afficher();
	}//GEN-LAST:event_jButton4ActionPerformed

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
		try { 
			if (JOptionPane.showConfirmDialog (null,"confirmer la modification","modification",
					JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				Terminal captor = new Terminal();
				captor.setLongitude(Integer.parseInt(txtno.getText()));
				captor.setLatitude(Integer.parseInt(txtpr.getText()));
				captor.setNumero(Integer.parseInt(txtid.getText()));
				captor.setActive(Boolean.parseBoolean(txtbr.getSelectedItem().toString()));

				Boolean result=this.data.update(captor);
				this.data.startConnection("172.31.249.22", 2400);


				afficher();

			} 
		} catch (Exception e){JOptionPane.showMessageDialog(null,"erreur de modifier !!!!!!!"+e.getMessage());
		System.err.println(e);}
	}//GEN-LAST:event_jButton5ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
		try {
			if(JOptionPane.showConfirmDialog(null,"attention vous voulez supprimer un detecteur"
					,"supprimer detecteur",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

				if(txtid.getText().length() != 0){

					int y = Integer.parseInt(txtid.getText());
					Terminal captor= this.data.find(y);
					this.data.delete(captor);
					this.data.startConnection("172.31.249.22", 2400);
					afficher();
				}
				else { JOptionPane.showMessageDialog(null,"veuillez SVP remplire le champ numero !");}

		}catch (Exception e){JOptionPane.showMessageDialog(null,"erreur de supprimer \n"+e.getMessage());} 


	}//GEN-LAST:event_jButton1ActionPerformed

	/*private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
		realiser a=new realiser();
		a.setVisible(true);
		// TODO add your handling code here:
	}//GEN-LAST:event_jButton6MouseClicked
	 */
	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
		try {
			model.setRowCount(0);// pour vider la list des etudient
			{
				Rs = stm.executeQuery("Select * From MotionSensor WHERE  = '"+txtre.getText()+"'");
			}while (Rs.next()){

				Object [] detecteur ={Rs.getInt(1),Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getInt(5)};
				model.addRow(detecteur);
			} if (model.getRowCount () == 0){JOptionPane.showMessageDialog(null,"il y a aucun etudient");

			} else{ int i=0;
			deplace(i);
			}

		}catch (Exception e) { System.err.println(e);
		JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}//GEN-LAST:event_jButton3ActionPerformed
	
	private void btnTraficActionPerformed(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException {//GEN-FIRST:event_jButton3ActionPerformed
		new TraficVue(this, true).setVisible(true);
	}//GEN-LAST:event_btnTraficActionPerformed
	
	private void SetMaxVoiture(java.awt.event.ActionEvent evt) throws ClassNotFoundException, SQLException, UnknownHostException, IOException {
		JOptionPane jop= new JOptionPane();
		String num = jop.showInputDialog(null,"Entrez le numero de voiture admis",JOptionPane.QUESTION_MESSAGE);
		int y = Integer.parseInt(num);
		
		this.data.SetNbrMaxVoiture(y);
		data.startConnection("172.31.249.22", 2400);
		this.jLabel9.setText("Voitures Admis : "+ y);
		this.repaint();
	}

	private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) throws SQLException, UnknownHostException, IOException {//GEN-FIRST:event_jMenuItem1ActionPerformed

		Terminal captor = new Terminal();
		captor.setLongitude(Integer.parseInt(txtid.getText()));
		captor.setLatitude(Integer.parseInt(txtno.getText()));

		try{
			String result=this.data.create(captor);
			this.data.startConnection("172.31.249.22", 2400);
			if((result!=null)) {
				JOptionPane.showMessageDialog(null,"la borne est bien ajout�e");
				txtno.setText("");

			}else {
				JOptionPane.showMessageDialog(null,"Ajout Echoue , Veuillez Recommencer");
				txtno.setText("");

			}
			afficher();


		}catch(Exception ex){JOptionPane.showMessageDialog(null,"l id est d�ja attribu� pour une borne d�ja existe a notre base de donne� SVp change l id"+ex.getMessage());}
	}//GEN-LAST:event_jMenuItem1ActionPerformed

	private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
		try { 
			if (JOptionPane.showConfirmDialog (null,"confirmer la modification","modification",
					JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				Terminal captor = new Terminal();
				captor.setLongitude(Integer.parseInt(txtno.getText()));
				captor.setLatitude(Integer.parseInt(txtpr.getText()));
				captor.setNumero(Integer.parseInt(txtid.getText()));
				captor.setStatus(Integer.parseInt(txtsta.getText()));
				captor.setActive(Boolean.parseBoolean(txtbr.getSelectedItem().toString()));

				Boolean result=this.data.update(captor);
				this.data.startConnection("172.31.249.22", 2400);


				afficher();

			}
		} catch (Exception e){JOptionPane.showMessageDialog(null,"erreur de modifier !!!!!!!"+e.getMessage());
		System.err.println(e);}
	}//GEN-LAST:event_jMenuItem2ActionPerformed

	private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
		try {
			if(JOptionPane.showConfirmDialog(null,"attention vous avez supprimer un etudient,est ce que tu et sure?"
					,"supprimer etudient",JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)

				if(txtid.getText().length() != 0){
					stm.executeUpdate("Delete From etudient where id = "+txtid.getText()); afficher();
				}
				else { JOptionPane.showMessageDialog(null,"veuillez SVP remplire le champ id !");}

		}catch (Exception e){JOptionPane.showMessageDialog(null,"erreur de supprimer \n"+e.getMessage());} 

	}//GEN-LAST:event_jMenuItem3ActionPerformed

	private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) throws UnknownHostException, IOException, SQLException {//GEN-FIRST:event_jMenuItem4ActionPerformed
		afficher();        // TODO add your handling code here:
	}//GEN-LAST:event_jMenuItem4ActionPerformed

	private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
		try {
			model.setRowCount(0);// pour vider la list des client
			List<Terminal> lt = this.data.findActive(Integer.parseInt(txtre.getText()));
			this.data.startConnection("172.31.249.22", 2400);
			Terminal captor = null; 
			int col = 5;
			int lig = lt.size();

			Object [][] listeUtil = new Object [lig][col]; 
			for (int i = 0; i < lig; i++) {
				captor = lt.get(i);
				listeUtil[i][0] = captor.getNumero();
				listeUtil[i][1] = captor.getLongitude();
				listeUtil[i][2] = captor.getLatitude();
				listeUtil[i][3] = captor.isActive();
				listeUtil[i][4] = captor.getStatus();

			}
			tble.setModel(new javax.swing.table.DefaultTableModel(
					listeUtil,
					new String [] {
							"NUMERO", "LONGITUDE", "LATITUDE", "FONCTIONNEL","STATUS"
					}
					)); 
			if (model.getRowCount () == 0){JOptionPane.showMessageDialog(null,"il y a aucune Borne");

			} else{ int i=0;
			deplace(i);
			}

		}catch (Exception e) { System.err.println(e);
		JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}//GEN-LAST:event_jMenuItem5ActionPerformed

	private void txtreKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtreKeyPressed
		if (evt.getKeyCode()==KeyEvent.VK_ENTER)//tu click sur entr� et il va commencer
		{try {
			model.setRowCount(0);// pour vider la list des etudient
			{
				Rs = stm.executeQuery("Select * From etudient WHERE note = '"+txtre.getText()+"'");
			}while (Rs.next()){

				Object [] etudient ={Rs.getInt(1),Rs.getString(2),Rs.getString(3),Rs.getString(4),Rs.getInt(5)};
				model.addRow(etudient);
			} if (model.getRowCount () == 0){JOptionPane.showMessageDialog(null,"il y a aucun etudient");

			} else{ int i=0;
			deplace(i);
			}

		}catch (Exception e) { System.err.println(e);
		JOptionPane.showMessageDialog(null,e.getMessage());
		}}
	}//GEN-LAST:event_txtreKeyPressed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
		/* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(GestionMotionSensorVue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(GestionMotionSensorVue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(GestionMotionSensorVue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(GestionMotionSensorVue.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new GestionTerminalVue().setVisible(true);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JButton btnTrafic;
	//private javax.swing.JButton jButton6;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JMenu jMenu1;
	private javax.swing.JMenu jMenu2;
	private javax.swing.JMenuBar jMenuBar1;
	private javax.swing.JMenuItem jMenuItem1;
	private javax.swing.JMenuItem jMenuItem2;
	private javax.swing.JMenuItem jMenuItem3;
	private javax.swing.JMenuItem jMenuItem4;
	private javax.swing.JMenuItem jMenuItem5;
	private javax.swing.JMenuItem jMenuItem6;
	private javax.swing.JMenuItem menuItem;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTable tble;
	private javax.swing.JComboBox txtbr;
	private javax.swing.JTextField txtid;
	private javax.swing.JTextField txtno;
	private javax.swing.JTextField txtpr;
	private javax.swing.JTextField txtre;
	private JTextField txtsta;
}


