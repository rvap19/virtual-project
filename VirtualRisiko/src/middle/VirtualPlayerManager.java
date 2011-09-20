/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle;

import middle.event.ElectionEvent;
import middle.event.InitEvent;
import middle.event.RecoveryEvent;
import middle.event.RisikoEvent;
import services.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import middle.listener.ElectionEventListener;
import middle.listener.InitEventListener;
import middle.listener.RecoveryEventListener;
import middle.management.PlayerManager;
import middle.management.advertisement.GameAdvertisement;
import middle.management.advertisement.PipeAdvertisement;
import middle.management.advertisement.PlayerAdvertisement;
import middle.management.advertisement.RegistrationAdvertisement;
import middle.messages.ElectionMessage;
import middle.messages.InitMessage;
import middle.messages.RecoveryMessage;
import middle.messages.WelcomeMessage;


import util.GameFactory;
import util.GameParameter;
import util.RecoveryUtil;
import virtualrisikoii.RecoveryParameter;
import virtualrisikoii.VirtualRisikoIIApp;
import virtualrisikoii.VirtualRisikoIIView;
import virtualrisikoii.XMapPanel;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Obiettivo;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public abstract class VirtualPlayerManager implements  InitEventListener,RecoveryEventListener,ElectionEventListener{
    protected String newManager=null;
    protected PlayerManager manager;
    protected String myName;

    protected HashMap<String,PlayerAdvertisement>players;
    protected HashMap<String,GameAdvertisement>games;
    protected HashMap<String,RegistrationAdvertisement>registrations;
    protected HashMap<String,PipeAdvertisement> pipes;

    protected boolean receivedInit=false;
    protected GameParameter gameParameter;
    protected VirtualCommunicator communicator;
    protected ElectionController electionController;
    
    protected Middle middle;
    protected RisikoMessageGenerator messageBuilder;
    protected VirtualRisikoIIApp app;
    protected VirtualRisikoIIView view;

    public VirtualPlayerManager(){
        
    }

    /*public VirtualPlayerManager(String name,int port) {
        manager=new PlayerManager(name,port);
        myName=name;
        players=new HashMap<String, PlayerAdvertisement>();
        games=new HashMap<String, GameAdvertisement>();
        registrations=new HashMap<String, RegistrationAdvertisement>();
        pipes=new HashMap<String,PipeAdvertisement>();
        
             
    }*/

    public PlayerManager getManager() {
        return manager;
    }

    

    public void init()throws IOException{
        players=new HashMap<String, PlayerAdvertisement>();
        games=new HashMap<String, GameAdvertisement>();
        registrations=new HashMap<String, RegistrationAdvertisement>();
        pipes=new HashMap<String,PipeAdvertisement>();
        
        middle=this.initMiddleLayer();
        messageBuilder=initMessageGenerator();
        manager=initPlayerManager();
        manager.init(null,false);
        
       
    }

    public void findADVS() throws  IOException{
         manager.findGames();
        manager.findPlayers();
    }



    public void clear(){
        this.players.clear();
            this.games.clear();
            this.registrations.clear();
    }

    public void clearPlayers(){
        this.players.clear();
    }

    public void clearGames(){

            this.games.clear();

    }

    public void clearRegistrations(){
       
            this.registrations.clear();
    }

    public Set<String> getGames(){
        return this.games.keySet();
    }

    public Set<String> getPlayers(){
        return this.players.keySet();
    }

    public Set<String> getRegistrations(){
        return this.registrations.keySet();
    }

    public Set<String> findRegistrations(String gameID) throws IOException {
        List<RegistrationAdvertisement> regs= manager.findRegistrations(gameID);
        int size=regs.size();
        for(int i=0;i<size;i++){
            this.registrationUpdated(regs.get(i));
        }

        return this.registrations.keySet();


    }

    public Set<String> findPlayers() throws  IOException {
        List<PlayerAdvertisement> plays= manager.findPlayers();
        int size=plays.size();
        for(int i=0;i<size;i++){
            this.presenceUpdated(plays.get(i));
        }
        return this.players.keySet();
    }

    public Set<String> findGames() throws  IOException {
        List<GameAdvertisement> gs= manager.findGames();
        int size=gs.size();
        for(int i=0;i<size;i++){
            this.gameUpdated(gs.get(i));
        }
        return this.games.keySet();
    }



    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }



    public void gameUpdated(GameAdvertisement adv) {
        games.put(adv.getGameName(),adv);
    }

    public void presenceUpdated(PlayerAdvertisement playerInfo) {
        players.put( playerInfo.getName(), playerInfo);
    }

    public void pipeUpdated(PipeAdvertisement pipeInfo) {
        this.pipes.put(pipeInfo.getName(), pipeInfo);
    }

    public void registrationUpdated(RegistrationAdvertisement adv) {
        this.registrations.put( adv.getPeerID(), adv);
    }

    public void creategame(String name,String mapName,int maxPlayers,int maxTurns) throws IOException{
        manager.createGame(name,mapName, maxPlayers);
        middle.configureVirtualCommunicatorAsCentral(this.myName, this.manager.getPeerGroup(), this.manager.getMyPipeAdvertisement());
        communicator=middle.getCommunicator();
        communicator.setPlayerName(myName);
        ElectionController electionController=initElectionController(this.myName, this.manager.getPeerGroup(), pipes);
        middle.addListener(EventTypes.ELECTION, electionController);
        middle.setElectionManager(electionController);
        electionController.setElectionListener(this);
        Random random=new Random();
        this.gameParameter=new GameParameter(mapName);
        gameParameter.setMaxPlayers(maxPlayers);
        gameParameter.setSeed_cards(random.nextInt());
        gameParameter.setSeed_dice(random.nextInt());
        gameParameter.setSeed_region(random.nextInt());
        gameParameter.setMaxTurns(maxTurns);
        communicator.setGameParameter(gameParameter, false, null, maxPlayers);
        middle.addListener(EventTypes.INIT, this);
        middle.addListener(EventTypes.RECOVERY, this);
    }
    
    protected abstract ElectionController initElectionController();

    public void notify(InitEvent event) {
        InitMessage msg=(InitMessage)event.getSource();
        try {
            if (receivedInit) {
                return;
            }
            GameParameter parameter=msg.getGameParameter();
            List<String> names=msg.getNames();
            int myTurno=names.indexOf(this.myName);
            int playerNames=names.size();
            int seed_dice=parameter.getSeed_dice();
            String map_name=parameter.getMapName();
            int seed_card=parameter.getSeed_cards();
            int seed_region=parameter.getSeed_region();
            int maxTurns=parameter.getMaxTurns();
            
            receivedInit = true;
            System.out.println("messaggio di inizializazione ricevuto !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            GameFactory factory = new GameFactory();
            //factory.loadGame("classicalMap");
            factory.loadGame(map_name);
            Mappa mappa = factory.getMappa();
            List<Obiettivo> obiettivi = factory.getObiettivi();
            int turno = 0;
            System.out.println("REGISTRAZIONE " + myTurno);
            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno,maxTurns, playerNames, myTurno, seed_dice, seed_region, seed_card,names);
            tavolo.setNameMap(map_name);
            GameController controller = GameController.createGameController(middle);
            
            factory.loadMapPanel();
            XMapPanel panel = factory.getMapPanel();
             app = new VirtualRisikoIIApp();
              view=new VirtualRisikoIIView(app, panel);

            app.show(view);



        } catch (Exception ex) {
        }


    }

    public boolean startCreatedGame(String gamaName) throws IOException{
        GameAdvertisement gameAdv = this.games.get(gamaName);
        this.manager.createRegistration(gameAdv.getGameID());
        if (this.myName.equals(gameAdv.getCreatorID())) {
            this.registrations.put(manager.getMyRegistrationAdvertisement().getPeerID(), manager.getMyRegistrationAdvertisement());
            startGame();
            return true;
        }
        return false;
    }

    public boolean registerInGame(String gamaName) throws IOException{
        GameAdvertisement gameAdv = this.games.get(gamaName);

        PipeAdvertisement creatorPipe = pipes.get(gameAdv.getCreatorID() + " Pipe");
            middle.configureVirtualCommunicatorAsPeer(this.myName, this.manager.getPeerGroup(), creatorPipe,this.manager.getMyPipeAdvertisement());
            communicator=middle.getCommunicator();
            middle.addListener(EventTypes.INIT, this);
            middle.addListener(EventTypes.RECOVERY, this);
            
            
            
                 electionController=this.initElectionController(myName, manager.getPeerGroup(), pipes);//new ElectionController(this.myName, this.manager.getPeerGroup(), pipes);
                
                electionController.setElectionListener(this);
                middle.setElectionManager(electionController);
                middle.addListener(EventTypes.ELECTION, electionController);
                WelcomeMessage msg=this.messageBuilder.generateWelcomeMSG(this.myName);
                this.communicator.sendMessage(msg);
                return true;
            

    }

    
    public void notify(RecoveryEvent event){
        RecoveryMessage msg=(RecoveryMessage) event.getSource();
        notifyReconnect(msg.getParameter());
    }
    private void notifyReconnect(RecoveryParameter parameter) {
        try {

            System.out.println("messaggio di inizializazione ricevuto !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

            //factory.loadGame("classicalMap");



            System.out.println("riconnessione ::: turno corrente" +parameter.getTurno()+" turno del mio giocatore "+ parameter.getTurnoMyGiocatore());

            RecoveryUtil util=new RecoveryUtil();
            util.recoveryTable(parameter);
            Tavolo tavolo = Tavolo.getInstance();
             GameController controller=GameController.getInstance();

            XMapPanel panel = util.getPanel();
             app = new VirtualRisikoIIApp();
             if(view!=null){
                 app.hide(view);
             }
             view=new  VirtualRisikoIIView(app, panel);
            app.show(view);




        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void notify(ElectionEvent event) {
        ElectionMessage msg=(ElectionMessage) event.getSource();

         if(newManager==null){
             newManager=msg.getPeerID();
             System.out.println("Primo tentativo ...  nuovo manager :: "+newManager);
             return;
         }

         String s=msg.getPeerID();
         if(!s.equals(newManager)){
             newManager=s;
             System.out.println("cambio vincitore elezione...  nuovo manager :: "+newManager);
             return;
         }
        int currentTurn=msg.getCurrentTurn();
        System.out.println("elezione notificata nuovo manager :: "+newManager);
        
        try{
            if(newManager.equals(this.myName)){
                List<Giocatore> players=Tavolo.getInstance().getGiocatori();
                ArrayList<String> names=new ArrayList<String>();
                Giocatore g;
                for(int i=0;i<players.size();i++){
                    g=players.get(i);
                    if(!g.getUsername().equals(myName)){
                        names.add(g.getUsername());
                    }
                }
                communicator.commuteToCentralCommunicator(this.manager.getMyPipeAdvertisement(), names, true);
                GameController.getInstance().restartTimers();

            }else{
                String pipeName=newManager+" Pipe";
                PipeAdvertisement pipeAdv=this.pipes.get(pipeName);
               // VirtualCommunicator.initPeerComunicator(myName, this.manager.getPeerGroup(), pipeAdv, this.manager.getMyPipeAdvertisement());
               communicator.restartPeerCommunicator(pipeAdv, this.manager.getMyPipeAdvertisement());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }


    }


    protected void startGame(){
        try {
            int myTurno=0;
            
            List<String> names=middle.getPlayerNames();
            InitMessage msg=this.messageBuilder.generateInitMSG(names,gameParameter);
            communicator.sendMessage(msg);
            GameFactory factory = new GameFactory();

            //factory.loadGame("classicalMap");
            factory.loadGame(gameParameter.getMapName());
            Mappa mappa = factory.getMappa();
            List<Obiettivo> obiettivi = factory.getObiettivi();
            int turno = names.indexOf(middle.getPlayerName());
            
            
            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno,gameParameter.getMaxTurns(), names.size(), myTurno, gameParameter.getSeed_dice(), gameParameter.getSeed_region(), gameParameter.getSeed_cards(),names);
            tavolo.setNameMap(gameParameter.getMapName());
            GameController controller=GameController.createGameController(middle);
            factory.loadMapPanel();
            XMapPanel panel=factory.getMapPanel();
            VirtualRisikoIIApp app = new VirtualRisikoIIApp();
            app.show(new VirtualRisikoIIView(app,panel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    public void notify(RisikoEvent event) {
        String type=event.getType();
        if(type.equals(EventTypes.INIT)){
            this.notify((InitEvent)event);
        }else if(type.equals(EventTypes.ELECTION)){
            this.notify((ElectionEvent)event);
        }else if(type.equals(EventTypes.RECOVERY)){
            this.notify((RecoveryEvent)event);
        }
    }

    

    
    protected abstract Middle initMiddleLayer();
    protected abstract PlayerManager initPlayerManager();
    protected abstract RisikoMessageGenerator initMessageGenerator();
    protected abstract middle.ElectionController initElectionController(String myName, PeerGroup peerGroup, HashMap<String, PipeAdvertisement> pipes) ;

     

}
