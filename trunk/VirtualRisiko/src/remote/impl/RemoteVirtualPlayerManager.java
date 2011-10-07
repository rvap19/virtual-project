/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote.impl;

import domain.Game;
import domain.Gameregistration;
import domain.User;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import middle.EventTypes;
import middle.VirtualPlayerManager;
import middle.management.advertisement.GameAdvertisement;
import middle.management.advertisement.PipeAdvertisement;
import middle.management.advertisement.PlayerAdvertisement;
import middle.management.advertisement.RegistrationAdvertisement;
import middle.messages.WelcomeMessage;
import remote.Risiko;

/**
 *
 * @author root
 */
public abstract class RemoteVirtualPlayerManager extends VirtualPlayerManager{
    protected Risiko server;
    protected User myself;
    
    protected HashMap<String,User>remote_players;
    protected HashMap<String,Game>remote_games;
    protected HashMap<String,Gameregistration>remote_registrations;

    @Override
    public void init() throws IOException {
         players=new HashMap<String, PlayerAdvertisement>();
        games=new HashMap<String, GameAdvertisement>();
        registrations=new HashMap<String, RegistrationAdvertisement>();
        pipes=new HashMap<String,PipeAdvertisement>();
        
        remote_games=new HashMap<String, Game>();
        remote_players=new HashMap<String, User>();
        remote_registrations=new HashMap<String, Gameregistration>();
        
        
        middle=this.initMiddleLayer();
        messageBuilder=initMessageGenerator();
        
    }

    public User getMyself() {
        return myself;
    }

    public void setMyself(User myself) {
        this.myself = myself;
    }
    
    

    
    
    @Override
    public void clearGames() {
        super.clearGames();
        remote_games.clear();
    }

    @Override
    public void clearPlayers() {
        super.clearPlayers();
        remote_players.clear();
    }

    @Override
    public void clearRegistrations() {
        super.clearRegistrations();
        remote_registrations.clear();
    }

    @Override
    public Set<String> findGames() throws IOException {
        this.clearGames();
        if(manager!=null){
            super.findGames();
        }
        Game[] gr=server.getAvailableGames();
        for(int i=0;i<gr.length;i++){
            Game x=gr[i];
            remote_games.put(x.getNome(), x);
        }
        return remote_games.keySet();
    }

    @Override
    public Set<String> findPlayers() throws IOException {
        this.clearPlayers();
        if(manager!=null){
            super.findPlayers();
            
        }
        User[] ur=server.getAuthenticateUsers();
        for(int i=0;i<ur.length;i++){
            User x=ur[i];
            remote_players.put(x.getUsername(), x);
        }
        return remote_players.keySet();
    }

    @Override
    public Set<String> findRegistrations(String gameID) throws IOException {
        this.clearRegistrations();
        if(manager!=null){
            super.findRegistrations(gameID);
        }
        Game g=this.remote_games.get(gameID);
        if(g!=null){
            User[] gr=server.getPlayers(g);
            for(int i=0;i<gr.length;i++){
                User x=gr[i];
                Gameregistration reg=new Gameregistration(g.getId(), x.getUsername());
                remote_registrations.put(x.getUsername(), reg);
            }
        }
        return remote_registrations.keySet();
            
    }

    @Override
    public Set<String> getGames() {
        return this.remote_games.keySet();
    }

    @Override
    public Set<String> getPlayers() {
        return this.remote_players.keySet();
    }

    @Override
    public Set<String> getRegistrations() {
        return this.remote_registrations.keySet();
    }
    

    
    
    public Risiko getServer() {
        return server;
    }

    public void setServer(Risiko server) {
        this.server = server;
    }

    @Override
    public void creategame(String name, String mapName, int maxPlayers, int maxTurns) throws IOException {
        if(manager==null){
            manager=this.initPlayerManager();
            
            manager.init(null, true);
            manager.findPlayers();
            manager.findGames();
            manager.findPipes();
        }
        super.creategame(name, mapName, maxPlayers, maxTurns);
        Game game=new Game();
        game.setAttiva(true);
        game.setDataCreazione(new Date());
        game.setInizio(new Date());
        game.setManagerUsername(this.myName);
        game.setMappa(mapName);
        game.setNome(name);
        game.setNumeroGiocatoriMax(maxPlayers);
        game.setNumeroTurniMax(maxTurns);
        game=server.createGame(game);
        
        server.registerPlayer(game,myself );
        
        while(!super.startedGame){
            manager.findPipes();
            manager.findPlayers();
            manager.findGames();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public boolean registerInGame(String gamaName) throws IOException {
        Game g=this.remote_games.get(gamaName);
        if(g==null){
            return false;
        }
        if(manager==null){
            manager=this.initPlayerManager();
             InetAddress address=server.getAddressFor(g.getManagerUsername());
             System.out.println(address.getHostAddress());
            manager.init(address.getHostAddress(), false);
           //  manager.init(null, true);
            manager.findGames();
            manager.findPipes();
            manager.findPlayers();
           
        }
        
        
       
        
        Gameregistration reg=server.registerPlayer(g, myself);
        if(reg==null){
            return false;
        }
        
        String creatorName=reg.getGame().getManagerUsername();
        
        

        PipeAdvertisement creatorPipe = pipes.get(creatorName + " Pipe");
        while(creatorPipe==null){
            System.out.println("ricerca manager");
            this.manager.findPipes();
            this.manager.findPlayers();
            this.manager.findGames();
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
                System.out.println("impossibile continuare la ricerca");
                System.exit(-1);
            }
            creatorPipe = pipes.get(creatorName + " Pipe");
        }
        
        
        
        
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
    
    
    
}
