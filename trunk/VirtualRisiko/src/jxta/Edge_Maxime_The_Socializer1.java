/*
 * Copyright (c) 2010 DawningStreams, Inc.  All rights reserved.
 *  
 *  Redistribution and use in source and binary forms, with or without 
 *  modification, are permitted provided that the following conditions are met:
 *  
 *  1. Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *  
 *  2. Redistributions in binary form must reproduce the above copyright notice, 
 *     this list of conditions and the following disclaimer in the documentation 
 *     and/or other materials provided with the distribution.
 *  
 *  3. The end-user documentation included with the redistribution, if any, must 
 *     include the following acknowledgment: "This product includes software 
 *     developed by DawningStreams, Inc." 
 *     Alternately, this acknowledgment may appear in the software itself, if 
 *     and wherever such third-party acknowledgments normally appear.
 *  
 *  4. The name "DawningStreams,Inc." must not be used to endorse or promote
 *     products derived from this software without prior written permission.
 *     For written permission, please contact DawningStreams,Inc. at 
 *     http://www.dawningstreams.com.
 *  
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 *  DAWNINGSTREAMS, INC OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 *  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  
 *  DawningStreams is a registered trademark of DawningStreams, Inc. in the United 
 *  States and other countries.
 *  
 */

package jxta;


import jxta.discover.PlayerPresenceDiscover;
import jxta.listener.PlayerListener;
import jxta.advertisement.PlayerAdvertisement;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import jxta.advertisement.GameAdvertisement;
import jxta.discover.GameDiscover;
import jxta.listener.GameListener;
import net.jxta.document.AdvertisementFactory;
import net.jxta.exception.PeerGroupException;
import net.jxta.id.IDFactory;
import net.jxta.peer.PeerID;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupID;
import net.jxta.platform.NetworkConfigurator;
import net.jxta.platform.NetworkManager;

public class Edge_Maxime_The_Socializer1 implements PlayerListener,GameListener {
    

    public static final String Name = "raffaele";

    public static final int TcpPort = 9721;
    public static final PeerID PID = IDFactory.newPeerID(PeerGroupID.defaultNetPeerGroupID, Name.getBytes());
    public static final File ConfigurationFile = new File(new File(".").getAbsoluteFile().getParentFile().getParentFile().getParentFile()+ System.getProperty("file.separator") + Name);


    private HashMap<String,PlayerAdvertisement> players;
    private HashMap<String,GameAdvertisement> games;

    private PeerGroup NetPeerGroup;
    private PlayerPresenceDiscover playerDiscover;
    private GameDiscover gameDiscover;
    private NetworkManager MyNetworkManager;

    public void init() throws IOException, PeerGroupException{
        
            
            // Removing any existing configuration?
            Tools.CheckForExistingConfigurationDeletion(Name, ConfigurationFile);

            // Creation of the network manager
             MyNetworkManager = new NetworkManager(NetworkManager.ConfigMode.EDGE,
                    Name, ConfigurationFile.toURI());


            // Retrieving the network configurator
            NetworkConfigurator MyNetworkConfigurator = MyNetworkManager.getConfigurator();

            // Checking if RendezVous_Jack should be a seed


            // Setting Configuration
            MyNetworkConfigurator.setTcpPort(TcpPort);
            MyNetworkConfigurator.setTcpEnabled(true);
            MyNetworkConfigurator.setTcpIncoming(true);
            MyNetworkConfigurator.setTcpOutgoing(true);

            // Setting the Peer ID
            Tools.PopInformationMessage(Name, "Setting the peer ID to :\n\n" + PID.toString());
            MyNetworkConfigurator.setPeerID(PID);
            this.players=new HashMap<String, PlayerAdvertisement>();
            this.games=new HashMap<String, GameAdvertisement>();
            // Starting the JXTA network
            Tools.PopInformationMessage(Name, "Start the JXTA network and player discovery");
            NetPeerGroup  = MyNetworkManager.startNetwork();
             playerDiscover=new PlayerPresenceDiscover();
             playerDiscover.init(NetPeerGroup);
             playerDiscover.addPlayerListener(this);
             playerDiscover.startApp(null);

             gameDiscover=new GameDiscover();
             gameDiscover.init(NetPeerGroup);
            gameDiscover.addGameListener(this);
            gameDiscover.startApp(null);

    }

    public void findPlayers() throws PeerGroupException, IOException{
        
        
        playerDiscover.announcePresence(10, Name);
        playerDiscover.searchPlayers(true);

    }

    public void findGames() throws PeerGroupException, IOException{
        
        
        gameDiscover.searchGames(true);

    }

    public void createGame(String name,int max){
        this.gameDiscover.announceGame(max, name);
    }

    public void stop(){
        this.playerDiscover.stopApp();
        this.gameDiscover.stopApp();
        this.MyNetworkManager.stopNetwork();
    }

    public void presenceUpdated(PlayerAdvertisement playerInfo) {

        System.out.println("trovato adv per player "+playerInfo.getName());
        this.players.put(playerInfo.getPeerID(), playerInfo);
    }

     public void gameUpdated(GameAdvertisement adv) {
        
        this.games.put(adv.getCreatorID(), adv);
        String creator="SCONOSCIUTO";
        if(players.containsKey(adv.getCreatorID())){
            creator=players.get(adv.getCreatorID()).getName();
        }
        System.out.println("trovato adv per game "+adv.getGameName()+" creato da "+creator);

    }

    
    public static void main(String[] args) throws IOException, PeerGroupException, InterruptedException  {
        AdvertisementFactory.registerAdvertisementInstance(
                PlayerAdvertisement.getAdvertisementType(),
                new PlayerAdvertisement.Instantiator());

        AdvertisementFactory.registerAdvertisementInstance(
                GameAdvertisement.getAdvertisementType(),
                new GameAdvertisement.Instantiator());
        
            Edge_Maxime_The_Socializer1 socializer=new Edge_Maxime_The_Socializer1();
            socializer.init();
            socializer.findPlayers();
            socializer.createGame("scemo  ki legge", 6);


            Thread.sleep(10*1000);

            socializer.findPlayers();
            socializer.createGame("scemo  ki legge", 6);
            socializer.findGames();

            Thread.sleep(10*1000);
           
            
            
            
        

   



        
            
            
            
            
            
            
       

    }



}