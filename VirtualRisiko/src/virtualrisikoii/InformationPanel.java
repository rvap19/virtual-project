/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * InformationPanel.java
 *
 * Created on 30-apr-2011, 12.31.39
 */

package virtualrisikoii;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import jxta.communication.Communicator;
import net.jxta.endpoint.Message;
import virtualrisikoii.listener.ChatListener;
import virtualrisikoii.listener.PassListener;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class InformationPanel extends javax.swing.JPanel implements ChatListener,PassListener{

    private Tavolo tavolo;
    private Communicator communicator;

    /** Creates new form InformationPanel */
    public InformationPanel() {
        initComponents();
        communicator=Communicator.getInstance();
        communicator.addChatListener(this);
        communicator.addPassListener(this);
        tavolo=Tavolo.getInstance();
        obiettivoLabel.setIcon(new ImageIcon("src/virtualrisikoii/resources/obiettivi/classical/"+tavolo.getMyGiocatore().getObiettivo().getCodice()+".jpg"));
       System.out.println("/src/virtualrisikoii/resources/obiettivi/classical/"+tavolo.getMyGiocatore().getObiettivo().getCodice()+".jpg");
        this.obiettivoLabel.setToolTipText(tavolo.getMyGiocatore().getObiettivo().toString());
        TitledBorder border= (TitledBorder) this.getBorder();
        border.setTitle("Benvenuto "+tavolo.getMyGiocatore().getNome());
        border= (TitledBorder) this.jPanel1.getBorder();
        border.setTitle("Informazioni obiettivo "+tavolo.getMyGiocatore().getNome());

    }

    public void setTavolo(Tavolo tavolo){
        this.tavolo=tavolo;
    }


    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        obiettivoLabel = new javax.swing.JLabel();
        passaTurnoButton = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        chatMessageField = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        inviaATuttiButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        gameInformationArea = new javax.swing.JTextArea();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(InformationPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, resourceMap.getString("Form.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, resourceMap.getFont("Form.border.titleFont"))); // NOI18N
        setName("Form"); // NOI18N

        jPanel1.setBackground(resourceMap.getColor("jPanel1.background")); // NOI18N
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel1.border.title"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        obiettivoLabel.setIcon(resourceMap.getIcon("obiettivoLabel.icon")); // NOI18N
        obiettivoLabel.setText(resourceMap.getString("obiettivoLabel.text")); // NOI18N
        obiettivoLabel.setName("obiettivoLabel"); // NOI18N

        passaTurnoButton.setText(resourceMap.getString("passaTurnoButton.text")); // NOI18N
        passaTurnoButton.setName("passaTurnoButton"); // NOI18N
        passaTurnoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passaTurnoButtonActionPerformed(evt);
            }
        });

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(obiettivoLabel)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(passaTurnoButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(obiettivoLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passaTurnoButton)
                    .addComponent(jButton3)))
        );

        jPanel4.setBackground(resourceMap.getColor("jPanel4.background")); // NOI18N
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel4.border.title"))); // NOI18N
        jPanel4.setName("jPanel4"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        chatTextArea.setColumns(20);
        chatTextArea.setFont(resourceMap.getFont("chatTextArea.font")); // NOI18N
        chatTextArea.setRows(5);
        chatTextArea.setName("chatTextArea"); // NOI18N
        jScrollPane2.setViewportView(chatTextArea);

        chatMessageField.setText(resourceMap.getString("chatMessageField.text")); // NOI18N
        chatMessageField.setName("chatMessageField"); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setEnabled(false);
        jButton1.setName("jButton1"); // NOI18N

        inviaATuttiButton.setText(resourceMap.getString("inviaATuttiButton.text")); // NOI18N
        inviaATuttiButton.setName("inviaATuttiButton"); // NOI18N
        inviaATuttiButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inviaATuttiButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(inviaATuttiButton))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(chatMessageField, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(chatMessageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inviaATuttiButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.setBackground(resourceMap.getColor("jPanel2.background")); // NOI18N
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        gameInformationArea.setColumns(20);
        gameInformationArea.setFont(resourceMap.getFont("gameInformationArea.font")); // NOI18N
        gameInformationArea.setRows(5);
        gameInformationArea.setName("gameInformationArea"); // NOI18N
        jScrollPane1.setViewportView(gameInformationArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void inviaATuttiButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inviaATuttiButtonActionPerformed
        // TODO add your handling code here:
        String chatMessage=this.tavolo.getMyGiocatore().getNome()+" > "+this.chatMessageField.getText()+"\n";
        try {
            Message msg = this.communicator.createChatMessage(Communicator.ChatAttributes.all, chatMessage);
            communicator.sendMessage(msg);
            
            this.chatMessageField.setText("");
            this.chatTextArea.append(chatMessage);
        } catch (Exception ex) {
            ex.printStackTrace();
        } 

    }//GEN-LAST:event_inviaATuttiButtonActionPerformed

    private void passaTurnoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passaTurnoButtonActionPerformed
        // TODO add your handling code here:
        if((!tavolo.isInizializzazione())&&tavolo.isTurnoMyGiocatore()){
            this.tavolo.passaTurno();
            Message msg=this.communicator.createPassesMessage(tavolo.getTurnoSuccessivo());
            try {
                        //Thread.sleep(3000);
                        this.communicator.sendMessage(msg);
                       

                    } catch (Exception  ex) {
                        ex.printStackTrace();
                    } 
            this.updateDatiGiocatore(tavolo.getGiocatoreCorrente());
        }
        
    }//GEN-LAST:event_passaTurnoButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if(!tavolo.isInizializzazione()){
            if(this.tavolo.recuperaCarta(tavolo.getGiocatoreCorrente()))
                this.updateDatiGiocatore(this.tavolo.getGiocatoreCorrente());
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed


    public void updateDatiGiocatore(Giocatore giocatore){
        TitledBorder border=(TitledBorder) this.getBorder();
        border.setTitle("Informazioni Giocatore "+giocatore.getNome());
        this.repaint();
    }



    public void appendActionInHistory(String action){
        this.gameInformationArea.append("\n"+action);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField chatMessageField;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JTextArea gameInformationArea;
    private javax.swing.JButton inviaATuttiButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel obiettivoLabel;
    private javax.swing.JButton passaTurnoButton;
    // End of variables declaration//GEN-END:variables

    public void updateChat(String to, String messageString) {

        this.chatTextArea.append(messageString);
    }

    public void updatePass(int turno_successivo) {


            int turnoSucc=tavolo.getTurnoSuccessivo();
            if(turnoSucc==0&&tavolo.getGiocatori().get(0).getNumeroTruppe()==0){
                tavolo.setInizializzazione(false);
            }

            this.tavolo.passaTurno();
            
            
            this.updateDatiGiocatore(tavolo.getGiocatoreCorrente());
            if(tavolo.isTurnoMyGiocatore()){

                this.appendActionInHistory(tavolo.getGiocatoreCorrente().getNome()+" ancora "+tavolo.getGiocatoreCorrente().getNumeroTruppe());
                System.out.println("ancora in inizializzazione "+tavolo.isInizializzazione());
            }
        

    }

    
}
