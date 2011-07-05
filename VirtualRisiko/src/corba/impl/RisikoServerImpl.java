/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

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
import corba.impl.dao.UserJpaController;
import corba.impl.dao.exceptions.IllegalOrphanException;
import corba.impl.dao.exceptions.NonexistentEntityException;
import corba.impl.dao.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.CORBA.ORB;

/**
 *
 * @author root
 */
public class RisikoServerImpl extends RisikoServerPOA{
    private UserJpaController userDAO;
    private GameJpaController gameDAO;
    private GameregistrationJpaController registrationDAO;

    private HashMap<String,Player> infos;
    private ORB orb;

    
    public RisikoServerImpl(){
        this.userDAO=new UserJpaController();
        this.gameDAO=new GameJpaController();
        this.registrationDAO=new GameregistrationJpaController();
        infos=new HashMap<String, Player>();
    }
    public UserInfo authenticate(String usrname, String pwd) {
        User user=this.userDAO.findUserByUserNamePassword(usrname, pwd);
        System.out.println("authenticate "+usrname+" "+pwd);
        if((user!=null&&!user.getConfermato()) ||(infos.get(usrname)!=null)){
            user=null;
        }
        UserInfo info= CorbaUtil.createUserInfo(user);
        if(info!=null){

            Iterator<String> keys=this.infos.keySet().iterator();
            while(keys.hasNext()){
                Player current=infos.get(keys.next());
                try{
                    if(current!=null){
                        
                        current.notifyNewPlayer(info);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }
        
        return info;
    }

    public PartitaInfo[] getAvailableGames() {
        List<Game> games=gameDAO.findActiveGames();
        int size=games.size();

        PartitaInfo[] result=new PartitaInfo[size];
        for(int i=0;i<size;i++){
            int users=registrationDAO.findGameregistrationByPartitaID(games.get(i).getId()).size();
            result[i]=CorbaUtil.createPartitaInfo(games.get(i),users);
        }
        return result;
    }

    public PartitaInfo getCurrentGame(UserInfo player) {
         User user=this.userDAO.findUser(player.username);

         Iterator<Gameregistration> registration=user.getGameregistrationCollection().iterator();
         while(registration.hasNext()){
             Gameregistration current=registration.next();
             if(current.getGame().getAttiva()){
                 return CorbaUtil.createPartitaInfo(current.getGame(),0);
             }
         }
         return CorbaUtil.createPartitaInfo(null,0);
    }

    public boolean saveResult(RegistrationInfo[] results) {
        int size = results.length;
        boolean result = true;
        Gameregistration registration;
        try {
            for (int i = 0; i < size; i++) {
                registration = this.registrationDAO.findGameregistration(new GameregistrationPK(results[i].gameID, results[i].username));
                if (registration != null) {
                    registration.setPunteggio(results[i].score);
                    registration.setVincitore(results[i].victory);

                        this.registrationDAO.edit(registration);
                        this.removePlayer(results[i].username);

                } else {
                    result = false;
                }
            }
            Game game = this.gameDAO.findGame(results[0].gameID);
            game.setAttiva(false);
            game.setFine(new Date());
            this.gameDAO.edit(game);

        } catch (NonexistentEntityException ex) {
            result=false;
        } catch (Exception ex) {
            result=false;
        }
        return result;
    }

    private void removePlayer(String username){
        

        this.infos.remove(username);
    }
   

    public synchronized boolean signPlayer(UserInfo player, PartitaInfo partita) {

        List list=this.registrationDAO.findGameregistrationByPartitaAndPlayer(partita.id,player.username);
        if(list!=null&&list.size()>0){
            return true;
        }
        Gameregistration registration=new Gameregistration();
        Game g=this.gameDAO.findGame(partita.id);
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
                current.notifyStart(g.getManagerUsername());
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
            result[i]=iter.next().getUserInfo();
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
            return CorbaUtil.createPartitaInfo(null, -1);
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
        PartitaInfo info= CorbaUtil.createPartitaInfo(g,0);
        this.signPlayer(user, info);
        Iterator<String> keys=this.infos.keySet().iterator();
            while(keys.hasNext()){
                Player current=infos.get(keys.next());

                try{
                    if(current!=null){
                        current.notifyNewGame(info);
                    }
                }catch(Exception ex){

                }
            }
        return info;
    }

    public boolean createUser(UserDetails details) {
        boolean created=false;
        User user=CorbaUtil.createUser(details);
        Random random=new Random();
        user.setCodiceRegistrazione(random.nextInt(1000000000));
        user.setConfermato(false);
        try {
            this.userDAO.create(user);
            String message=Mailer.createWelcomeMessage(user.getUsername(), user.getPassword(), Integer.toString(user.getCodiceRegistrazione()));
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
        if(user!=null&&user.getCodiceRegistrazione()==codice){
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
        return this.infos.get(username).isLogged();
    }

    public PartitaInfo getActiveGame(String username) {
        List<Gameregistration> result=this.registrationDAO.findGameregistrationByPlayer(username);

        if(result==null || result.size()==0){
            return CorbaUtil.createPartitaInfo(null, 0);
        }
        Iterator<Gameregistration> iter=result.iterator();

        while(iter.hasNext()){
            Gameregistration current=iter.next();
            if(current.getGame().getAttiva()){
                return CorbaUtil.createPartitaInfo(current.getGame(), 0);
            }
        }

        return CorbaUtil.createPartitaInfo(null, 0);


    }

    public PartitaInfo getPartitaInfo(int id) {
        Game game=this.gameDAO.findGame(id);
        return CorbaUtil.createPartitaInfo(game, 0);

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

 
 

}
