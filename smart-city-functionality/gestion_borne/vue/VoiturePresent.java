/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestion_borne.vue;


import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

import borne.Car;
import borne.Terminal;
import functionality_server.functionalityServer;
import gestion_borne.crud.CarDAO;
import gestion_borne.crud.DAO;
import gestion_borne.crud.MotionSensorDAO;
import gestion_borne.crud.TerminalDAO;
import motionSensor.MotionSensor;

/**
 *
 * @author Anaelle
 */
public class VoiturePresent extends javax.swing.JDialog {

    /**
     * Creates new form DListeUtilisateur
     */
	private functionalityServer server;
    public VoiturePresent(java.awt.Frame parent, boolean modal) throws SQLException, ClassNotFoundException, UnknownHostException, IOException {
        super(parent, modal);
        initComponents();
        
        CarDAO  data = new CarDAO (server); 
       
        List<Car> lt = data.getAll();
        data.startConnection("172.31.249.22", 2400);
        Car captor = null; 
        int col = 3;
        int lig = lt.size();
        
        Object [][] listeUtil = new Object [lig][col]; 
        for (int i = 0; i < lig; i++) {
            captor = lt.get(i);
            listeUtil[i][0] = captor.getIdCity();
            listeUtil[i][1] = captor.getNbcars();
            listeUtil[i][2] = captor.getDateof();
           
            
        }
       
        tbListeUtilisateur.setModel(new javax.swing.table.DefaultTableModel(
            listeUtil,
            new String [] {
                "IDCITY", "VOITURE PRESENTE", "DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
        };

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
    }
});
        setLocationRelativeTo(this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbListeUtilisateur = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tbListeUtilisateur.setFont(new java.awt.Font("Century Schoolbook", 1, 18)); // NOI18N
        tbListeUtilisateur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
                
            },
            new String [] {
            		"IDCITY", "VOITURE PRESENTE", "DATE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbListeUtilisateur);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        



    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbListeUtilisateur;
    // End of variables declaration                   
}