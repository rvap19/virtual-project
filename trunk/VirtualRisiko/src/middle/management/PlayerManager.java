
package middle.management;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import middle.PeerGroup;
import middle.management.advertisement.GameAdvertisement;
import middle.management.advertisement.PipeAdvertisement;
import middle.management.advertisement.PlayerAdvertisement;
import middle.management.advertisement.RegistrationAdvertisement;
import middle.management.discover.GameDiscover;
import middle.management.discover.PlayerPresenceDiscover;
import middle.management.discover.RegistrationDiscovery;
import middle.management.listener.GameListener;
import middle.management.listener.PipeListener;
import middle.management.listener.PlayerListener;
import middle.management.listener.RegistrationListener;


public abstract class PlayerManager implements PipeListener,PlayerListener,GameListener,RegistrationListener {
    

    
   

    protected HashMap<String,PlayerAdvertisement> players;
    protected HashMap<String,GameAdvertisement> games;
    protected HashMap<String,RegistrationAdvertisement> registrations;
    protected HashMap<String,PipeAdvertisement> pipes;

    protected PlayerAdvertisement myPlayerAdvertisement;
    protected GameAdvertisement myGameAdvertisement;
    protected RegistrationAdvertisement myRegistrationAdvertisement;

    protected PeerGroup NetPeerGroup;
    
    
    protected PlayerPresenceDiscover playerDiscover;
    protected GameDiscover gameDiscover;
    protected RegistrationDiscovery registrationDiscover;

    protected   String Name;// = "foggiano";

    

    
    
    public PipeAdvertisement getOutputPipe(){
        return this.playerDiscover.getPipeAdvertisement();
    }
    
    public  void init(String seed,boolean startAsRendezvous)throws IOException {
        this.players=new HashMap<String, PlayerAdvertisement>();
            this.games=new HashMap<String, GameAdvertisement>();
            this.registrations=new HashMap<String, RegistrationAdvertisement>();
            this.pipes=new HashMap<String,PipeAdvertisement>();
    }

    public List<PlayerAdvertisement> findPlayers() throws  IOException{
       
     
        this.myPlayerAdvertisement=playerDiscover.announcePresence(10, Name);
        
        return playerDiscover.searchPlayers(true);

    }

    public List<PipeAdvertisement> findPipes() throws IOException{
        return playerDiscover.searchPipes(true);
    }

    public void addPipeListener(PipeListener listener){
        this.playerDiscover.addPlayerPipeListener(listener);
    }
    public List<GameAdvertisement> findGames() throws  IOException{
        
        
        return gameDiscover.searchGames(true);

    }

    public void createGame(String name,String mapName,int max) throws IOException{
        this.myGameAdvertisement=this.gameDiscover.announceGame(Name,max, name,mapName);
    }

    public void createRegistration(String gameId) throws IOException{
        this.myRegistrationAdvertisement=this.registrationDiscover.announceRegistartion(Name,gameId);
    }

    public List<RegistrationAdvertisement> findRegistrations(String gameID) throws IOException{
        return registrationDiscover.searchRegistrations(gameID, true);
    }



    public abstract void stop() throws IOException;

    public void presenceUpdated(PlayerAdvertisement playerInfo) {

        System.out.println("trovato adv per player "+playerInfo.getName());
        this.players.put(playerInfo.getPeerID(), playerInfo);
    }

    public void pipeUpdated(PipeAdvertisement pipeInfo) {
        System.out.println("trovata nuova pipe ::"+pipeInfo.getName());
        this.pipes.put(pipeInfo.getName(), pipeInfo);
    }

    
     public void gameUpdated(GameAdvertisement adv) {
        
        this.games.put(adv.getGameID().toString(), adv);
        String creator="SCONOSCIUTO";
        if(players.containsKey(adv.getCreatorID())){
            creator=players.get(adv.getCreatorID()).getName();
        }
        System.out.println("trovato adv per game "+adv.getGameName()+" creato da "+creator);

    }

     public void registrationUpdated(RegistrationAdvertisement adv) {
        this.registrations.put(adv.getPeerID(), adv);
        System.out.println("trovata registrazione di "+players.get(adv.getPeerID()).getName()+" per partita "+games.get(adv.getGameID()).getGameName());
    }

    public void removeRegistartionListener(RegistrationListener listener) {
        registrationDiscover.removeRegistartionListener(listener);
    }

    public void addRegistrationListener(RegistrationListener listener) {
        registrationDiscover.addRegistrationListener(listener);
    }

    public void removePlayerListener(PlayerListener listener) {
        playerDiscover.removePlayerListener(listener);
    }

    public void addPlayerListener(PlayerListener listener) {
        playerDiscover.addPlayerListener(listener);
    }

    public void removeGameListener(GameListener listener) {
        gameDiscover.removeGameListener(listener);
    }

    public void addGameListener(GameListener listener) {
        gameDiscover.addGameListener(listener);
    }

    public GameAdvertisement getMyGameAdvertisement() {
        return myGameAdvertisement;
    }

    public PlayerAdvertisement getMyPlayerAdvertisement() {
        return myPlayerAdvertisement;
    }

    public RegistrationAdvertisement getMyRegistrationAdvertisement() {
        return myRegistrationAdvertisement;
    }

    public PeerGroup getPeerGroup(){
        return this.NetPeerGroup;
    }
    
    public PipeAdvertisement getMyPipeAdvertisement(){
        return this.playerDiscover.getPipeAdvertisement();
    }

    public PlayerAdvertisement getPlayerAdvertisement(String player){
        return this.players.get(player);
    }
    
    public PipeAdvertisement getPipeAdvertisement(String pipe){
        return this.pipes.get(pipe);
    }
    
    public GameAdvertisement getGameAdvertisement(String game){
        return this.games.get(game);
    }
    
    public RegistrationAdvertisement getRegistrationAdvertisement(String reg){
        return this.registrations.get(reg);
    }
     
    
   


}