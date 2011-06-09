/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.listener.ConnectionListener;
import net.jxta.endpoint.Message;
import net.jxta.peergroup.PeerGroup;
import net.jxta.protocol.PipeAdvertisement;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;

/**
 *
 * @author root
 */
public class ConnectionHandler extends Thread{
    
    private ConnectionListener connectionListener;
    public JxtaServerPipe server;

    private PeerGroup group;
    private PipeAdvertisement pipeadv;
    private int backlog;
    private int timeout;

    public ConnectionHandler(PeerGroup group, PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
        this.group=group;
        this.pipeadv=pipeadv;
        this.backlog=backlog;
        this.timeout=timeout;
        
       System.err.println("parametri server settati");
    }

    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }


    public void run() {
       try {
            server=new JxtaServerPipe(group, pipeadv, backlog, timeout);
            System.err.println("server pipe avviato");
            while(true){
                JxtaBiDiPipe pipe=server.accept();
                System.out.println("connection handler "+this.server.getPipeAdv().getName()+":: connessione accettata");
                
                connectionListener.notifyConnection(pipe);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

   


}
