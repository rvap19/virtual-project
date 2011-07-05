/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import corba.PartitaInfo;
import corba.RisikoServer;
import java.io.IOException;
import java.util.HashMap;
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
        this.partita=this.server.getPartitaInfo(partita.id);
        boolean manageronline=this.server.isOnline(partita.managerUsername);
        if(manageronline){
            System.out.println("manager online ....elezione annullata");
            return;
        }
        System.out.println("start remote election");

        super.startElection();
    }



}
