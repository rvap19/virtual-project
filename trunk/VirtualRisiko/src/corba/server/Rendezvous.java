/*
* Copyright (c) 2006 Mark Petrovic <mspetrovic@gmail.com>
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
* Original Author: Mark Petrovic <mspetrovic@gmail.com>
* */

package corba.server;

import java.io.File;
import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import jxta.advertisement.GameAdvertisement;
import jxta.advertisement.PlayerAdvertisement;
import jxta.advertisement.RegistrationAdvertisement;

import net.jxta.discovery.DiscoveryService;
import net.jxta.document.AdvertisementFactory;


import net.jxta.exception.PeerGroupException;


import net.jxta.peergroup.PeerGroup;

import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;


import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousListener;


public class Rendezvous implements RendezvousListener {
   RendezVousService     netpgRendezvous;
   private               String               jxtaHome;
   private               PeerGroup            netPeerGroup;
   private               DiscoveryService     discovery;
   private               NetworkConfigurator  configurator;

   // our chosen peer ID
   private               String               peerID = "urn:jxta:uuid-79B6A084D3264DF8B641867D926C48D9F8BA10F44BA74475ABE2BB568892B0DC03";

   
    private NetworkManager MyNetworkManager;

   // ---------------------------------------------------------------------------------

   public Rendezvous() {
      jxtaHome = ".jxta";
      System.setProperty("JXTA_HOME", jxtaHome);
      
      System.out.println("Private " + this.getClass().getName() + " Starting");
   }

   // --------------------------------------------------------------------------------

   public void start() {
      try {
         configureJXTA();
         startJXTA();
         waitForQuit();
      }
      catch (PeerGroupException e) {
         e.printStackTrace();
       
         System.exit(0);
      }
      catch (Exception e) {
         
         e.printStackTrace();
         System.exit(0);
      }
   }

   // -----------------------------------------------------------------------------------------

   private void startJXTA() throws PeerGroupException, Exception {
      System.out.println("Starting JXTA platform");

      

      netPeerGroup   = MyNetworkManager.startNetwork();

      // The rendezvous service for NetPeerGroup
      netpgRendezvous = netPeerGroup.getRendezVousService();
      netpgRendezvous.addListener(this);
      netpgRendezvous.startRendezVous();

      // The NetPeerGroup discovery service 
      discovery = netPeerGroup.getDiscoveryService();

      System.out.println("Platform started");
   }

  

   private void configureJXTA() throws IOException {
      System.out.println("Configuring platform");

      AdvertisementFactory.registerAdvertisementInstance(
                PlayerAdvertisement.getAdvertisementType(),
                new PlayerAdvertisement.Instantiator());

        AdvertisementFactory.registerAdvertisementInstance(
                GameAdvertisement.getAdvertisementType(),
                new GameAdvertisement.Instantiator());

        AdvertisementFactory.registerAdvertisementInstance(
                RegistrationAdvertisement.getAdvertisementType(),
                new RegistrationAdvertisement.Instantiator());

        File ConfigurationFile=new File(new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile()+ System.getProperty("file.separator") + "config_file"+ System.getProperty("file.separator") + "SERVER");

            NetworkManager.RecursiveDelete(ConfigurationFile);
         MyNetworkManager = new NetworkManager(NetworkManager.ConfigMode.RENDEZVOUS_RELAY, "SERVER", ConfigurationFile.toURI());


      configurator = MyNetworkManager.getConfigurator();
      configurator.setHome(ConfigurationFile);
      configurator.setPeerId(peerID);
      configurator.setName("My SERVER Name");
      configurator.setPrincipal("ofno");
      configurator.setPassword("consequence");
      configurator.setDescription("Risiko Rendezvous");
      configurator.setUseMulticast(false);

      try {
         String rdvSeedingURI="";
         String relaySeedingURI="";
         configurator.addRdvSeedingURI(new URI(rdvSeedingURI));
         configurator.addRelaySeedingURI(new URI(relaySeedingURI));
         configurator.setMode(NetworkConfigurator.RDV_SERVER + NetworkConfigurator.RELAY_SERVER);

         configurator.setUseOnlyRelaySeeds(true);
         configurator.setUseOnlyRendezvousSeeds(true);

         configurator.setTcpEnabled(true);
         configurator.setTcpIncoming(true);
         configurator.setTcpOutgoing(true);
          configurator.setTcpPort(Server.TCP_PORT);
      }
      catch(URISyntaxException e) {
         e.printStackTrace();
         System.exit(1);
      }

      // Configure the private sandbox network
      System.out.println("Configuring private net");
      /*
      // XOXO:  In a future release, this will probably be supported, with the two-arg 
      // constructor for NetPeerGroupFactory().  Leave it for reference purposes.
      configurator.setInfrastructureID(rtProps.getProperty("NetPeerGroupID"));
      configurator.setInfrastructureName(rtProps.getProperty("NetPeerGroupName"));
      configurator.setInfrastructureDescription(rtProps.getProperty("NetPeerGroupDesc"));
      */
      
      try {
         configurator.save();
      }
      catch(IOException e) {
         e.printStackTrace();
         System.exit(1);
      }

      System.out.println("Platform configured and saved");
   }

   // -----------------------------------------------------------------------------------------

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
                     + eventDescription + " from peer = " + event.getPeer());
   }

   synchronized public void waitForQuit() {
         try {
            wait();
         }
         catch (InterruptedException e) {
            e.printStackTrace();
         }
   }

   public static void main(String[] args) {
      Rendezvous rdv = new Rendezvous();
      rdv.start();
   }

}
