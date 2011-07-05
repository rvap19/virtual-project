/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import java.io.IOException;
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

    private static ConnectionHandler instance;

    private ConnectionListener connectionListener;
    private JxtaServerPipe server;

    private PeerGroup group;
    private PipeAdvertisement pipeadv;
    private boolean running;
    private int backlog;
    private int timeout;

    public static ConnectionHandler getInstance(PeerGroup group, PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
        if(instance==null){
            instance=new ConnectionHandler(group, pipeadv, backlog, timeout);
        }
        return instance;
    }
    private ConnectionHandler(PeerGroup group, PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
        this.group=group;
        this.pipeadv=pipeadv;
        this.backlog=backlog;
        this.timeout=timeout;
        running=true;
       System.err.println("parametri server settati");
    }

    public void stopServer(){
        this.running=false;
    }

    public ConnectionListener getConnectionListener() {
        return connectionListener;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }


    public void run() {
        try {
            running=true;
            server = new JxtaServerPipe(group, pipeadv, backlog, timeout);
        } catch (IOException ex) {
            System.err.println("impossibile avviare server");
            System.exit(-1);
        }
            System.err.println("server pipe avviato");
            while(running){
                JxtaBiDiPipe pipe;
                try {
                    pipe = server.accept();
                    pipe.setMaxRetryTimeout(10*1000);
                    pipe.setRetryTimeout(10*1000);
                    Message msg=pipe.getMessage(0);

                    if(running){
                        connectionListener.notifyConnection(pipe,msg);
                    }else{
                        pipe.close();
                    }
                    System.out.println("connection handler "+this.server.getPipeAdv().getName()+":: connessione accettata");
                } catch (InterruptedException ex) {
                     System.err.println("timeout su ricezione msg registrazione");
                } catch (IOException ex) {
                    System.err.println("");
                }
                
                
                
            }
        
    }

   


}
