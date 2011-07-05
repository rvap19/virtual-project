/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.gui;

import corba.PartitaInfo;
import corba.PlayerOperations;
import corba.RegistrationInfo;
import corba.UserInfo;
import java.io.IOException;
import javax.swing.DefaultListModel;
import net.jxta.exception.PeerGroupException;
import services.RemoteVirtualPlayerManager;
import services.VirtualPlayerManager;
import virtualrisikoii.GameDialog;

/**
 *
 * @author root
 */
public class RemoteVirtualPlayerManagerGUI extends VirtualPlayerManagerGUI implements PlayerOperations{

    public RemoteVirtualPlayerManagerGUI(VirtualPlayerManager virtualPlayermanager) throws IOException, PeerGroupException {

       super(virtualPlayermanager);
    }



    public void setListeners(){
        virtualPlayerManager.getManager().addPipeListener(this);
        ((RemoteVirtualPlayerManager)virtualPlayerManager).getPlayer().setListener(this);
    }

    public UserInfo getUserInfo() {
       return  ((RemoteVirtualPlayerManager)virtualPlayerManager).getUserInfo();
    }

    public void notifyNewGame(PartitaInfo partita) {
        super.updateList(partita.name, gamesList);
        ((RemoteVirtualPlayerManager)virtualPlayerManager).notifyNewGame(partita);
    }

    public void notifyNewRegistration(RegistrationInfo registration) {
        super.updateList(registration.username, super.currentPlayersInGameList);
        ((RemoteVirtualPlayerManager)virtualPlayerManager).notifyNewRegistration(registration);
    }

    public void notifyStart(String managerName) {
        this.setVisible(false);
        ((RemoteVirtualPlayerManager)this.virtualPlayerManager).notifyStart(managerName);
    }

    public void notifyNewPlayer(UserInfo userInfo) {
        super.updateList(userInfo.username, super.allPlayersList);
        ((RemoteVirtualPlayerManager)virtualPlayerManager).notifyNewPlayer(userInfo);
    }

    public boolean isLogged() {
       return this.getUserInfo().logged;
    }

}
