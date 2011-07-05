/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import corba.PartitaInfo;
import corba.PlayerOperations;
import corba.RegistrationInfo;
import corba.RisikoServer;
import corba.UserInfo;
import corba.impl.PlayerImpl;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import util.GameParameter;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class RemoteVirtualPlayerManager extends VirtualPlayerManager implements VictoryListener{
    private HashMap<String,UserInfo> players;
    private HashMap<String,PartitaInfo> games;
    private HashMap<String,PipeAdvertisement> pipes;

    private RisikoServer server;
    private PlayerImpl player;

    private PartitaInfo game;


  

    public RemoteVirtualPlayerManager(RisikoServer server,PlayerImpl me,String name,int port) throws IOException, PeerGroupException {
        this.server=server;
        this.player=me;
        this.game=this.findPreviuosRegistration();
        players=new HashMap<String, UserInfo>();
        games=new HashMap<String, PartitaInfo>();
        pipes=new HashMap<String,PipeAdvertisement>();
        receivedInit=false;
        manager=new PlayerManager(me.getUserInfo().username, corba.server.Server.TCP_PORT);
       

    }

    @Override
    public String getMyName() {
        return this.player.getUserInfo().username;
    }


    @Override
    public void init() throws IOException, PeerGroupException {
         manager.init("tcp://"+"192.168.1.12:"+corba.server.Server.TCP_PORT, false);
         manager.findPlayers();
         manager.findPipes();
    }


    public PlayerImpl getPlayer() {
        return player;
    }

    public void setPlayer(PlayerImpl player) {
        this.player = player;
    }

    public boolean register(String gamaName){
         try {

            PartitaInfo partitaInfo = this.games.get(gamaName);



            PipeAdvertisement creatorPipe = pipes.get(partitaInfo.managerUsername + " Pipe");
            if(creatorPipe==null){
                System.err.println("RICERCA PIPE CREATOR");
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
                ElectionController electionController=new ElectionController(this.player.getUserInfo().username, this.manager.getPeerGroup(), pipes);
                communicator.setElectionNotifier(electionController);
           //     electionController.setElectionListener(this);


                server.signPlayer(player.getUserInfo(),games.get(gamaName));

                return true;
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
         return false;
    }


    private PartitaInfo findPreviuosRegistration(){
        PartitaInfo info= this.server.getActiveGame(this.player.getUserInfo().username);
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

       updateMap(players, playerInfo.getName(), playerInfo);
    }

    public void pipeUpdated(PipeAdvertisement pipeInfo) {
       // System.out.println("scoperto pipe"+pipeInfo.getName());
        this.pipes.put(pipeInfo.getName(), pipeInfo);
    }

    @Override
    public void creategame(String name, String mapName, int maxPlayers, int maxTurns) throws IOException {
        this.game=server.createGame(player.getUserInfo(),(short)maxTurns, (short)maxPlayers, name, mapName);
         try{
            String myName=this.player.getUserInfo().username;
            VirtualCommunicator communicator=VirtualCommunicator.initCentralCommunicator1(myName, this.manager.getPeerGroup(), this.manager.getMyPipeAdvertisement());
            communicator.setPlayerName(myName);
           /* ElectionController electionController=new ElectionController(myName, this.manager.getPeerGroup(), pipes);
            communicator.setElectionNotifier(electionController);
            electionController.setElectionListener(this);*/
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
        PartitaInfo[] infos=this.server.getAvailableGames();
        this.games.clear();

        for(int i=0;i<infos.length;i++){
            this.games.put(infos[i].name, infos[i]);
        }
        return this.games.keySet();
    }

    @Override
    public Set<String> findPlayers() throws PeerGroupException, IOException {
        UserInfo[] infos=this.server.getAuthenticateUsers();
        this.players.clear();

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
        this.register(gamaName);
        return this.server.signPlayer(this.getUserInfo(), games.get(gamaName));
    }

    @Override
    public boolean startCreatedGame(String gamaName) throws IOException {
        return false;
    }

    @Override
    protected void startGame() {
        super.startGame();
        GameController controller =GameController.getInstance();
        controller.addVictoryListener(this);
    }

    @Override
    public void notifyElection(ElectionMessage msg) {
        super.notifyElection(msg);
        if(newManager.equals(getMyName())){
            server.notifyNewManager(this.game,newManager);
        }
    }







}
