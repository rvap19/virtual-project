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
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Properties;

import net.jxta.discovery.DiscoveryService;

import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.XMLElement;

import net.jxta.exception.PeerGroupException;

import net.jxta.id.IDFactory;

import net.jxta.peergroup.NetPeerGroupFactory;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;

import net.jxta.platform.ModuleSpecID;
import net.jxta.platform.NetworkConfigurator;

import net.jxta.protocol.ConfigParams;
import net.jxta.protocol.ModuleImplAdvertisement;
import net.jxta.protocol.PeerGroupAdvertisement;

import net.jxta.rendezvous.RendezvousEvent;
import net.jxta.rendezvous.RendezVousService;
import net.jxta.rendezvous.RendezvousListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Rendezvous implements RendezvousListener {
   RendezVousService     netpgRendezvous;
   RendezVousService     apppgRendezvous;
   private               Logger               logger;
   private               String               jxtaHome;
   private               PeerGroup            netPeerGroup;
   private               DiscoveryService     discovery;
   private               NetworkConfigurator  configurator;

   // our chosen peer ID
   private               String               peerID = "urn:jxta:uuid-79B6A084D3264DF8B641867D926C48D9F8BA10F44BA74475ABE2BB568892B0DC03";

 

   // ---------------------------------------------------------------------------------

   public Rendezvous() {
      jxtaHome = ".jxta";
      System.setProperty("JXTA_HOME", jxtaHome);
      logger = initLogger();
      logger.info("Private " + this.getClass().getName() + " Starting");
   }

   // --------------------------------------------------------------------------------

   private void start() {
      try {
         configureJXTA();
         startJXTA();
         createPeerGroup();
         waitForQuit();
      }
      catch (PeerGroupException e) {
         e.printStackTrace();
         logger.warn("Exiting.");
         System.exit(0);
      }
      catch (Exception e) {
         logger.warn("Unable to start JXTA platform.  Exiting.");
         e.printStackTrace();
         System.exit(0);
      }
   }

   // -----------------------------------------------------------------------------------------

   private void startJXTA() throws PeerGroupException, Exception {
      logger.info("Starting JXTA platform");

    

      

      // The rendezvous service for NetPeerGroup
      netpgRendezvous = netPeerGroup.getRendezVousService();
      netpgRendezvous.addListener(this);
      netpgRendezvous.startRendezVous();

      // The NetPeerGroup discovery service 
      discovery = netPeerGroup.getDiscoveryService();

      logger.info("Platform started");
   }

   // -----------------------------------------------------------------------------------------
   
   private void createPeerGroup() throws Exception, PeerGroupException {

      // The new-application subgroup parameters.
      String name = "My App Group";
      String desc = "My App Group Description";
      String gid =  "urn:jxta:uuid-79B6A084D3264DF8B641867D926C48D902";
      String specID = "urn:jxta:uuid-309B33F10EDF48738183E3777A7C3DE9C5BFE5794E974DD99AC7D409F5686F3306";

      StringBuilder sb = new StringBuilder("=Creating group:  ");
      sb.append(name).append(", ");
      sb.append(desc).append(", ");
      sb.append(gid).append(", ");
      sb.append(specID);
      logger.info(sb.toString());

      ModuleImplAdvertisement implAdv = netPeerGroup.getAllPurposePeerGroupImplAdvertisement();
      ModuleSpecID modSpecID = (ModuleSpecID )IDFactory.fromURI(new URI(specID));
      implAdv.setModuleSpecID(modSpecID);

      // Publish the Peer Group implementation advertisement.
      discovery.publish(implAdv);
      discovery.remotePublish(null, implAdv);

      //   Create the new group using the group ID, advertisement, name, and description
      PeerGroupID groupID = (PeerGroupID )IDFactory.fromURI(new URI(gid));
      PeerGroup newGroup = netPeerGroup.newGroup(groupID,implAdv,name,desc);

      // Start the rendezvous for our applcation subgroup.
      apppgRendezvous = newGroup.getRendezVousService();
      apppgRendezvous.addListener(this);
      apppgRendezvous.startRendezVous();

      // Publish the group remotely.  newGroup() handles the local publishing. 
      PeerGroupAdvertisement groupAdv = newGroup.getPeerGroupAdvertisement();
      discovery.remotePublish(null, groupAdv);

      logger.info("Private Application newGroup = " + name + " created and published");
   }

   // -----------------------------------------------------------------------------------------

   private void configureJXTA() {
      logger.info("Configuring platform");
      logger.info("RDV_HOME = " + jxtaHome);

      configurator = new NetworkConfigurator();
      configurator.setHome(new File(jxtaHome));
      configurator.setPeerId(peerID);
      configurator.setName("My Peer Name");
      configurator.setPrincipal("ofno");
      configurator.setPassword("consequence");
      configurator.setDescription("Private Rendezvous");
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
      }
      catch(URISyntaxException e) {
         e.printStackTrace();
         System.exit(1);
      }

      // Configure the private sandbox network
      logger.info("Configuring private net");
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

      logger.info("Platform configured and saved");
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

         logger.info("RendezvousEvent:  event =  " 
                     + eventDescription + " from peer = " + event.getPeer());
   }

   // -----------------------------------------------------------------------------------------

   private Logger initLogger() {
      // See http://logging.apache.org/log4j/docs/manual.html
      // Allows for deepest control over Log4J config.
      System.setProperty("log4j.defaultInitOverride", "true");

      Logger logger=null;
      try {
         ClassLoader cl = this.getClass().getClassLoader();
         InputStream is = cl.getResourceAsStream("log4j.properties");
         Properties p = new Properties();
         p.load(is);
         is.close();
         org.apache.log4j.PropertyConfigurator.configure(p);
         logger = Logger.getLogger(this.getClass());
      }
      catch (Exception e) {
         e.printStackTrace();  
      }
      finally {
         return logger;
      }

   }

   // -----------------------------------------------------------------------------------------

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
