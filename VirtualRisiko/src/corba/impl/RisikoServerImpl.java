/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import corba.PartitaInfo;
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

    private HashMap<String,UserInfo> users;
    private ORB orb;

    public RisikoServerImpl(){
        this.userDAO=new UserJpaController();
        this.gameDAO=new GameJpaController();
        this.registrationDAO=new GameregistrationJpaController();
        users=new HashMap<String, UserInfo>();
    }
    public UserInfo authenticate(String usrname, String pwd) {
        User user=this.userDAO.findUserByUserNamePassword(usrname, pwd);
        System.out.println("authenticate "+usrname+" "+pwd);
        if(user!=null&&!user.getConfermato()){
            user=null;
        }
        UserInfo info= CorbaUtil.createUserInfo(user);
        if(user!=null&&user.getConfermato()){
            this.users.put(usrname, info);
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
        
        int size=results.length;
        boolean result=true;
        User user;
        Gameregistration registration;
        for(int i=0;i<size;i++){
            user=this.userDAO.findUserByUsername(results[i].username);
            this.users.remove(user.getUsername());


            

             registration=this.registrationDAO.findGameregistration(new GameregistrationPK(results[i].gameID, results[i].username));
             registration.setPunteggio(results[i].score);
            registration.setVincitore(results[i].victory);
            try {

                this.registrationDAO.edit(registration);
                this.userDAO.edit(user);
            } catch (PreexistingEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
                result=false;
            } catch (Exception ex) {
                result=false;
            }
        }
        return result;
    }

   

    public synchronized boolean signPlayer(UserInfo player, PartitaInfo partita) {

        Gameregistration registration=new Gameregistration(partita.id,player.username);
        try {
            this.registrationDAO.create(registration);
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
        Collection<UserInfo> values=this.users.values();
        int size=values.size();


        UserInfo[] result=new UserInfo[size];
        Iterator<UserInfo> iter=values.iterator();
        for(int i=0;i<size;i++){
            result[i]=iter.next();
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

        Game g=this.gameDAO.findActiveGamesByManagerUsername(user.username);
        if(g!=null){
            return CorbaUtil.createPartitaInfo(null, g.getNumeroGiocatoriMax());
        }
        g=new Game();
        g.setAttiva(true);
        g.setDataCreazione(new Date());
        g.setInizio(new Date());
        g.setNome(name);
        g.setNumeroGiocatoriMax((int)maxPlayers);
        g.setNumeroTurniMax(maxTurn);
        g.setManagerUsername(user.username);
        g.setMappa(type);
        this.gameDAO.create(g);
        return CorbaUtil.createPartitaInfo(g,0);
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

 
 

}
