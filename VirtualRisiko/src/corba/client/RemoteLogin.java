package corba.client;


import corba.Authentication;
import corba.Player;
import corba.PlayerHelper;
import corba.RisikoServer;
import corba.RisikoServerHelper;
import corba.Summary;
import corba.UserInfo;
import corba.impl.PlayerImpl;
import corba.impl.RisikoServerImpl;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.LogManager;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import jxta.gui.RemoteVirtualPlayerManagerGUI;


import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import services.RemoteVirtualPlayerManager;

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
    private ORB orb;

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

    public ORB getOrb() {
        return orb;
    }

    public void setOrb(ORB orb) {
        this.orb = orb;
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
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

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

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText("HELP !!");
        jMenu1.setName("jMenu1"); // NOI18N
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });

        jMenuItem4.setText("classifica");
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem1.setText("recupera password");
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("effettua registrazione");
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem3.setText("attiva account");
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String userName=usernameField.getText();
        String pss=passwordField.getText();
        if(userName==null){
            userName="";
        }
        if(pss==null){
            pss="";
        }
        
        Authentication auth=null;
        try{
             auth=server.authenticate(userName, pss);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(rootPane, "Impossibile contattare server", "VirtualRisiko info", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!auth.message.equals("")){
            JOptionPane.showMessageDialog(rootPane, auth.message, "VirtualRisiko info", JOptionPane.ERROR_MESSAGE);
            return;
        }
     UserInfo info= auth.info;
        if(info.logged){
            System.out.println("Utente "+usernameField.getText()+" autenticato");
            try {
                PlayerImpl player  = new PlayerImpl(info);
                    // server.shutdown();
                    
                    ORBThread thread=new ORBThread(server, info, player, orb);
                    thread.start();
                    RemoteVirtualPlayerManager manager=new RemoteVirtualPlayerManager(server, player, info.username, 9701);
                    RemoteVirtualPlayerManagerGUI gui=new RemoteVirtualPlayerManagerGUI(manager);
                    gui.setListeners();
                    
                    this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }else{
            System.out.println("username / pass errat");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_usernameFieldActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new PasswordRecoveryFrame().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new PlayerDetailsFrame(this.server).setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        new AccountActivFrame(server).setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed
        // TODO add your handling code here:
        

    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TOD
        StatisticFrame frame=new StatisticFrame(server);
        frame.setVisible(true);
        Summary[] content=server.getCompleteStatistics();
        frame.setContent(content);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
    * @param args the command line arguments
    */

    public static void start(String[] args)throws Exception{
        Properties props=new Properties();

                props.load(new FileInputStream("remoteGame.properties"));
            ORB orb = ORB.init(args, props);
	    System.out.println("Initialized ORB");

            //Resolve MessageServer
	    RisikoServer risikoServer = RisikoServerHelper.narrow(
//	        orb.string_to_object("corbaname:iiop:"+props.getProperty("org.omg.CORBA.ORBInitialHost")+":"+props.getProperty("org.omg.CORBA.ORBInitialPort")+"#RisikoServer"));
orb.string_to_object("corbaname::"+props.getProperty("org.omg.CORBA.ORBInitialHost")+":"+props.getProperty("org.omg.CORBA.ORBInitialPort")+"#RisikoServer"));




        final RemoteLogin login=new RemoteLogin();
        login.setHelloImpl(risikoServer);
        login.setOrb(orb);


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                login.setVisible(true);
            }
        });
    }

    public static void start2(String[] args)throws Exception{
        Properties props=new Properties();

                props.load(new FileInputStream("remoteGame.properties"));
            ORB orb = ORB.init(args, props);
	    System.out.println("Initialized ORB");

            org.omg.CORBA.Object objRef =
                     orb.resolve_initial_references("NameService");
	    NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            RisikoServer risikoServer= RisikoServerHelper.narrow(ncRef.resolve_str("RisikoServer"));
            //Resolve MessageServer
	    System.out.println("risiko server loaded");


        final RemoteLogin login=new RemoteLogin();
        login.setHelloImpl(risikoServer);
        login.setOrb(orb);


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                login.setVisible(true);
            }
        });
    }
    public static void main(String args[])throws Exception {

         //initialize orb
         /*   Properties props = System.getProperties();
            props.put("org.omg.CORBA.ORBInitialPort", "1050");
            //Replace MyHost with the name of the host on which you are running the server
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");*/
        start(args);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables

    private  class ORBThread extends Thread{
        private ORB orb;
        private PlayerImpl player;
        private RisikoServer server;
        private UserInfo info;
        public ORBThread(RisikoServer server,UserInfo info,PlayerImpl player,ORB orb){
            this.server=server;
            this.player=player;
            this.orb=orb;
            this.info=info;
        }

        @Override
        public void run() {
            try{
            //Instantiate Servant and create reference
                POA rootPOA = POAHelper.narrow(
                    orb.resolve_initial_references("RootPOA"));
                System.out.println("rootPOA resolved");



                rootPOA.activate_object(player);
                Player ref = PlayerHelper.narrow(
                    rootPOA.servant_to_reference(player));


System.out.println("try to register with Risiko Server");
                //Register listener reference (callback object) with MessageServer
                this.server.registerPlayer(info.username,ref);
                System.out.println("Player registered with Risiko Server");

                //Activate rootpoa
                rootPOA.the_POAManager().activate();

                //Wait for messages
                System.out.println("Wait for incoming messages");
                orb.run();
            }catch(Exception ex){
               ex.printStackTrace();
            }
        }


    }
}
