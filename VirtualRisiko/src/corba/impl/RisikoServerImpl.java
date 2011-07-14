/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import corba.Authentication;
import corba.PartitaInfo;
import corba.Player;
import corba.RegistrationInfo;
import corba.RisikoServerPOA;
import corba.Summary;
import corba.SummaryPlayerInfo;
import corba.UserDetails;
import corba.UserInfo;
import corba.impl.dao.GameJpaController;
import corba.impl.dao.GameregistrationJpaController;
import corba.impl.dao.TournamentJpaController;
import corba.impl.dao.TournamentregistrationJpaController;
import corba.impl.dao.UserJpaController;
import corba.impl.dao.exceptions.IllegalOrphanException;
import corba.impl.dao.exceptions.NonexistentEntityException;
import corba.impl.dao.exceptions.PreexistingEntityException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;

/**
 *
 * @author root
 */
public class RisikoServerImpl extends RisikoServerPOA{

    public static final String usernamePasswordError="Username / password errati : riprova";
    public static final String userAuthenticated="Hai gia effettuato il login";
    public static final String userNotConfirmed="non hai confermato la tua iscrizione";
    
    private UserJpaController userDAO;
    private GameJpaController gameDAO;
    private GameregistrationJpaController registrationDAO;
    private TournamentJpaController tournamentDAO;
    private TournamentregistrationJpaController tournamentRegistrationDao;
    private HashMap<String,Player> infos;
    private ORB orb;

    
    public RisikoServerImpl(){
       LogManager.getLogManager().reset();
        this.userDAO=new UserJpaController();
        this.gameDAO=new GameJpaController();
        this.registrationDAO=new GameregistrationJpaController();
        this.tournamentDAO=new TournamentJpaController();
        this.tournamentRegistrationDao=new TournamentregistrationJpaController();
        infos=new HashMap<String, Player>();
    }
    public Authentication authenticate(String usrname, String pwd) {
        User user=this.userDAO.findUserByUserNamePassword(usrname, pwd);
        System.out.println("authenticate "+usrname+" "+pwd);
        String message="";
        if(user==null){
            message=usernamePasswordError;
        }else if((user!=null&&!user.getConfermato()) ){
            user=null;

            message=userNotConfirmed;
        }else if((infos.get(usrname)!=null)){
            user=null;
            message=userAuthenticated;
        }

        UserInfo info= CorbaUtil.createUserInfo(user);
        String currentusername="";
        try{
            if(info!=null){
                Iterator<String> keys=this.infos.keySet().iterator();
                while(keys.hasNext()){
                    currentusername=keys.next();
                    Player current=infos.get(currentusername);
                        if(current!=null){
                           current.notifyNewPlayer(info);
                        }

                }
            }
       }catch(SystemException ex){
        System.out.println("errore listener");
        infos.remove(currentusername);
       }
        
        return new Authentication(message, info);
    }

    public PartitaInfo[] getAvailableGames() {
        List<Game> games=gameDAO.findActiveGames();
        int size=games.size();

        PartitaInfo[] result=new PartitaInfo[size];
        for(int i=0;i<size;i++){
            
            Game game=games.get(i);
            
            try{
                if(this.isOnline(game.getManagerUsername())){
                    result[i]=createPartitaInfo(game);
                }else{
                    result[i]=CorbaUtil.createPartitaInfo(null, 0, "", 0);
                }
            
            }catch(Exception ex){
                
                System.err.println("manager offline");
                
            }
            
            
        }
        return result;
    }

    private PartitaInfo createPartitaInfo(Game game){
        int users=registrationDAO.findGameregistrationByPartitaID(game.getId()).size();
        if(game.getIDTorneo()!=null){
                Tournament tournament=this.tournamentDAO.findTournament(game.getIDTorneo());
                return CorbaUtil.createPartitaInfo(game,users,tournament.getNome(),game.getFaseTorneo());
            }else{
                return CorbaUtil.createPartitaInfo(game, users,"",0);
            }
    }

    public PartitaInfo getCurrentGame(UserInfo player) {
         User user=this.userDAO.findUser(player.username);

         Iterator<Gameregistration> registration=user.getGameregistrationCollection().iterator();
         while(registration.hasNext()){
             Gameregistration current=registration.next();

             if(current.getGame().getAttiva()){
                 return createPartitaInfo(current.getGame());
             }
         }
         return CorbaUtil.createPartitaInfo(null,0,"",0);
    }

    public boolean saveResult(RegistrationInfo[] results) {
        int size = results.length;
        boolean result = true;
        Gameregistration registration;
        
            for (int i = 0; i < size; i++) {
                registration = this.registrationDAO.findGameregistration(new GameregistrationPK(results[i].gameID, results[i].username));
                if (registration != null) {
                    registration.setPunteggio(results[i].score);
                    registration.setVincitore(results[i].victory);
                    try {
                        this.registrationDAO.edit(registration);
                    } catch (NonexistentEntityException ex) {
                        try {
                            this.registrationDAO.create(registration);
                        } catch (PreexistingEntityException ex1) {
                            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex1);
                        } catch (Exception ex1) {
                            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex1);
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    this.removePlayer(results[i].username);

                } else {
                    result = false;
                }
            }
            Game game = this.gameDAO.findGame(results[0].gameID);
            game.setAttiva(false);
            game.setFine(new Date());
            try {
                this.gameDAO.edit(game);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            if(game.getIDTorneo()!=null){
                this.aggiornaTorneo(game, results);
            }

       
        return result;
    }

    private void aggiornaTorneo(Game game,RegistrationInfo[] results){
        int fase=game.getFaseTorneo();
        Tournament tournament=this.tournamentDAO.findTournament(game.getIDTorneo());
        Tournamentregistration registration;
        // aggironamento ounteggi tornei
        for(int i=0;i<results.length;i++){
            registration=this.tournamentRegistrationDao.findTournamentregistration(new TournamentregistrationPK(tournament.getId(), results[i].username));
            registration.setPunteggio(registration.getPunteggio()+results[i].score);
            try {
                this.tournamentRegistrationDao.edit(registration);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(fase==1){
            //cerca la registrazione col punteggio piu alto
            Collection<Tournamentregistration> c=tournament.getTournamentregistrationCollection();
            Iterator<Tournamentregistration> iter=c.iterator();
            Tournamentregistration currentMax=null;
            while(iter.hasNext()){
                Tournamentregistration current=iter.next();
                if(currentMax==null || currentMax.getPunteggio()<current.getPunteggio()){
                    currentMax=current;
                }
            }
            currentMax.setVincitore(true);
            try {
                this.tournamentRegistrationDao.edit(currentMax);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

            
            tournament.setDataFine(new Date());
            try {
                this.tournamentDAO.edit(tournament);
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        }else{
            List<Game> games=this.gameDAO.findGamesByTorneoAndPhase(tournament.getId(), fase-1);
            int firstBest=findBestGiocatore(results, -1);
            int secondBest=findBestGiocatore(results, firstBest);

            User user1=this.userDAO.findUser(results[firstBest].username);
            User user2=this.userDAO.findUser(results[secondBest].username);

            Game g=this.findGameAvailableFor(games);
            Gameregistration gregistration=new Gameregistration();
            gregistration.setGame(g);
            gregistration.setUser(user1);
            gregistration.setPunteggio(0);
            gregistration.setVincitore(false);
            try {
                this.registrationDAO.create(gregistration);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

             g=this.findGameAvailableFor(games);
             gregistration=new Gameregistration();
            gregistration.setGame(g);
            gregistration.setUser(user2);
            gregistration.setPunteggio(0);
            gregistration.setVincitore(false);
            try {
                this.registrationDAO.create(gregistration);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }




        }

    }

    private Game findGameAvailableFor(List<Game> games){
        Iterator<Game> iter=games.iterator();
        while(iter.hasNext()){
            Game g=iter.next();
            int registrations=this.registrationDAO.findGameregistrationByPartitaID(g.getId()).size();
            if(registrations<g.getNumeroGiocatoriMax()){
                return g;
            }
        }
        return null;
    }

    private int findBestGiocatore(RegistrationInfo[] infos,int best){
        int max=infos[0].score;
        int currentBest=0;

        for(int i=1;i<infos.length;i++){
            if(infos[i].score>max && i!=best){
                max=infos[i].score;
                currentBest=i;
            }
        }
        return currentBest;
    }

    private void removePlayer(String username){
        

        this.infos.remove(username);
    }
   

    public synchronized boolean signPlayer(UserInfo player, PartitaInfo partita) {

        List list=this.registrationDAO.findGameregistrationByPartitaAndPlayer(partita.id,player.username);
        Game g=this.gameDAO.findGame(partita.id);
        if(list!=null&&list.size()==1&&g.getManagerUsername()==null){
            
            g.setManagerUsername(player.username);
            try {
                this.gameDAO.edit(g);
                
            } catch (Exception ex) {
               return false;
            }
            return true;
        }


        if(list!=null&&list.size()>0){
            return true;
        }
        Gameregistration registration=new Gameregistration();
         g=this.gameDAO.findGame(partita.id);
        User u=this.userDAO.findUser(player.username);
        registration.setGame(g);
        registration.setUser(u);
        registration.setPunteggio(0);
        registration.setVincitore(false);
        try {
            List<Gameregistration> regList=this.registrationDAO.findGameregistrationByPartitaID(partita.id);
            if(regList!=null&&regList.size()>=g.getNumeroGiocatoriMax()){
                return false;
            }
            this.registrationDAO.create(registration);
            if(regList.size()==g.getNumeroGiocatoriMax()-1){
                Player current=this.infos.get(g.getManagerUsername());
                try{
                    if(g.getIDTorneo()==null)
                       current.notifyStart(g.getManagerUsername());
                }catch(SystemException ex){
                    this.infos.remove(g.getManagerUsername());
                }
            }
            return true;
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (Exception ex) {
            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public UserInfo[] getAuthenticateUsers() {
        Collection<Player> values=this.infos.values();
        int size=values.size();


        UserInfo[] result=new UserInfo[size];
        Iterator<Player> iter=values.iterator();
        for(int i=0;i<size;i++){
            try{
                result[i]=iter.next().getUserInfo();
            }catch(SystemException ex){

            }
        }
        return result;
    }

    public void shutdown() {
        this.orb.shutdown(false);
    }

    public UserInfo[] getPlayers(PartitaInfo partita) {
        List<Gameregistration> registrations=this.registrationDAO.findGameregistrationByPartitaID(partita.id);
        int size=registrations.size();

        UserInfo[] result=new UserInfo[size];
        for(int i=0;i<size;i++){
            result[i]=CorbaUtil.createUserInfo(registrations.get(i).getUser());
        }
        return result;
    }

    public void setORB(ORB orb) {
        this.orb=orb;
    }

    public PartitaInfo createGame(UserInfo user, short maxTurn, short maxPlayers, String name, String type) {

        List result=this.gameDAO.findActiveGamesByManagerUsername(user.username);
        if(result!=null&&result.size()>0){
            return CorbaUtil.createPartitaInfo(null, -1,"",0);
        }
        Game g=new Game();
        g.setAttiva(true);
        g.setDataCreazione(new Date());
        g.setInizio(new Date());
        g.setNome(name);
        g.setNumeroGiocatoriMax((int)maxPlayers);
        g.setNumeroTurniMax(maxTurn);
        g.setManagerUsername(user.username);
        g.setMappa(type);
        this.gameDAO.create(g);
        PartitaInfo info= CorbaUtil.createPartitaInfo(g,0,"",-1);
        this.signPlayer(user, info);
        String currentUserName=null;
        Iterator<String> keys=this.infos.keySet().iterator();
        try{
            while(keys.hasNext()){
                currentUserName=keys.next();
                Player current=infos.get(currentUserName);

                
                    if(current!=null){
                       
                            current.notifyNewGame(info);
                        
                    }
                
                }
        }catch(SystemException ex){
            infos.remove(currentUserName);
        }
        return info;
    }

    public boolean createUser(UserDetails details) {
        boolean created=false;
        User user=CorbaUtil.createUser(details);
        Random random=new Random();
        user.setCodiceConferma(random.nextInt(1000000000));
        user.setConfermato(false);
        try {
            this.userDAO.create(user);
            String message=Mailer.createWelcomeMessage(user.getUsername(), user.getPassword(), Integer.toString(user.getCodiceConferma()));
            Mailer.inoltraEmail(user.getEmail(), "team@virtualrisiko.org", "welcome to the virtual risiko world", message);
            created=true;
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return created;
    }

    public boolean checkUsername(String username) {
       User user= this.userDAO.findUser(username);
       return user==null;
    }

    public boolean activateUser(String username,int codice) {
        boolean active=false;
        User user=this.userDAO.findUser(username);
        if(user!=null&&user.getCodiceConferma()==codice){
            try {
                user.setConfermato(true);
                this.userDAO.edit(user);
                active=true;
            } catch (IllegalOrphanException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return active;
    }

    public boolean sendEmailForPassword(UserInfo user) {
        User x=this.userDAO.findUser(user.username);
        if(x==null){
            return false;
        }

        String message=Mailer.createRecoveryPasswordMessage(x.getUsername(), x.getPassword());
        Mailer.inoltraEmail(x.getEmail(), "team@virtualrisiko.org", "password recovery", message);
        return true;
    }

    public SummaryPlayerInfo getStatistics(String username) {
        User user=this.userDAO.findUser(username);
        SummaryPlayerInfo info=CorbaUtil.createSummaryPlayerInfo(user);
        return info;

    }

    public Summary[] getCompleteStatistics() {
        List<User> list=this.userDAO.findUserEntities();
        int size=list.size();
        Summary[] result=new Summary[size];

        for(int i=0;i<size;i++){
            result[i]=CorbaUtil.createSummary(list.get(i));
        }
        return result;
    }

    public void registerPlayer(String username,Player player) {
       
        this.infos.put(username, player);
        System.out.println("ciao");
    }

    public boolean isOnline(String username) {
        Player player=infos.get(username);
        if(player==null){
            return false;
        }
        boolean result=false;
        try{
            result=  player.isLogged();
        }catch(Exception ex){
            result= false;
        }finally{
            return result;
        }
    }

    public PartitaInfo getActiveGame(String username) {
        List<Gameregistration> result=this.registrationDAO.findGameregistrationByPlayer(username);

        if(result==null || result.size()==0){
            return CorbaUtil.createPartitaInfo(null, 0,"",0);
        }
        Iterator<Gameregistration> iter=result.iterator();

        while(iter.hasNext()){
            Gameregistration current=iter.next();
            if(current.getGame().getAttiva()){
                return createPartitaInfo(current.getGame());
            }
        }

        return CorbaUtil.createPartitaInfo(null, 0,"",0);


    }

    public PartitaInfo getPartitaInfo(int id) {
        Game game=this.gameDAO.findGame(id);
        return createPartitaInfo(game);

    }

    public void notifyNewManager(PartitaInfo partita, String manager) {
        Game game=this.gameDAO.findGame(partita.id);
        game.setManagerUsername(manager);
        try {
            this.gameDAO.edit(game);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteRegistration(PartitaInfo partita, UserInfo user) {
        Game game=this.gameDAO.findGame(partita.id);
        if(game==null){
            return;
        }
        if(isOnline(game.getManagerUsername())){
            return;
        }
        List<Gameregistration> registrations=this.registrationDAO.findGameregistrationByPartitaAndPlayer(partita.id, user.username);
        if(registrations!=null && registrations.size()==1){
            try {
                this.registrationDAO.destroy(registrations.get(0).getGameregistrationPK());
            } catch (NonexistentEntityException ex) {
                System.out.println("impossibile eliminare registrazione di user "+user.username+" alla partita "+partita.name);
            }
        }
    }

    public void deletePartita(PartitaInfo partita, UserInfo info) {
        
        List<Gameregistration> registrations=this.registrationDAO.findGameregistrationByPartitaID(partita.id);
        boolean online=false;
        Iterator<Gameregistration> iter=registrations.iterator();
        while(iter.hasNext()){
            User current=iter.next().getUser();
            if(!current.getUsername().equals(info.username)){
                online=online || isOnline(current.getUsername());
            }
        }
        if(!online && info.username.equals(partita.managerUsername)){
            try {
                iter=registrations.iterator();
                while(iter.hasNext()){
                    Gameregistration current=iter.next();
                    this.registrationDAO.destroy(current.getGameregistrationPK());
                }
                this.gameDAO.destroy(partita.id);
            } catch (NonexistentEntityException ex) {
                 System.out.println("impossibile eliminare partita "+partita.name+" .. partita non presente");
            }
        }
    }

    public void changeManager(PartitaInfo partita, UserInfo info) {
        Game game=this.gameDAO.findGame(partita.id);
        if(game!=null){
            game.setManagerUsername(info.username);
            try {
                this.gameDAO.edit(game);
            } catch (NonexistentEntityException ex) {
                System.out.println("partita non trovata .."+partita.name+" non trovata");
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

 
 

}
