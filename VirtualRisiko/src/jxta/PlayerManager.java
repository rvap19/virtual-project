
package jxta;


import jxta.discover.PlayerPresenceDiscover;
import jxta.advertisement.PlayerAdvertisement;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.RegistrationAdvertisement;
import jxta.communication.JXTAPeerGroup;
import jxta.discover.GameDiscover;
import jxta.discover.RegistrationDiscovery;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;
import net.jxta.endpoint.EndpointAddress;
import net.jxta.endpoint.EndpointListener;
import net.jxta.endpoint.Message;
import net.jxta.endpoint.MessageElement;
import net.jxta.endpoint.MessageFilterListener;
import net.jxta.endpoint.MessageTransport;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.pipe.OutputPipe;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;
import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezvousListener;
import remote.impl.NetManager;

public class PlayerManager extends middle.management.PlayerManager implements RendezvousListener{
    
    private NetworkManager MyNetworkManager;
    private   int TcpPort = 9700;

    private PeerID PID;// = IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, Name.getBytes());
    private File ConfigurationFile;// = new File(new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile()+ System.getProperty("file.separator") + Name);

    private OutputPipe outputPipe;

    public PlayerManager(){
        
        this("foggiano",9721);
    }
    public PlayerManager(String name,int tcpPort){
        
        this.Name=name;
      //  this.TcpPort=tcpPort;
        PID=IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, Name.getBytes());
        ConfigurationFile=new File(new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile()+ System.getProperty("file.separator") + "config_file"+ System.getProperty("file.separator") + Name);
    }

    @Override
    public void init(String seed,boolean startAsRendezvous) throws IOException{
        super.init(seed, startAsRendezvous);
       AdvertisementFactory.registerAdvertisementInstance(
                GameAdvertisement.getAdvertisementType(),
                new GameAdvertisement.Instantiator());
        
        AdvertisementFactory.registerAdvertisementInstance(
                PlayerAdvertisement.getAdvertisementType(),
                new PlayerAdvertisement.Instantiator());

        

        AdvertisementFactory.registerAdvertisementInstance(
                RegistrationAdvertisement.getAdvertisementType(),
                new RegistrationAdvertisement.Instantiator());


            NetworkManager.RecursiveDelete(ConfigurationFile);
            // Creation of the network manager
            if(!startAsRendezvous){
             MyNetworkManager = new NetworkManager(NetworkManager.ConfigMode.EDGE,
                    Name, ConfigurationFile.toURI());
            }else{
                 MyNetworkManager = new NetworkManager(NetworkManager.ConfigMode.RENDEZVOUS_RELAY,
                    Name, ConfigurationFile.toURI());
            }

            // Retrieving the network configurator
            NetworkConfigurator MyNetworkConfigurator = MyNetworkManager.getConfigurator();

            MyNetworkConfigurator.clearRendezvousSeeds();
             MyNetworkConfigurator.setTcpEnabled(true);
             MyNetworkConfigurator.setHttpEnabled(true);
             MyNetworkConfigurator.setTcpPort(TcpPort);
             MyNetworkConfigurator.setHttpPort(TcpPort+1);
             MyNetworkConfigurator.setUseMulticast(true);
             MyNetworkConfigurator.setTcpOutgoing(true);
                MyNetworkConfigurator.setHttpOutgoing(true);
                MyNetworkConfigurator.setTcpIncoming(true);
                MyNetworkConfigurator.setHttpIncoming(true);
                MyNetworkConfigurator.setTcpPublicAddress(NetManager.findMeExternalIP()+":"+TcpPort, true);
                MyNetworkConfigurator.setHttpPublicAddress(NetManager.findMeExternalIP()+":"+(TcpPort+1), true);
            if(seed!=null){
                /*iunserire tcp indirizzo*/
                
                URI TheSeed = URI.create("tcp://"+seed+":"+TcpPort);
               // MyNetworkConfigurator.addRdvSeedingURI(TheSeed);//       TheSeed);
                MyNetworkConfigurator.addSeedRelay(TheSeed);
                MyNetworkConfigurator.addSeedRendezvous(TheSeed);
                TheSeed = URI.create("http://"+seed+":"+(TcpPort+1));
                MyNetworkConfigurator.addSeedRelay(TheSeed);
                MyNetworkConfigurator.addSeedRendezvous(TheSeed);
                
            }

            
            
           
            
           
            
            
            
        
               
            

            MyNetworkConfigurator.setPeerID(PID);
        try {
            // NetPeerGroup.getRendezVousService().setAutoStart(true);
             // MyNetworkManager.waitForRendezvousConnection(3000);

             NetPeerGroup  = new JXTAPeerGroup(MyNetworkManager.startNetwork());
             net.jxta.peergroup.PeerGroup pg=((JXTAPeerGroup)NetPeerGroup).getPeerGroup();
             
            
        } catch (PeerGroupException ex) {
            System.out.println("PG Exception starting network");
            ex.printStackTrace();
            System.exit(-1);
        }
            JXTAPeerGroup pG=(JXTAPeerGroup) NetPeerGroup;
            net.jxta.peergroup.PeerGroup peerGroup=pG.getPeerGroup();

            if(startAsRendezvous){
                peerGroup.getRendezVousService().addListener(this);
                peerGroup.getRendezVousService().startRendezVous();
            }else{
                peerGroup.getRendezVousService().setAutoStart(true);
                 
                if(!peerGroup.getRendezVousService().isConnectedToRendezVous()){
                    peerGroup.getRendezVousService().startRendezVous();
                }
            }
            
             

             playerDiscover=new PlayerPresenceDiscover();
             playerDiscover.init(NetPeerGroup);
             playerDiscover.addPlayerListener(this);
             playerDiscover.addPlayerPipeListener(this);
             playerDiscover.startApp(null);

             gameDiscover=new GameDiscover();
             gameDiscover.init(NetPeerGroup);
            gameDiscover.addGameListener(this);
            gameDiscover.startApp(null);

            registrationDiscover=new RegistrationDiscovery();
            registrationDiscover.init(NetPeerGroup);
            registrationDiscover.addRegistrationListener( this);
            registrationDiscover.startApp(null);
            System.out.println("Rendezvous "+ peerGroup.getRendezVousService().isRendezVous());

    }

    

  



    public void stop() throws IOException{
        this.playerDiscover.stopApp();
        this.gameDiscover.stopApp();
        this.MyNetworkManager.stopNetwork();
    }

    public void rendezvousEvent(RendezvousEvent event) {
      String eventDescription;
      
      int    eventType;

         eventType = event.getType();

         switch( eventType ) {
            case RendezvousEvent.RDVCONNECT:
               eventDescription = "RDVCONNECT";
               break;
            case RendezvousEvent.RDVRECONNECT:
               eventDescription = "RDVRECONNECT";
               break;
            case RendezvousEvent.RDVDISCONNECT:
               eventDescription = "RDVDISCONNECT";
               break;
            case RendezvousEvent.RDVFAILED:
               eventDescription = "RDVFAILED";
               break;
            case RendezvousEvent.CLIENTCONNECT:
               eventDescription = "CLIENTCONNECT";
               break;
            case RendezvousEvent.CLIENTRECONNECT:
               eventDescription = "CLIENTRECONNECT";
               break;
            case RendezvousEvent.CLIENTDISCONNECT:
               eventDescription = "CLIENTDISCONNECT";
               break;
            case RendezvousEvent.CLIENTFAILED:
               eventDescription = "CLIENTFAILED";
               break;
            case RendezvousEvent.BECAMERDV:
               eventDescription = "BECAMERDV";
               break;
            case RendezvousEvent.BECAMEEDGE:
               eventDescription = "BECAMEEDGE";
               break;
            default:
               eventDescription = "UNKNOWN RENDEZVOUS EVENT";
         }

         System.out.println("RendezvousEvent:  event =  " 
                     + eventDescription + " from peer = " + event.getPeerID().toURI());
   }

     private static class ChatUnicastReceiver implements EndpointListener {
        public void processIncomingMessage(Message msg, EndpointAddress source, EndpointAddress destination) {
            MessageElement chat = msg.getMessageElement("Chat");
           
            
        }

        public Message filterMessage(Message msg, EndpointAddress ea, EndpointAddress ea1) {
             System.out.println("##### "+ea.toURI());
             return msg;
        }
    }


}