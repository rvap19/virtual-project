/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CompleteStatisticsFrame.java
 *
 * Created on 19-ott-2011, 14.11.27
 */
package web_service;

import java.util.List;
import xxx.Gameregistration;
import xxx.GameregistrationArray;

/**
 *
 * @author root
 */
public class CompleteStatisticsFrame extends javax.swing.JFrame {
    private RisikoStatisticalClient client;
    private Object[][] dataTable=null;

    /** Creates new form CompleteStatisticsFrame */
    public CompleteStatisticsFrame() {
        initComponents();
    }
    
    public CompleteStatisticsFrame(GameregistrationArray data){
        List<Gameregistration> list=data.getItem();
        int columns=2;
        int rows=list.size();
        dataTable=new Object[rows][columns];
        for(int i=0;i<rows;i++){
            Gameregistration reg=list.get(i);
            dataTable[i][0]=reg.getId().getUserUsername();
            dataTable[i][1]=reg.getPunteggio();

                    
        }
        initComponents();
        
    }

    public RisikoStatisticalClient getClient() {
        return client;
    }

    public void setClient(RisikoStatisticalClient client) {
        this.client = client;
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Classifica generale");

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        if(dataTable==null){
            dataTable=new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            };
        }
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            dataTable,
            new String [] {
                "player", "punteggio"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setName("jTable1"); // NOI18N
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        int row=this.jTable1.getSelectedRow();
        int column=this.jTable1.getSelectedColumn();
        System.out.println("Selezionato punto matrice M("+row+","+column+")");
        if(column==0){
            String username=this.dataTable[row][column].toString();
            GameregistrationArray newData=this.client.getPlayerStatistics(username);
            PlayerStatisticalFrame frame=new PlayerStatisticalFrame(newData, username);
            frame.setClient(client);
            frame.setVisible(true);
            dispose();
        }
    }//GEN-LAST:event_jTable1MouseClicked

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
