
package jxta;


import jxta.discover.PlayerPresenceDiscover;
import jxta.listener.PlayerListener;
import jxta.advertisement.PlayerAdvertisement;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.RegistrationAdvertisement;
import jxta.discover.GameDiscover;
import jxta.discover.RegistrationDiscovery;
import jxta.listener.GameListener;
import jxta.listener.PipeListener;
import jxta.listener.RegistrationListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.OutputPipe;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.protocol.PipeAdvertisement;

public class PlayerManager implements PlayerListener,GameListener,RegistrationListener {
    

    
   

    private HashMap<String,PlayerAdvertisement> players;
    private HashMap<String,GameAdvertisement> games;
    private HashMap<String,RegistrationAdvertisement> registrations;

    private PlayerAdvertisement myPlayerAdvertisement;
    private GameAdvertisement myGameAdvertisement;
    private RegistrationAdvertisement myRegistrationAdvertisement;

    private PeerGroup NetPeerGroup;
    private NetworkManager MyNetworkManager;
    
    private PlayerPresenceDiscover playerDiscover;
    private GameDiscover gameDiscover;
    private RegistrationDiscovery registrationDiscover;

    private   String Name;// = "foggiano";

    private   int TcpPort;// = 9721;

    private PeerID PID;// = IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, Name.getBytes());
    private File ConfigurationFile;// = new File(new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile()+ System.getProperty("file.separator") + Name);

    private OutputPipe outputPipe;

    public PlayerManager(){
        
        this("foggiano",9721);
    }
    public PlayerManager(String name,int tcpPort){
        this.Name=name;
        this.TcpPort=tcpPort;
        PID=IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, Name.getBytes());
        ConfigurationFile=new File(new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile()+ System.getProperty("file.separator") + Name);
    }

    public PipeAdvertisement getOutputPipe(){
        return this.playerDiscover.getPipeAdvertisement();
    }
    public void init() throws IOException, PeerGroupException{
        AdvertisementFactory.registerAdvertisementInstance(
                PlayerAdvertisement.getAdvertisementType(),
                new PlayerAdvertisement.Instantiator());

        AdvertisementFactory.registerAdvertisementInstance(
                GameAdvertisement.getAdvertisementType(),
                new GameAdvertisement.Instantiator());

        AdvertisementFactory.registerAdvertisementInstance(
                RegistrationAdvertisement.getAdvertisementType(),
                new RegistrationAdvertisement.Instantiator());


            NetworkManager.RecursiveDelete(ConfigurationFile);
            // Creation of the network manager
             MyNetworkManager = new NetworkManager(NetworkManager.ConfigMode.EDGE,
                    Name, ConfigurationFile.toURI());


            // Retrieving the network configurator
            NetworkConfigurator MyNetworkConfigurator = MyNetworkManager.getConfigurator();
           

            
            MyNetworkConfigurator.setTcpPort(TcpPort);
            MyNetworkConfigurator.setTcpEnabled(true);
            MyNetworkConfigurator.setTcpIncoming(true);
            MyNetworkConfigurator.setTcpOutgoing(true);

            MyNetworkConfigurator.setPeerID(PID);
            this.players=new HashMap<String, PlayerAdvertisement>();
            this.games=new HashMap<String, GameAdvertisement>();
            this.registrations=new HashMap<String, RegistrationAdvertisement>();
            
            NetPeerGroup  = MyNetworkManager.startNetwork();
             NetPeerGroup.getRendezVousService().setAutoStart(true);
             MyNetworkManager.waitForRendezvousConnection(3000);
             

             playerDiscover=new PlayerPresenceDiscover();
             playerDiscover.init(NetPeerGroup);
           //  playerDiscover.addPlayerListener(this);
             playerDiscover.startApp(null);

             gameDiscover=new GameDiscover();
             gameDiscover.init(NetPeerGroup);
           // gameDiscover.addGameListener(this);
            gameDiscover.startApp(null);

            registrationDiscover=new RegistrationDiscovery();
            registrationDiscover.init(NetPeerGroup);
      //      registrationDiscover.addRegistrationListener( this);
            registrationDiscover.startApp(null);

    }

    public List<PlayerAdvertisement> findPlayers() throws PeerGroupException, IOException{
       
     /*   if(myPlayerAdvertisement!=null){
            this.NetPeerGroup.getDiscoveryService().flushAdvertisement(myPlayerAdvertisement);
        }*/
        this.myPlayerAdvertisement=playerDiscover.announcePresence(10, Name);
        
        return playerDiscover.searchPlayers(true);

    }

    public void addPipeListener(PipeListener listener){
        this.playerDiscover.addPlayerPipeListener(listener);
    }
    public List<GameAdvertisement> findGames() throws PeerGroupException, IOException{
        
        
        return gameDiscover.searchGames(true);

    }

    public void createGame(String name,int max) throws IOException{
        if(myGameAdvertisement!=null){
            this.NetPeerGroup.getDiscoveryService().flushAdvertisement(myGameAdvertisement);
        }
        this.myGameAdvertisement=this.gameDiscover.announceGame(Name,max, name);
    }

    public void createRegistration(String gameId) throws IOException{
        if(myRegistrationAdvertisement!=null){
            this.NetPeerGroup.getDiscoveryService().flushAdvertisement(myRegistrationAdvertisement);
        }
        this.myRegistrationAdvertisement=this.registrationDiscover.announceRegistartion(Name,gameId);
    }

    public List<RegistrationAdvertisement> findRegistrations(String gameID) throws IOException{
        return registrationDiscover.searchRegistrations(gameID, true);
    }



    public void stop() throws IOException{
        DiscoveryService service=this.NetPeerGroup.getDiscoveryService();
        if(myGameAdvertisement!=null)
            service.flushAdvertisement(myGameAdvertisement);
        if(myPlayerAdvertisement!=null)
            service.flushAdvertisement(myPlayerAdvertisement);
        if(myRegistrationAdvertisement!=null)
            service.flushAdvertisement(myRegistrationAdvertisement);
        this.playerDiscover.stopApp();
        this.gameDiscover.stopApp();
        this.MyNetworkManager.stopNetwork();
    }

    public void presenceUpdated(PlayerAdvertisement playerInfo) {

        System.out.println("trovato adv per player "+playerInfo.getName());
        this.players.put(playerInfo.getPeerID(), playerInfo);
    }

     public void gameUpdated(GameAdvertisement adv) {
        
        this.games.put(adv.getID().toString(), adv);
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

     
    
   


}