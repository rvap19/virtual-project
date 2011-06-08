/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatSendPanel.java
 *
 * Created on 21-mag-2011, 12.36.12
 */

package virtualrisikoii;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
//import jxta.communication.Communicator;
import jxta.communication.FullCommunicator;
import services.ChatSender;
import services.GameController;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class ChatSendPanel extends javax.swing.JPanel {

    /** Creates new form ChatSendPanel */
    private List<String> players;
    private ChatSender sender;

    public ChatSendPanel() {
        Tavolo tavolo=Tavolo.getInstance();
        sender=GameController.getInstance();
        players=new ArrayList<String>();
        players.add(FullCommunicator.ChatAttributes.TO_ALL);

        Iterator<Giocatore> iter=tavolo.getGiocatori().iterator();

        while(iter.hasNext()){
            players.add(iter.next().getNome());
        }
        initComponents();
       // this.jComboBox1.setSelectedIndex(0);
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        allChatButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTextField1.setName("jTextField1"); // NOI18N

        jComboBox1.setName("jComboBox1"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${players}");
        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, jComboBox1);
        bindingGroup.addBinding(jComboBoxBinding);

        allChatButton.setText("invia");
        allChatButton.setName("allChatButton"); // NOI18N
        allChatButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allChatButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(allChatButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(allChatButton)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private void allChatButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allChatButtonActionPerformed
        // TODO add your handling code here:
        String destination=this.jComboBox1.getSelectedItem().toString();
        String message=this.jTextField1.getText();
        this.jTextField1.setText("");
        sender.sendMessage(null, destination, message);

      
    }//GEN-LAST:event_allChatButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allChatButton;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JTextField jTextField1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

}
