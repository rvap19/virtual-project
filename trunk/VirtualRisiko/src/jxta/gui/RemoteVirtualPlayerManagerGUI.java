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
import java.util.logging.LogManager;
import javax.swing.JOptionPane;
import net.jxta.exception.PeerGroupException;
import services.RemoteVirtualPlayerManager;
import services.VirtualPlayerManager;
import virtualrisikoii.GameDetailsFrame;

/**
 *
 * @author root
 */
public class RemoteVirtualPlayerManagerGUI extends VirtualPlayerManagerGUI implements PlayerOperations{

    private boolean registred=false;

    public RemoteVirtualPlayerManagerGUI(VirtualPlayerManager virtualPlayermanager) throws IOException, PeerGroupException {

       super(virtualPlayermanager);
       super.local=false;
       LogManager.getLogManager().reset();
       setListeners();
       managePreviuosGame();
    }

    private void managePreviuosGame() throws IOException{
        RemoteVirtualPlayerManager manager=((RemoteVirtualPlayerManager)virtualPlayerManager);
        PartitaInfo info=manager.getPreviousGame();
        if(info!=null){
            if(manager.isManager(info)){
                //joptionpane for eleminazione partita
                int result=JOptionPane.showConfirmDialog(rootPane, "Gli iscritti alla partita "+info.name+" sono tutti offline...eliminare la partita ?", "VirtualRisiko info", JOptionPane.OK_CANCEL_OPTION);
                if(result==JOptionPane.OK_OPTION){
                    manager.deletePreviousGame();
                }
            }else{
                //joptonpane per leiminazione registrazione
                int result=JOptionPane.showConfirmDialog(rootPane, "E' stata trovata una precedente registrazione alla partita "+info.name+" ...eliminare registrazione ?", "VirtualRisiko info", JOptionPane.OK_CANCEL_OPTION);
                if(result==JOptionPane.OK_OPTION){
                    manager.deletePreviousRegistration();
                }else{
                    if(manager.registerInGame(info.name)){
                        registred=true;
                    }else{
                        JOptionPane.showConfirmDialog(rootPane, "Impossibile partecipare alla partita "+info.name+" ...chidere l'applicazione", "VirtualRisiko info", JOptionPane.ERROR_MESSAGE);
                        System.exit(-1);
                    }
                }
            }
        }
    }

    public boolean isRegistred() {
        return registred;
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

    @Override
    protected void selectList() {
        super.selectList();
        if(super.gamesList.getSelectedValue()==null){
            return;
        }
        String name=super.gamesList.getSelectedValue().toString();

        PartitaInfo info=((RemoteVirtualPlayerManager)virtualPlayerManager).getPartitaInfo(name);
        GameDetailsFrame details=new GameDetailsFrame(this, true);
        details.getPlayersNumberLabel().setText(Integer.toString(info.playersNumber));
        details.getPeermanagerLabel().setText(info.managerUsername);
        details.getNameLabel().setText(info.name);
        details.getMapNameLabel().setText(info.type);
        details.getMaxPlayersLabel().setText(Integer.toString(info.maxPlayers));
        details.getMaxTurnsLabel().setText(Integer.toString(info.maxTurns));
        details.setVisible(true);

        if(info.phase<0){
            details.getTornamentLabel().setText("###");
            details.getPhaseLabel().setText("###");
        }else{
            details.getTornamentLabel().setText(info.tournament);
            details.getPhaseLabel().setText(Short.toString(info.phase));
        }

    }





}
