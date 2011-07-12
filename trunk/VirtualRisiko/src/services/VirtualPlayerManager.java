/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.PlayerManager;
import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.PlayerAdvertisement;
import jxta.advertisement.RegistrationAdvertisement;
import jxta.communication.VirtualCommunicator;
import jxta.communication.messages.ElectionMessage;
import jxta.communication.messages.InitMessage;
import jxta.communication.messages.listener.ElectionListener;
import jxta.communication.messages.listener.InitListener;
import net.jxta.exception.PeerGroupException;
import net.jxta.protocol.PipeAdvertisement;
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
public class VirtualPlayerManager implements  InitListener,RecoveryListener,ElectionListener{
    protected String newManager=null;
    protected PlayerManager manager;
    protected String myName;

    protected HashMap<String,PlayerAdvertisement>players;
    protected HashMap<String,GameAdvertisement>games;
    protected HashMap<String,RegistrationAdvertisement>registrations;
    protected HashMap<String,PipeAdvertisement> pipes;

    protected boolean receivedInit=false;
    protected GameParameter gameParameter;

    protected VirtualRisikoIIApp app;
    protected VirtualRisikoIIView view;

    public VirtualPlayerManager(){
        
    }

    public VirtualPlayerManager(String name,int port) {
        manager=new PlayerManager(name,port);
        myName=name;
        players=new HashMap<String, PlayerAdvertisement>();
        games=new HashMap<String, GameAdvertisement>();
        registrations=new HashMap<String, RegistrationAdvertisement>();
        pipes=new HashMap<String,PipeAdvertisement>();
        try {
            init();
        } catch (IOException ex) {
            Logger.getLogger(VirtualPlayerManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PeerGroupException ex) {
            Logger.getLogger(VirtualPlayerManager.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }

    public PlayerManager getManager() {
        return manager;
    }

    

    public void init()throws IOException, PeerGroupException{
        manager.init(null,false);
        
       
    }

    public void findADVS() throws PeerGroupException, IOException{
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

    public Set<String> findPlayers() throws PeerGroupException, IOException {
        List<PlayerAdvertisement> plays= manager.findPlayers();
        int size=plays.size();
        for(int i=0;i<size;i++){
            this.presenceUpdated(plays.get(i));
        }
        return this.players.keySet();
    }

    public Set<String> findGames() throws PeerGroupException, IOException {
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
        VirtualCommunicator communicator=VirtualCommunicator.initCentralCommunicator1(this.myName, this.manager.getPeerGroup(), this.manager.getMyPipeAdvertisement());
        communicator.setPlayerName(myName);
        ElectionController electionController=new ElectionController(this.myName, this.manager.getPeerGroup(), pipes);
        communicator.setElectionNotifier(electionController);
        electionController.setElectionListener(this);
        Random random=new Random();
        this.gameParameter=new GameParameter(mapName);
        gameParameter.setMaxPlayers(maxPlayers);
        gameParameter.setSeed_cards(random.nextInt());
        gameParameter.setSeed_dice(random.nextInt());
        gameParameter.setSeed_region(random.nextInt());
        gameParameter.setMaxTurns(maxTurns);
        communicator.setGameParameter(gameParameter, false, null, maxPlayers);
        communicator.addInitListener(this);
        communicator.setRecoveryListeners(this);
    }

    public void init(InitMessage msg) {
        try {
            if (receivedInit) {
                return;
            }
            int myTurno=msg.getMyTurno();
            int players=msg.getPlayers();
            int seed_dice=msg.getSeed_dice();
            String map_name=msg.getMap_name();
            int seed_card=msg.getSeed_card();
            int seed_region=msg.getSeed_region();
            int maxTurns=msg.getMaxTurns();
            List<String> names=msg.getNames();
            receivedInit = true;
            System.out.println("messaggio di inizializazione ricevuto !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            GameFactory factory = new GameFactory();
            //factory.loadGame("classicalMap");
            factory.loadGame(map_name);
            Mappa mappa = factory.getMappa();
            List<Obiettivo> obiettivi = factory.getObiettivi();
            int turno = 0;
            System.out.println("REGISTRAZIONE " + myTurno);
            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno,maxTurns, players, myTurno, seed_dice, seed_region, seed_card,names);
            tavolo.setNameMap(map_name);
            GameController controller = GameController.createGameController();
            
            factory.loadMapPanel();
            XMapPanel panel = factory.getMapPanel();
             app = new VirtualRisikoIIApp();
              view=new VirtualRisikoIIView(app, panel);

            app.show(view);



        } catch (Exception ex) {
            ex.printStackTrace();
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
            VirtualCommunicator communicator=VirtualCommunicator.initPeerComunicator(this.myName, this.manager.getPeerGroup(), creatorPipe,this.manager.getMyPipeAdvertisement());
            if(communicator!=null){
                communicator.addInitListener(this);
                communicator.setRecoveryListeners(this);
                ElectionController electionController=new ElectionController(this.myName, this.manager.getPeerGroup(), pipes);
                communicator.setElectionNotifier(electionController);
                electionController.setElectionListener(this);
                return true;
            }

        return false;
    }

    public void notifyReconnect(RecoveryParameter parameter) {
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


    public void notifyElection(ElectionMessage msg) {

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
                VirtualCommunicator.getInstance().commuteToCentralCommunicator(this.manager.getMyPipeAdvertisement(), names, true);
                GameController.getInstance().restartTimers();

            }else{
                String pipeName=newManager+" Pipe";
                PipeAdvertisement pipeAdv=this.pipes.get(pipeName);
               // VirtualCommunicator.initPeerComunicator(myName, this.manager.getPeerGroup(), pipeAdv, this.manager.getMyPipeAdvertisement());
               VirtualCommunicator.getInstance().restartPeerCommunicator(pipeAdv, this.manager.getMyPipeAdvertisement());
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }


    }


    protected void startGame(){
        try {
            int myTurno=0;
            VirtualCommunicator communicator=VirtualCommunicator.getInstance();
            communicator.sendInitMessages();
            GameFactory factory = new GameFactory();

            //factory.loadGame("classicalMap");
            factory.loadGame(gameParameter.getMapName());
            Mappa mappa = factory.getMappa();
            List<Obiettivo> obiettivi = factory.getObiettivi();
            int turno = 0;

            List<String> names=communicator.getPlayerrNames();
            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno,gameParameter.getMaxTurns(), communicator.getCurrentPlayerNumber(), myTurno, gameParameter.getSeed_dice(), gameParameter.getSeed_region(), gameParameter.getSeed_cards(),names);
            tavolo.setNameMap(gameParameter.getMapName());
            GameController controller=GameController.createGameController();
            factory.loadMapPanel();
            XMapPanel panel=factory.getMapPanel();
            VirtualRisikoIIApp app = new VirtualRisikoIIApp();
            app.show(new VirtualRisikoIIView(app,panel));
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }



     

}
