/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlayerManagerGUI.java
 *
 * Created on 13-mag-2011, 13.11.54
 */



import corba.PartitaInfo;
import corba.RisikoServer;
import corba.UserInfo;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import jxta.gui.GameDialog;
import net.jxta.exception.PeerGroupException;

/**
 *
 * @author root
 */
public class RemotePlayerManagerGUI extends javax.swing.JFrame {

   private HashMap<String,UserInfo> players;
   private HashMap<String,PartitaInfo> games;
  

   private RisikoServer server;
   private UserInfo myIdentity;

   private GameDialog gameDialog;

    public RemotePlayerManagerGUI(RisikoServer server,UserInfo me,String name,int port) throws IOException, PeerGroupException {
        this.server=server;
        this.myIdentity=me;
        players=new HashMap<String, UserInfo>();
        games=new HashMap<String, PartitaInfo>();
        
       

        initComponents();
        gameDialog=new GameDialog(this, true);
        gameDialog.setVisible(false);
        userNameLabel.setText(name);
        
       gamesList.setModel(new DefaultListModel());
       currentPlayersInGameList.setModel(new DefaultListModel());
       allPlayersList.setModel(new DefaultListModel());

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

        jScrollPane1 = new javax.swing.JScrollPane();
        gamesList = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentPlayersInGameList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        allPlayersList = new javax.swing.JList();
        jButton4 = new javax.swing.JButton();
        userNameLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("player manager");

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        gamesList.setBorder(javax.swing.BorderFactory.createTitledBorder("games"));
        gamesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        gamesList.setName("gamesList"); // NOI18N

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${games}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, this, eLProperty, gamesList);
        jListBinding.setDetailBinding(org.jdesktop.beansbinding.ELProperty.create("${gameName}"));
        bindingGroup.addBinding(jListBinding);

        gamesList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                gamesListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(gamesList);

        jButton1.setText("register");
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("refresh");
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        currentPlayersInGameList.setBorder(javax.swing.BorderFactory.createTitledBorder("current players"));
        currentPlayersInGameList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        currentPlayersInGameList.setName("currentPlayersInGameList"); // NOI18N
        jScrollPane2.setViewportView(currentPlayersInGameList);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        allPlayersList.setBorder(javax.swing.BorderFactory.createTitledBorder("all players"));
        allPlayersList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        allPlayersList.setAutoscrolls(false);
        allPlayersList.setDoubleBuffered(true);
        allPlayersList.setName("allPlayersList"); // NOI18N
        allPlayersList.setValueIsAdjusting(true);
        allPlayersList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                allPlayersListPropertyChange(evt);
            }
        });
        jScrollPane3.setViewportView(allPlayersList);
        allPlayersList.getAccessibleContext().setAccessibleParent(this);

        jButton4.setText("new game");
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        userNameLabel.setFont(new java.awt.Font("Georgia", 2, 14));
        userNameLabel.setText("####");
        userNameLabel.setName("userNameLabel"); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/virtualrisikoii/resources/sfondo.jpg"))); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Rockwell", 1, 14));
        jLabel1.setText("Bentornato a Virtual Risiko,");
        jLabel1.setName("jLabel1"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(userNameLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)))
                    .addComponent(jButton4))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userNameLabel)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(16, 16, 16))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
            

            updatePlayersJList();
            updateGamesJList();
           
    }//GEN-LAST:event_jButton2ActionPerformed

    private void updatePlayersJList(){
        DefaultListModel model=(DefaultListModel) allPlayersList.getModel();
        model.clear();

        this.players.clear();

        UserInfo[] users=server.getAuthenticateUsers();
        for(int i=0;i<users.length;i++){
            players.put(users[i].username, users[i]);
        }

        Iterator<UserInfo> iter=this.players.values().iterator();
        while(iter.hasNext()){
            model.addElement(iter.next().username);
        }

    }
    
    private void updateGamesJList(){
        DefaultListModel model=(DefaultListModel) this.gamesList.getModel();
        model.clear();

        this.games.clear();

        PartitaInfo[] partitas=server.getAvailableGames();
        for(int i=0;i<partitas.length;i++){
            games.put(partitas[i].name, partitas[i]);
        }

        Iterator<PartitaInfo> iter=this.games.values().iterator();
        while(iter.hasNext()){
            model.addElement(iter.next().name);
        }

    }
    
    private void updateRegistrationsJList(PartitaInfo info){
        DefaultListModel model=(DefaultListModel) this.currentPlayersInGameList.getModel();
        model.clear();


        UserInfo[] regis=server.getPlayers(info);
        

        
        for(int i=0;i<regis.length;i++){
            model.addElement(regis[i].username);
        }
    }


    private void allPlayersListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_allPlayersListPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_allPlayersListPropertyChange

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      String name=null;
      int maxPlayers=0;
      String mapName;
       
            // TODO add your handling code here:
            this.gameDialog.setVisible(true);
             name = gameDialog.getGameName();
             maxPlayers = gameDialog.getMaxPlayer();
             mapName=gameDialog.getMapName();
             server.createGame(myIdentity, (short)15,(short) 6, name);
            
        this.updateGamesJList();
      

      
    }//GEN-LAST:event_jButton4ActionPerformed

    private void gamesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_gamesListValueChanged
       
            // TODO add your handling code here:
            Object xxx=this.gamesList.getSelectedValue();
            if(xxx==null)
                return;
            
            updateRegistrationsJList(this.games.get(xxx.toString()));
            
            
        

    }//GEN-LAST:event_gamesListValueChanged

   

    /*
     * registrazione alla partita
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
            // TODO add your handling code here:
            if (this.gamesList.getSelectedValue() == null) {
                return;
            }
            String gamaName = gamesList.getSelectedValue().toString();
            
           server.signPlayer(myIdentity, games.get(gamaName));
           this.updateRegistrationsJList(games.get(gamaName));
            this.jButton1.setEnabled(false);
       

            
        
            
       



    }//GEN-LAST:event_jButton1ActionPerformed


    private void startGame(){
       

    }
 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList allPlayersList;
    private javax.swing.JList currentPlayersInGameList;
    private javax.swing.JList gamesList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel userNameLabel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables



   

    

  

    

}
