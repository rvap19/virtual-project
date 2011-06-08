/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import corba.PartitaInfo;
import corba.RegistrationInfo;
import corba.UserInfo;
import java.util.Date;

/**
 *
 * @author root
 */
public class CorbaUtil {

    public static User createUnser(UserInfo info){
        if(info.username.equals("")){
            return null;
        }
        User user=new User();
        user.setUsername(info.username);
        user.setPassword(info.password);
        return user;
    }

    public static UserInfo createUserInfo(User user){
        if(user==null){
            return new UserInfo("", "", "", "", false);
        }
        UserInfo info=new UserInfo("", "", user.getUsername(), user.getPassword(), true);
        return info;
    }

    public static PartitaInfo createPartitaInfo(Game partita){
        if(partita==null){
            return new PartitaInfo(false,(short) 0, 0,(short)0,(short)0, "");
            
        }
      //  PartitaInfo info=new PartitaInfo(partita.getAttiva(),(short) partita.getGameregistrationCollection().size(),partita.getId(),(short) partita.getNumeroTurniMax(), (short)partita.getNumeroGiocatoriMax(), partita.getNome());
        PartitaInfo x=new PartitaInfo(partita.getAttiva(), (short)(partita.getGameregistrationCollection().size()), partita.getId(), (short)partita.getNumeroTurniMax(), (short)partita.getNumeroGiocatoriMax().intValue(), partita.getNome());
      //  (boolean _active, short _playersNumber, int _id, short _maxTurns, short _maxPlayers, String _name)
        return x;
    }

    public static Game createGame(PartitaInfo info){
        Game g=new Game();
        g.setAttiva(info.active);
        g.setDataCreazione(new Date());
        g.setNumeroTurniMax(info.maxTurns);
        g.setNome(info.name);
        g.setNumeroGiocatoriMax((int)info.maxPlayers);
        g.setId(info.id);

        return g;
    }

    public static Gameregistration createGameRegistration(RegistrationInfo info,User user,Game game){
        Gameregistration r=new Gameregistration();
        r.setGame(game);
        r.setPunteggio(info.score);
        r.setUser(user);
        r.setVincitore(info.victory);
        return r;
    }

    public static RegistrationInfo createRegistrationInfo(Gameregistration r){
        if(r==null){
            return new RegistrationInfo(0, "", 0, false);
        }
        RegistrationInfo info=new RegistrationInfo(r.getGame().getId(), r.getUser().getUsername(), r.getPunteggio(), r.getVincitore());
        return info;
    }

}
