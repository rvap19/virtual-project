/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * UserActivactionFrame.java
 *
 * Created on 20-ott-2011, 0.23.32
 */
package web_service;


import javax.swing.JOptionPane;

/**
 *
 * @author root
 */
public class UserActivactionFrame extends javax.swing.JFrame {

    private RisikoStatisticalClient client;
    /** Creates new form UserActivactionFrame */
    public UserActivactionFrame() {
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

        passwordField1 = new javax.swing.JPasswordField();
        codiceText = new javax.swing.JTextField();
        usernameText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Attivazione utente");
        setResizable(false);

        passwordField1.setName("passwordField1"); // NOI18N

        codiceText.setName("codiceText"); // NOI18N

        usernameText.setName("usernameText"); // NOI18N

        jLabel1.setText("username");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("password");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("codice");
        jLabel3.setName("jLabel3"); // NOI18N

        jButton1.setText("invia");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usernameText, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                    .addComponent(passwordField1)
                    .addComponent(codiceText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codiceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         String username=this.usernameText.getText();
        if(username==null||username.equals("")){
           JOptionPane.showMessageDialog(this, "inserire username", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
                 
            return;
        }
        

        String pwd1=this.passwordField1.getText();
        if(pwd1==null||pwd1.equals("")){
            JOptionPane.showMessageDialog(this, "inserire password", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        
        String codice=this.codiceText.getText();
        if(codice==null||codice.equals("")){
            JOptionPane.showMessageDialog(this, "iinserire codice attivazione", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        int codiceValue=0;
        try{
            codiceValue=Integer.parseInt(codice);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(this, "codice attivazione non valido", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           return;
        }
        
                
        if(client.activateUser(username, pwd1,codiceValue )){
            JOptionPane.showMessageDialog(this, "utente attivato ... divertiti!!", "Virtual Risiko info", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "valori inseriti non validi, riprovare", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new UserActivactionFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField codiceText;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
