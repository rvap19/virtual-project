/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import corba.PartitaInfo;
import corba.RegistrationInfo;
import corba.RisikoServerPOA;
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
        if(user!=null){
            user.setLogged(true);
            try {
                this.userDAO.edit(user);

            } catch (IllegalOrphanException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RisikoServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        UserInfo info= CorbaUtil.createUserInfo(user);
        this.users.put(usrname, info);
        return info;
    }

    public PartitaInfo[] getAvailableGames() {
        List<Game> games=gameDAO.findActiveGames();
        int size=games.size();

        PartitaInfo[] result=new PartitaInfo[size];
        for(int i=0;i<size;i++){
            result[i]=CorbaUtil.createPartitaInfo(games.get(i));
        }
        return result;
    }

    public PartitaInfo getCurrentGame(UserInfo player) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean saveResult(RegistrationInfo[] results) {
        
        int size=results.length;
        boolean result=true;
        User user;
        Gameregistration registration;
        for(int i=0;i<size;i++){
            user=this.userDAO.findUserByUsername(results[i].username);
            user.setLogged(false);
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

    public PartitaInfo createGame(UserInfo u, short maxTurns, short maxPlayers, String name) {
        Game g=new Game();
        g.setAttiva(true);
        g.setDataCreazione(new Date());
        g.setInizio(new Date());
        g.setNome(name);
        g.setNumeroGiocatoriMax((int)maxPlayers);
        g.setNumeroTurniMax(maxTurns);
        this.gameDAO.create(g);
        return CorbaUtil.createPartitaInfo(g);
    }

    public boolean signPlayer(UserInfo player, PartitaInfo partita) {

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

}
