/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlayerManagerGUI.java
 *
 * Created on 13-mag-2011, 13.11.54
 */

package jxta.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import jxta.PlayerManager;
import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.PlayerAdvertisement;
import jxta.advertisement.RegistrationAdvertisement;
import jxta.communication.FullCommunicator;
import jxta.listener.GameListener;
import jxta.listener.PipeListener;
import jxta.listener.PlayerListener;
import jxta.listener.RegistrationListener;
import net.jxta.document.Advertisement;
import net.jxta.endpoint.Message;
import net.jxta.exception.PeerGroupException;
import net.jxta.protocol.PipeAdvertisement;
import services.GameController;
import util.GameFactory;
import virtualrisikoii.VirtualRisikoIIApp;
import virtualrisikoii.VirtualRisikoIIView;
import virtualrisikoii.XMapPanel;
import virtualrisikoii.listener.FullInitListener;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Obiettivo;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class FullPlayerManagerGUI extends javax.swing.JFrame implements GameListener,PlayerListener,PipeListener ,RegistrationListener , FullInitListener{

    private PlayerManager manager;
    private GameDialog gameDialog;
    private String myName;

    private HashMap<String,PlayerAdvertisement>players;
    private HashMap<String,GameAdvertisement>games;
    private HashMap<String,RegistrationAdvertisement>registrations;
    private HashMap<String,PipeAdvertisement> pipes;

    private boolean receivedInit=false;
    /** Creates new form PlayerManagerGUI */
    
    public FullPlayerManagerGUI() throws IOException,PeerGroupException{
        this("foggiano ",9701);
        
    }

    public FullPlayerManagerGUI(String name,int port) throws IOException, PeerGroupException {
        
        
        manager=new PlayerManager(name,port);
        myName=name;
        
        gameDialog=new GameDialog(this, true);
        
        gameDialog.setVisible(false);
        players=new HashMap<String, PlayerAdvertisement>();
        games=new HashMap<String, GameAdvertisement>();
        registrations=new HashMap<String, RegistrationAdvertisement>();
        pipes=new HashMap<String,PipeAdvertisement>();
        manager.init();
        
        manager.addRegistrationListener(this);
        manager.addGameListener(this);
        manager.addPlayerListener(this);
        manager.addPipeListener(this);
        manager.findGames();
        manager.findPlayers();

        initComponents();
        userNameLabel.setText(name);
        
       gamesList.setModel(new DefaultListModel());
       currentPlayersInGameList.setModel(new DefaultListModel());
       allPlayersList.setModel(new DefaultListModel());


      // FullCommunicator communicator =FullCommunicator.initCentralCommunicator1(myName,manager.getPeerGroup(), manager.getMyPipeAdvertisement(), 0);
       FullCommunicator communicator =FullCommunicator.initPeerFullCommunicator(myName, manager.getMyPipeAdvertisement(), manager.getPeerGroup());

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(220, 220, 220)
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
                .addComponent(userNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
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
        try {
            this.players.clear();
            this.games.clear();
            this.registrations.clear();

            DefaultListModel model=(DefaultListModel) gamesList.getModel();
            model.clear();

            model=(DefaultListModel) currentPlayersInGameList.getModel();
            model.clear();

            model=(DefaultListModel) allPlayersList.getModel();
            model.clear();

            List<PlayerAdvertisement> playersList=this.manager.findPlayers();
            Iterator<PlayerAdvertisement> iter1=playersList.iterator();
            while(iter1.hasNext()){
                PlayerAdvertisement pA=iter1.next();
                updateList(players, pA.getName(), pA, allPlayersList);
            }
            
            List<GameAdvertisement> gameList=this.manager.findGames();
            Iterator<GameAdvertisement> iter2=gameList.iterator();
            while(iter2.hasNext()){
                GameAdvertisement gA=iter2.next();
                updateList(games, gA.getGameName(), gA, gamesList);
            }

            




        } catch (PeerGroupException ex) {
            Logger.getLogger(FullPlayerManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FullPlayerManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void allPlayersListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_allPlayersListPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_allPlayersListPropertyChange

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
      String name=null;
      int maxPlayers=0;
      String mapName;
        try {
            // TODO add your handling code here:
            this.gameDialog.setVisible(true);
             name = gameDialog.getGameName();
             maxPlayers = gameDialog.getMaxPlayer();
             mapName=gameDialog.getMapName();
            manager.createGame(name,mapName, maxPlayers);
            
        } catch (IOException ex) {
           System.out.println("impossbile creare gioco "+name);
        }

      

      
    }//GEN-LAST:event_jButton4ActionPerformed

    private void gamesListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_gamesListValueChanged
        try {
            // TODO add your handling code here:
            Object xxx=this.gamesList.getSelectedValue();
            if(xxx==null)
                return;
            GameAdvertisement gameDv = this.games.get(this.gamesList.getSelectedValue().toString());
            updateRegistrations(gameDv.getGameID());

            
        } catch (IOException ex) {
            Logger.getLogger(FullPlayerManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_gamesListValueChanged

    private void updateRegistrations(String gameID) throws IOException{
        List<RegistrationAdvertisement> rA = this.manager.findRegistrations(gameID);
        this.registrations.clear();
        DefaultListModel model=(DefaultListModel) this.currentPlayersInGameList.getModel();
        model.clear();
        
        Iterator<RegistrationAdvertisement> iter=rA.iterator();
        while(iter.hasNext()){

            RegistrationAdvertisement adv=iter.next();
           registrations.put(adv.getPeerID(), adv);
            model.addElement(adv.getPeerID());
        }
        
    }

    /*
     * registrazione alla partita
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            if (this.gamesList.getSelectedValue() == null) {
                return;
            }
            String gamaName = gamesList.getSelectedValue().toString();
            GameAdvertisement gameAdv = this.games.get(gamaName);
            this.manager.createRegistration(gameAdv.getGameID());
            if (this.myName.equals(gameAdv.getCreatorID())) {
                this.registrations.put(manager.getMyRegistrationAdvertisement().getPeerID(), manager.getMyRegistrationAdvertisement());
                updateList(registrations, manager.getMyRegistrationAdvertisement().getPeerID(), manager.getMyRegistrationAdvertisement(), currentPlayersInGameList);
                startGame();
                return;
            }
            updateRegistrations(this.manager.getMyRegistrationAdvertisement().getGameID());
            PipeAdvertisement creatorPipe = pipes.get(gameAdv.getCreatorID() + " Pipe");
            FullCommunicator communicator=FullCommunicator.getInstance();
             communicator.addFullInitListener(this);
            communicator.connectoToPeer(creatorPipe);
           
            this.jButton1.setEnabled(false);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

            
        
            
       



    }//GEN-LAST:event_jButton1ActionPerformed


    private void startGame(){
        try {

           
        int numeroGiocatori=this.registrations.keySet().size();
        int myTurno=0;


            FullCommunicator communicator=FullCommunicator.getInstance();
            Random random=new Random();
            int dadi=random.nextInt();
            int regioni=random.nextInt();
            String mapName=this.manager.getMyGameAdvertisement().getMapName();
            communicator.sendInitMessages(numeroGiocatori, dadi, mapName, 0, regioni);
            boolean init=communicator.waitForInitAck(120*1000);
            System.out.println("ack RECEIVED :: "+init);



            GameFactory factory = new GameFactory();
            
            //factory.loadGame("classicalMap");
            factory.loadGame(mapName);
            Mappa mappa = factory.getMappa();
            List<Obiettivo> obiettivi = factory.getObiettivi();
            int turno = 0;

            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno, numeroGiocatori, myTurno, dadi, regioni, 0);
            tavolo.setNameMap(mapName);
            GameController controller=GameController.createGameController();
            factory.loadMapPanel();
            XMapPanel panel=factory.getMapPanel();
            this.setVisible(false);
            VirtualRisikoIIApp app = new VirtualRisikoIIApp();
            app.show(new VirtualRisikoIIView(app,panel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new FullPlayerManagerGUI().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(FullPlayerManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PeerGroupException ex) {
                    Logger.getLogger(FullPlayerManagerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList allPlayersList;
    private javax.swing.JList currentPlayersInGameList;
    private javax.swing.JList gamesList;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel userNameLabel;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    public void gameUpdated(GameAdvertisement adv) {
        updateList(games,adv.getGameName(),adv,gamesList);
    }

    public  void presenceUpdated(PlayerAdvertisement playerInfo) {
        /*if(!this.players.containsKey(playerInfo.getName())){
            players.put(playerInfo.getName(), playerInfo);
            DefaultListModel model=(DefaultListModel) this.allPlayersList.getModel();
            model.addElement(playerInfo.getName());
        }
          */
        updateList(players, playerInfo.getName(), playerInfo, allPlayersList);

           
        
    }

    public void registrationUpdated(RegistrationAdvertisement adv) {
        //updateList(registrations, adv.get, adv, gamesList);
        updateList(registrations, adv.getPeerID(), adv, currentPlayersInGameList);
    }

    private void updateList(HashMap map,String key,Advertisement adv,JList list){
        if(!map.containsKey(key)){
            map.put(key, adv);
            DefaultListModel model=(DefaultListModel) list.getModel();
            model.addElement(key);
        }
    }

    public void pipeUpdated(PipeAdvertisement pipeInfo) {
        this.pipes.put(pipeInfo.getName(), pipeInfo);
    }

    public void init(int t,int players,List<String>names,String creatorPipe,int seed_dice, String map_name, int seed_card, int seed_region) {
        try {/*
              *  per prima cosa continuare l'inizializzazione del communicator
              */
            if(receivedInit)
                return;

            receivedInit=true;
            System.out.println("messaggio di inizializazione ricevuto !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            int myTurno=names.indexOf(this.myName+" Pipe");

            /*
             * stampa i nomi ricevuti
             */

            Iterator<String> iter=names.iterator();
            while(iter.hasNext()){
                System.out.println(iter.next());
            }

            FullCommunicator communicator=FullCommunicator.getInstance();
            for(int i=myTurno+1;i<names.size();i++){
                PipeAdvertisement pipeAdv=this.pipes.get(names.get(i));
                System.out.println(pipeAdv);
                communicator.connectoToPeer(pipeAdv);

            }

            Message ack=communicator.createACKMessage(0);
            communicator.sendMessageTo(ack,creatorPipe);

            System.out.println("sending ack to master peerrrrrrrrr ");
            
            GameFactory factory = new GameFactory();
            //factory.loadGame("classicalMap");
            factory.loadGame(map_name);
            
            Mappa mappa = factory.getMappa();
            List<Obiettivo> obiettivi = factory.getObiettivi();
            int turno=0;
            int numeroGiocatori=players;
            
            
            
            System.out.println("REGISTRAZIONE "+myTurno);

            myTurno=t;
            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno, numeroGiocatori, myTurno, seed_dice, seed_region, seed_card);
            tavolo.setNameMap(map_name);
            GameController controller=GameController.createGameController();
            this.setVisible(false);
            factory.loadMapPanel();
            XMapPanel panel=factory.getMapPanel();
            VirtualRisikoIIApp app=new VirtualRisikoIIApp();
            app.show(new VirtualRisikoIIView(app,panel));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    

  

    

}
