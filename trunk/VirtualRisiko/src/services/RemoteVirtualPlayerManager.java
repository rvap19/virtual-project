/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import corba.PartitaInfo;
import corba.RegistrationInfo;
import corba.RisikoServer;
import corba.UserInfo;
import corba.impl.PlayerImpl;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import jxta.PlayerManager;
import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.PlayerAdvertisement;
import jxta.communication.VirtualCommunicator;
import jxta.communication.messages.ElectionMessage;
import jxta.communication.messages.InitMessage;
import net.jxta.document.Advertisement;
import net.jxta.exception.PeerGroupException;
import net.jxta.protocol.PipeAdvertisement;
import util.GameFactory;
import util.GameParameter;
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
public class RemoteVirtualPlayerManager extends VirtualPlayerManager implements VictoryListener{
    private HashMap<String,UserInfo> players;
    private HashMap<String,PartitaInfo> games;


    private RisikoServer server;
    private PlayerImpl player;

    private PartitaInfo game;


  

    public RemoteVirtualPlayerManager(RisikoServer server,PlayerImpl me,String name,int port) throws IOException, PeerGroupException {
        this.server=server;
        this.player=me;
       
        players=new HashMap<String, UserInfo>();
        games=new HashMap<String, PartitaInfo>();
        pipes=new HashMap<String,PipeAdvertisement>();
        receivedInit=false;
        manager=new PlayerManager(me.getUserInfo().username, corba.server.Server.TCP_PORT);
        super.myName=name;
         this.game=this.findPreviuosRegistration();
        

    }

    @Override
    public String getMyName() {
        return this.player.getUserInfo().username;
    }

    public PartitaInfo getPreviousGame() {
        return game;
    }

    public boolean isManager(PartitaInfo info){
        return info.managerUsername.equals(this.myName);
    }

    public void deletePreviousRegistration(){
        if(game!=null){
            this.server.deleteRegistration(game, player.getUserInfo());
            game=null;
        }
    }

    public void deletePreviousGame(){
        if(game!=null){
            this.server.deletePartita(game, player.getUserInfo());
            game=null;
        }
    }

    @Override
    public void init() throws IOException, PeerGroupException {
        Properties props=new Properties();

                props.load(new FileInputStream("remoteGame.properties"));
         manager.init("tcp://"+props.getProperty("org.omg.CORBA.ORBInitialHost")+":"+corba.server.Server.TCP_PORT, false);
         manager.findPlayers();
         manager.findPipes();
    }


    public PlayerImpl getPlayer() {
        return player;
    }

    public void setPlayer(PlayerImpl player) {
        this.player = player;
    }

    private List<String> names;
    public PartitaInfo register(String gamaName){
         try {
            
            this.findPlayers();
            PartitaInfo partitaInfo =this.game;
            if(partitaInfo==null){
                partitaInfo= this.games.get(gamaName);
            }
            
            if((!partitaInfo.tournament.equals(""))){
                server.signPlayer(this.player.getUserInfo(), partitaInfo);
                PartitaInfo[] partitas=server.getAvailableGames();
                for(int i=0;i<partitas.length;i++){
                    this.games.put(partitas[i].name, partitas[i]);
                    
                }

                partitaInfo=this.games.get(partitaInfo.name);
                
                
                
                
                
                
                
                
                
                
                if(partitaInfo.managerUsername.equals(this.myName)){

                         UserInfo[] infos=this.server.getPlayers(partitaInfo);
                         int index=0;
                         for(int i=0;i<infos.length;i++){
                             if(infos[i].username.equals(myName)){
                                 index=i;
                             }
                         }

                         UserInfo temp=infos[index];
                         infos[index]=infos[0];
                         infos[0]=temp;
                         names=new ArrayList<String>();
                         for(int i=0;i<infos.length;i++){
                             
                                 names.add(infos[i].username);
                             
                         }
                         creategame_(partitaInfo.name, partitaInfo.type, partitaInfo.maxPlayers, partitaInfo.maxTurns);
                         this.startGame();
                         this.findPlayers();
                         
                         return partitaInfo;
                }

            }



            PipeAdvertisement creatorPipe = pipes.get(partitaInfo.managerUsername + " Pipe");
            if(creatorPipe==null){
                System.err.println("RICERCA PIPE CREATOR "+partitaInfo.managerUsername);
                try {
                    manager.findPipes();

                    Thread.sleep(1500);
                } catch (Exception ex) {
                   ex.printStackTrace();
                }

                return register(gamaName);
            }
            VirtualCommunicator communicator=VirtualCommunicator.initPeerComunicator(this.player.getUserInfo().username, this.manager.getPeerGroup(), creatorPipe,this.manager.getMyPipeAdvertisement());
            if(communicator!=null){
                communicator.addInitListener(this);
                communicator.setRecoveryListeners(this);
                RemoteElectionController electionController=new RemoteElectionController(this.player.getUserInfo().username, this.manager.getPeerGroup(), pipes);
                electionController.setPartita(partitaInfo);
                electionController.setServer(server);
                communicator.setElectionNotifier(electionController);
                electionController.setElectionListener(this);
                if(game==null){
                    this.game=games.get(gamaName);
                }
                server.signPlayer(player.getUserInfo(),game);

                return game;
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
         return null;
    }


    public PartitaInfo findPreviuosRegistration(){
        PartitaInfo info= this.server.getActiveGame(this.player.getUserInfo().username);
        if(info.name.equals("")){
            return null;
        }
        System.out.println(info.name);
        return info;
    }

    public UserInfo getUserInfo() {
        return player.getUserInfo();
    }

    public void notifyNewGame(PartitaInfo partita) {
        games.put(partita.name, partita);
    }

    public void notifyNewRegistration(RegistrationInfo registration) {
       
    }

    public void notifyStart(String managerName) {
        System.out.println("AVVIO GAME");
        startGame();
    }

    public void notifyNewPlayer(UserInfo userInfo) {
        players.put(userInfo.username, userInfo);
    }

    public void gameUpdated(GameAdvertisement adv) {
        updateMap(games,adv.getGameName(),adv);
    }

    public void presenceUpdated(PlayerAdvertisement playerInfo) {
        System.out.println("scoperto player "+playerInfo.getName());
       updateMap(players, playerInfo.getName(), playerInfo);
    }

    public void pipeUpdated(PipeAdvertisement pipeInfo) {
        System.out.println("scoperto pipe"+pipeInfo.getName());
        this.pipes.put(pipeInfo.getName(), pipeInfo);
    }

    public PartitaInfo getPartitaInfo(String partita){
        return this.games.get(partita);
    }

    @Override
    public void creategame(String name, String mapName, int maxPlayers, int maxTurns) throws IOException {
        this.game=server.createGame(player.getUserInfo(),(short)maxTurns, (short)maxPlayers, name, mapName);
        creategame_(name, mapName, maxPlayers, maxTurns);
        
    }

     public void creategame_(String name, String mapName, int maxPlayers, int maxTurns) {

         try{
            String myName=this.player.getUserInfo().username;
            VirtualCommunicator communicator=VirtualCommunicator.initCentralCommunicator1(myName, this.manager.getPeerGroup(), this.manager.getMyPipeAdvertisement());
            communicator.setPlayerName(myName);
            RemoteElectionController electionController=new RemoteElectionController(myName, this.manager.getPeerGroup(), pipes);
            electionController.setPartita(game);
            electionController.setServer(server);
            communicator.setElectionNotifier(electionController);
            electionController.setElectionListener(this);
            Random random=new Random();
            this.gameParameter=new GameParameter(mapName);
            gameParameter.setMaxPlayers(maxPlayers);
            gameParameter.setSeed_cards(random.nextInt());
            gameParameter.setMaxTurns(maxTurns);
            gameParameter.setSeed_dice(random.nextInt());
            gameParameter.setSeed_region(random.nextInt());
            communicator.setGameParameter(gameParameter, false, null, maxPlayers);
            communicator.addInitListener(this);
            communicator.setRecoveryListeners(this);
         }catch(Exception ex){
             ex.printStackTrace();
         }
    }



    public void init(InitMessage init) {
        super.init(init);
        GameController controller=GameController.getInstance();
        controller.addVictoryListener(this);
    }

   

     public void notifyVictory(List<Giocatore> giocatori, Giocatore winner,boolean victory) {
        if(!VirtualCommunicator.getInstance().isManager()){
            return;
        }
        Tavolo tavolo=Tavolo.getInstance();

        RegistrationInfo[] results=new RegistrationInfo[giocatori.size()];
        for(int i=0;i<results.length;i++){
           Giocatore giocatoreCorrente=giocatori.get(i);
           RegistrationInfo info=new RegistrationInfo(game.id, tavolo.getPunteggioGiocatore(giocatoreCorrente), giocatoreCorrente==winner && victory, giocatoreCorrente.getUsername());
           results[i]=info;
        }

        server.saveResult(results);

    }


    private void updateMap(HashMap map,String key,Advertisement adv){
        if(!map.containsKey(key)){
            map.put(key, adv);

        }
    }

    @Override
    public void clear() {
        this.games.clear();
        this.players.clear();
        
    }

    @Override
    public void clearGames() {
       this.games.clear();
    }

    @Override
    public void clearPlayers() {
       this.players.clear();
    }

    @Override
    public void clearRegistrations() {
       
    }

   

    @Override
    public Set<String> findGames() throws PeerGroupException, IOException {
        try{
        PartitaInfo[] infos=this.server.getAvailableGames();

        this.games.clear();

        for(int i=0;i<infos.length;i++){
            if(!infos[i].name.equals("")){
                this.games.put(infos[i].name, infos[i]);
            }
        }
        }catch(Exception ex){

        }
        return this.games.keySet();
    }

    @Override
    public Set<String> findPlayers() throws PeerGroupException, IOException {
        manager.findPlayers();

        UserInfo[] infos=null;
        try{
            infos=this.server.getAuthenticateUsers();
        }catch(Exception ex){

        }
        this.players.clear();

        if(infos!=null)
            for(int i=0;i<infos.length;i++){
            this.players.put(infos[i].username, infos[i]);
        }
        return this.players.keySet();
    }

    @Override
    public Set<String> findRegistrations(String gameID) throws IOException {
        UserInfo[] infos=this.server.getPlayers(this.games.get(gameID));
        Set<String> set=new HashSet<String>();
        for(int i=0;i<infos.length;i++){
            set.add(infos[i].username);
        }
        return set;
    }

    @Override
    public Set<String> getGames() {
        return this.games.keySet();
    }

  

    @Override
    public Set<String> getPlayers() {
        return this.players.keySet();
    }

    @Override
    public Set<String> getRegistrations() {
        return new HashSet<String>();
    }

    @Override
    public boolean registerInGame(String gamaName) throws IOException {
        PartitaInfo info=this.register(gamaName);
        if(info!=null){

             this.server.signPlayer(this.getUserInfo(), info);
             return true;
           
        }else{
            return false;
        }
    }

    @Override
    public boolean startCreatedGame(String gamaName) throws IOException {
        return false;
    }

    @Override
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

            if(names!=null&&names.size()>0){
                communicator.setNames(names);
                communicator.setGameInProgress(true);
            }else{
                names=communicator.getPlayerNames();
            }
             
            Tavolo tavolo = Tavolo.createInstance(mappa, obiettivi, turno,gameParameter.getMaxTurns(), names.size(), myTurno, gameParameter.getSeed_dice(), gameParameter.getSeed_region(), gameParameter.getSeed_cards(),names);
            tavolo.setNameMap(gameParameter.getMapName());
            GameController controller=GameController.createGameController();
            factory.loadMapPanel();
            XMapPanel panel=factory.getMapPanel();
            VirtualRisikoIIApp app = new VirtualRisikoIIApp();
            app.show(new VirtualRisikoIIView(app,panel));

            controller.addVictoryListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
   

    @Override
    public void notifyElection(ElectionMessage msg) {
        super.notifyElection(msg);
        if(newManager.equals(getMyName())){
            server.notifyNewManager(this.game,newManager);
        }
    }







}
