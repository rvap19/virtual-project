/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package remote;

import dao.GameJpaController;
import dao.GameregistrationJpaController;
import dao.UserJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import domain.Game;
import domain.Gameregistration;
import domain.User;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author root
 */
public class RisikoService implements Risiko{
    private Set<User> authenticatedUsers;

    private UserJpaController userController;
    private GameJpaController gameController;
    private GameregistrationJpaController gameRegistrationController;
    
    public RisikoService(){
        authenticatedUsers=new HashSet<User>();   
        userController=new UserJpaController();
        gameController=new GameJpaController();
        gameRegistrationController=new GameregistrationJpaController();
    }

    @Override
    public User authenticate(String username, String pwd) {
        User user=this.userController.findUserByUserNamePassword(username, pwd);
        User x=null;
        if(user.getConfermato()){
             x=new User();
             x.setUsername(username);
             this.authenticatedUsers.add(x);
        }
        return x;
        
    }

    @Override
    public Gameregistration registerPlayer(Game game, User player) {
        Game g=gameController.findGame(game.getId());
        
        User u=userController.findUser(player.getUsername());
        Gameregistration reg=null;
        List<Gameregistration> list=this.gameRegistrationController.findGameregistrationByPartitaID(g.getId());
        if(list==null || list.size()<g.getNumeroGiocatoriMax()){
            reg=new Gameregistration( g.getId(),u.getUsername());
            reg.setGame(g);
            reg.setUser(u);
            try {
                this.gameRegistrationController.create(reg);
            } catch (PreexistingEntityException ex) {
                ex.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return reg;
    }

    @Override
    public boolean isOnline(String username) {
        Iterator<User> iter=this.authenticatedUsers.iterator();
        while(iter.hasNext()){
            User current=iter.next();
            if(current.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Game[] getAvailableGames() {
        List<Game> games=gameController.findActiveGames();
        Game[] result=new Game[games.size()];
        if(games==null){
            return null;
        }
        Iterator<Game>iter=games.iterator();
        int pos=0;
        while(iter.hasNext()){
            Game g=iter.next();
            Game ng=new Game();
            ng.setAttiva(true);
            ng.setDataCreazione(g.getDataCreazione());
            ng.setFaseTorneo(g.getFaseTorneo());
            ng.setIDTorneo(g.getIDTorneo());
            ng.setId(g.getId());
            ng.setInizio(g.getInizio());
            ng.setManagerUsername(g.getManagerUsername());
            ng.setMappa(g.getMappa());
            ng.setNome(g.getNome());
            ng.setNumeroGiocatoriMax(g.getNumeroGiocatoriMax());
            ng.setNumeroTurniMax(g.getNumeroTurniMax());
            result[pos]=ng;
            pos++;
        }
        return result;
    }

    @Override
    public boolean deleteRegistration(Gameregistration reg) {
        try {
            this.gameRegistrationController.destroy(reg.getGameregistrationPK());
            return true;
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(RisikoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteGame(Game g) {
        Game game=this.gameController.findGame(g.getId());
        List<Gameregistration> registrations=this.gameRegistrationController.findGameregistrationByPartitaID(game.getId());
        boolean online=false;
        Iterator<Gameregistration> iter=registrations.iterator();
        String manager=game.getManagerUsername();
        while(iter.hasNext()){
            User current=iter.next().getUser();
            if(!current.getUsername().equals(manager)){
                online=online || isOnline(current.getUsername());
            }
        }
        
        if(!online){
            try {
                gameController.destroy(game.getId());
                return true;
            //} catch (IllegalOrphanException ex) {
              //  Logger.getLogger(RisikoService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(RisikoService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return false;
        
        
    }

    @Override
    public void changeManager(Game g, String username) {
        Game game=this.gameController.findGame(g.getId());
        if(game!=null){
            game.setManagerUsername(username);
            try {
                this.gameController.edit(game);
            } catch (NonexistentEntityException ex) {
                System.out.println("partita non trovata .."+g.getNome()+" non trovata");
            } catch (Exception ex) {
               
            }
        }
    }

    @Override
    public boolean saveResult(Gameregistration[] res) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Game createGame(Game game) {
        this.gameController.create(game);
        
        return game;
    }

    @Override
    public User[] getAuthenticateUsers() {
        User[] result=new User[this.authenticatedUsers.size()];
        Iterator<User>iter=this.authenticatedUsers.iterator();
        int pos=0;
        while(iter.hasNext()){
            result[pos]=iter.next();
            pos++;
        }
        return result;
    }

    @Override
    public User[] getPlayers(Game game) {
        Game g=this.gameController.findGame(game.getId());
        List<Gameregistration>list=this.gameRegistrationController.findGameregistrationByPartitaID(game.getId());
        if(g==null || list==null || list.size()==0){
            return null;
        }
        
        List<Gameregistration> reg=list;
        User[] result=new User[reg.size()];
        Iterator<Gameregistration> iter=reg.iterator();
        int pos=0;
        while(iter.hasNext()){
            User current=iter.next().getUser();
            User x=new User();
            x.setUsername(current.getUsername());
            result[pos]=x;
            pos++;
        }
        return result;
        
        
    }

    @Override
    public Game getGame(int id) {
        Game g=this.gameController.findGame(id);
        Game ng=new Game();
        ng.setAttiva(true);
        ng.setDataCreazione(g.getDataCreazione());
        ng.setFaseTorneo(g.getFaseTorneo());
        ng.setIDTorneo(g.getIDTorneo());
        ng.setId(g.getId());
        ng.setInizio(g.getInizio());
        ng.setManagerUsername(g.getManagerUsername());
        ng.setMappa(g.getMappa());
        ng.setNome(g.getNome());
        ng.setNumeroGiocatoriMax(g.getNumeroGiocatoriMax());
        ng.setNumeroTurniMax(g.getNumeroTurniMax());
        return ng;
    }

    @Override
    public Gameregistration getActiveRegistrationGame(String username) {
        List<Gameregistration> result=this.gameRegistrationController.findGameregistrationByPlayer(username);

        if(result==null || result.size()==0){
            return null;
        }
        Iterator<Gameregistration> iter=result.iterator();

        while(iter.hasNext()){
            Gameregistration current=iter.next();
            if(current.getGame().getAttiva()){
                
                return new Gameregistration( current.getGame().getId(),username);
                
            }
        }

        return null;
    }
    
    
    private void removePlayer(User user){


        this.authenticatedUsers.remove(user);
    }

    public InetAddress getAddressFor(String username) {
        return null;
    }
 
}
