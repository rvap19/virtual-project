/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import corba.PartitaInfo;
import corba.RisikoServer;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JOptionPane;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.PipeAdvertisement;

/**
 *
 * @author root
 */
public class RemoteElectionController extends ElectionController{
    private RisikoServer server;
    private PartitaInfo partita;

     public RemoteElectionController(String playerName,PeerGroup group,HashMap<String,PipeAdvertisement> advertisements){
        super(playerName, group, advertisements);

    }

    public RisikoServer getServer() {
        return server;
    }

    public void setServer(RisikoServer server) {
        this.server = server;
    }

    public PartitaInfo getPartita() {
        return partita;
    }

    public void setPartita(PartitaInfo partita) {
        this.partita = partita;
    }

    



    @Override
    public void startElection() throws IOException {
        System.out.println("controllo che il manager della partita sia ancora online");
        try{
            this.partita=this.server.getPartitaInfo(partita.id);
        }catch(Exception ex){
            System.err.println("Impossibile continuare partita :  server perso");
            JOptionPane.showMessageDialog(null, "Errore di rete..provare a riavviare l'applicazione", "Virtual Risiko Info", JOptionPane.ERROR_MESSAGE);

            System.exit(-1);
        }
        boolean manageronline=false;
        try{
             manageronline=this.server.isOnline(partita.managerUsername);
        }catch(Exception ex){
            
            manageronline=true;
        }
        
        if(manageronline){
            System.out.println("manager online ....elezione annullata");
            JOptionPane.showMessageDialog(null, "Errore di rete..provare a riavviare l'applicazione", "Virtual Risiko Info", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
        System.out.println("start remote election");

        super.startElection();
    }



}
