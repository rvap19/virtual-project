
import corba.RisikoServer;
import corba.RisikoServerHelper;
import corba.UserInfo;
import java.util.logging.LogManager;


import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Login.java
 *
 * Created on 14-mag-2011, 16.20.33
 */

/**
 *>
 * @author root
 */
public class RemoteLogin extends javax.swing.JFrame {

    /** Creates new form Login */
    
    private RisikoServer server;

    public RemoteLogin() {
        initComponents();

   //     Suono.playSound("/virtualrisikoii/resources/dadi/intro2.wav");


        LogManager.getLogManager().reset();
    }

    public RisikoServer getHelloImpl() {
        return server;
    }

    public void setHelloImpl(RisikoServer helloImpl) {
        this.server = helloImpl;
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("virtual risiko login");
        setResizable(false);

        usernameField.setText("user");
        usernameField.setName("usernameField"); // NOI18N
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        jLabel2.setText("username");
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText("password");
        jLabel3.setName("jLabel3"); // NOI18N

        passwordField.setText("jPasswordField1");
        passwordField.setName("passwordField"); // NOI18N

        jButton1.setText("login");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/sfondo.jpg"))); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton1)))
            .addComponent(jLabel1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(11, 11, 11)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
     UserInfo info= server.authenticate(this.usernameField.getText(),this.passwordField.getText());
        if(info.logged){
            System.out.println("Utente "+usernameField.getText()+" autenticato");
           // server.shutdown();
            System.exit(0);
        }else{
            System.out.println("username / pass errat");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_usernameFieldActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[])throws Exception {

        ORB orb = ORB.init(args, null);
        org.omg.CORBA.Object objRef =
                 orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

        String name = "Hello";
        RisikoServer helloImpl = RisikoServerHelper.narrow(ncRef.resolve_str(name));

        final RemoteLogin login=new RemoteLogin();
        login.setHelloImpl(helloImpl);


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                login.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables

}
