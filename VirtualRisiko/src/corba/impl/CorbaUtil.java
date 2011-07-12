/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import corba.PartitaInfo;
import corba.RegistrationInfo;
import corba.ResultInfo;
import corba.Summary;
import corba.SummaryPlayerInfo;
import corba.UserDetails;
import corba.UserInfo;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

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
            return new UserInfo("", "", false);
        }
        UserInfo info=new UserInfo(user.getUsername(), user.getPassword(), true);
        
        return info;
    }

    public static PartitaInfo createPartitaInfo(Game partita, int players, String tournament, int phase) {
        if(partita==null){
            return new PartitaInfo("", "", (short)0, 0, (short)0, (short)0, "","",(short)0);
            
        }
        String manager="";
        if(partita.getManagerUsername()!=null){
            manager=partita.getManagerUsername();
        }
        PartitaInfo x=new PartitaInfo(partita.getNome(), partita.getMappa(),(short) players, partita.getId(), (short)partita.getNumeroTurniMax(), (short)partita.getNumeroGiocatoriMax().intValue(), manager,tournament,(short)phase);
        return x;
    }

    public static Game createGame(PartitaInfo info){
        Game g=new Game();
        g.setDataCreazione(new Date());
        g.setNumeroTurniMax(info.maxTurns);
        g.setNome(info.name);
        g.setNumeroGiocatoriMax((int)info.maxPlayers);
        g.setId(info.id);
        g.setManagerUsername(info.managerUsername);
        g.setDataCreazione(new Date());
        g.setInizio(new Date());
        return g;
    }


 

    

    static SummaryPlayerInfo createSummaryPlayerInfo(User user){
        Collection<Gameregistration> collection=user.getGameregistrationCollection();
        Iterator<Gameregistration> registrations=collection.iterator();
        int size=collection.size();

        ResultInfo[] results=new ResultInfo[size];
        Gameregistration current;
        int index=0;
        while(registrations.hasNext()){
            current=registrations.next();
            results[index]=new ResultInfo(current.getGame().getNome(), current.getGame().getInizio().toString(), current.getPunteggio(), current.getVincitore());
            index++;
        }
        return new SummaryPlayerInfo(user.getUsername(), results);
    }


    static Summary createSummary(User user) {
        Collection collection=user.getGameregistrationCollection();
        int result=0;
        if(collection==null||collection.size()==0){
            return new Summary(user.getUsername(),0);
        }
        Iterator<Gameregistration> iter=collection.iterator();
        Gameregistration current;
        while(iter.hasNext()){
            current=iter.next();
            if(!current.getGame().getAttiva()){
                result=result+current.getPunteggio();
            }
        }
        return new Summary(user.getUsername(), result);
    }

    

    static User createUser(UserDetails details) {
        User user=new User(details.username);
        user.setPassword(details.password);
        user.setNome(details.name);
        user.setCognome(details.surname);
        user.setEmail(details.email);
        user.setDataDiNascita(new Date());
        user.setDataDiIscrizione(new Date());
       
        return user;
    }




    

}
