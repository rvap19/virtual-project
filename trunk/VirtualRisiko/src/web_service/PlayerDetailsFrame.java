/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlayerDetailsFrame.java
 *
 * Created on 16-giu-2011, 13.51.56
 */

package web_service;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import xxx.User;

/**
 *
 * @author root
 */
public class PlayerDetailsFrame extends javax.swing.JFrame {
    private RisikoStatisticalClient client;

    public PlayerDetailsFrame(RisikoStatisticalClient client){
        this();
        this.client=client;
    }

    /** Creates new form PlayerDetailsFrame */
    public PlayerDetailsFrame() {
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
        jTextArea1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        usernameText = new javax.swing.JTextField();
        emailText = new javax.swing.JTextField();
        nomeText = new javax.swing.JTextField();
        cognomeText = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel8 = new javax.swing.JLabel();
        passwordField1 = new javax.swing.JPasswordField();
        passwordField2 = new javax.swing.JPasswordField();

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registrazione Virtual Risiko");
        setResizable(false);

        jLabel1.setText("username");
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText("password");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("email");
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setText("nome");
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setText("cognome");
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText("data di nascita");
        jLabel6.setName("jLabel6"); // NOI18N

        usernameText.setName("usernameText"); // NOI18N
        usernameText.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                usernameTextPropertyChange(evt);
            }
        });

        emailText.setName("emailText"); // NOI18N

        nomeText.setName("nomeText"); // NOI18N

        cognomeText.setName("cognomeText"); // NOI18N

        jTextField7.setName("jTextField7"); // NOI18N

        jTextField8.setName("jTextField8"); // NOI18N

        jTextField9.setName("jTextField9"); // NOI18N

        okButton.setText("conferma");
        okButton.setName("okButton"); // NOI18N
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jSeparator1.setName("jSeparator1"); // NOI18N

        jLabel8.setText("Benvenuto  nel mondo Virtual Risiko !");
        jLabel8.setName("jLabel8"); // NOI18N

        passwordField1.setName("passwordField1"); // NOI18N

        passwordField2.setName("passwordField2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(42, 42, 42)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nomeText, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(emailText, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(usernameText, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addComponent(cognomeText, javax.swing.GroupLayout.DEFAULT_SIZE, 230, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                        .addComponent(passwordField2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(okButton)))
                        .addGap(83, 83, 83)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(passwordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(passwordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(nomeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cognomeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(okButton))
                .addGap(41, 41, 41))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameTextPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_usernameTextPropertyChange
        // TODO add your handling code here:
       
    }//GEN-LAST:event_usernameTextPropertyChange

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
        String username=this.usernameText.getText();
        if(username==null||username.equals("")){
           JOptionPane.showMessageDialog(this, "inserire username", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
                 
            return;
        }
        if(!this.client.checkUsername(username)){
            JOptionPane.showMessageDialog(this, "username non disponibile", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }

        String pwd1=this.passwordField1.getText();
        if(pwd1==null||pwd1.equals("")){
            JOptionPane.showMessageDialog(this, "inserire password", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        String pwd2=this.passwordField2.getText();
        if(pwd2==null||pwd2.equals("")){
            JOptionPane.showMessageDialog(this, "ripetere password", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        if(!pwd1.equals(pwd2)){
           JOptionPane.showMessageDialog(this, "le password non coincidono", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
           return;
        }

        String email=this.emailText.getText();
        if(email==null||email.equals("")){
            JOptionPane.showMessageDialog(this, "inserire email", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        if(!this.client.checkEmail(email)){
            JOptionPane.showMessageDialog(this, "email gia presente", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        

        String nome=this.nomeText.getText();
        if(nome==null||nome.equals("")){
            JOptionPane.showMessageDialog(this, "inserire nome", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }

        String cognome=this.cognomeText.getText();
        if(cognome==null||cognome.equals("")){
            JOptionPane.showMessageDialog(this, "inserire cognome", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
            return;
        }
        
        User user=new User();
        user.setCognome(cognome);
        try {
            DatatypeFactory factory=DatatypeFactory.newInstance();
            XMLGregorianCalendar data=factory.newXMLGregorianCalendar(new GregorianCalendar());
            user.setDataDiIscrizione(data);
        user.setDataDiNascita(data);
        } catch (DatatypeConfigurationException ex) {
            ex.printStackTrace();
        }
        
        user.setEmail(email);
        
        user.setNazione("italia");
        user.setNome(nome);
        user.setPassword(pwd2);
        user.setUsername(username);
        user.setCodiceConferma(null);
        user.setConfermato(false);
        
                
        if(client.createUser(user)){
            JOptionPane.showMessageDialog(this, "il tuo codice di attivazione è stato inviato all email specificata", "Virtual Risiko info", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }else{
            JOptionPane.showMessageDialog(this, "impossibile registrare utente", "Virtual Risiko info", JOptionPane.ERROR_MESSAGE);
           
        }
        

    }//GEN-LAST:event_okButtonActionPerformed

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField cognomeText;
    private javax.swing.JTextField emailText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JTextField nomeText;
    private javax.swing.JButton okButton;
    private javax.swing.JPasswordField passwordField1;
    private javax.swing.JPasswordField passwordField2;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables

}