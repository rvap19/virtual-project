/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corba.impl;

import corba.PartitaInfo;
import corba.PlayerOperations;
import corba.PlayerPOA;
import corba.RegistrationInfo;
import corba.UserInfo;
import corba.client.RemotePlayerManagerGUI;

/**
 *
 * @author root
 */
public class PlayerImpl extends PlayerPOA{

    private PlayerOperations listener;
    private final UserInfo info;

    public PlayerImpl(UserInfo info){
        this.info=info;
    }

    public PlayerOperations getListener() {
        return listener;
    }

    public void setListener(PlayerOperations listener) {
        this.listener = listener;
    }




   


    public void notifyNewGame(PartitaInfo partita) {
        this.listener.notifyNewGame(partita);
    }

    public void notifyNewRegistration(RegistrationInfo registration) {
        this.listener.notifyNewRegistration(registration);
    }

    public void notifyStart(String managerName) {
        System.out.println(managerName);
       this.listener.notifyStart(managerName);
    }

    public void notifyNewPlayer(UserInfo userInfo) {
       this.listener.notifyNewPlayer(userInfo);
     System.out.println("online new User "+userInfo.username);
    }

    

    

    public UserInfo getUserInfo() {
        return info;
    }

   

}
