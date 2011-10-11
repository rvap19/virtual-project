/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication;

import jxta.advertisement.JXTAPipeAdvertisement;
import java.io.IOException;
import middle.ConnectionHandler;
import middle.ConnectionListener;
import middle.PeerGroup;
import middle.management.advertisement.PipeAdvertisement;
import middle.messages.RisikoMessage;
import net.jxta.endpoint.Message;
import net.jxta.util.JxtaBiDiPipe;
import net.jxta.util.JxtaServerPipe;


/**
 *
 * @author root
 */
public class JXTAConnectionHandler extends middle.ConnectionHandler{

    
    private JxtaServerPipe server;

   

    public static ConnectionHandler getInstance(PeerGroup group, PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
        if(instance==null){
            instance=new JXTAConnectionHandler(group, pipeadv, backlog, timeout);
        }
        return (ConnectionHandler)instance;
    }
    private JXTAConnectionHandler(middle.PeerGroup group, middle.management.advertisement.PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
        super(group,pipeadv,backlog,timeout);
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
            JXTAPeerGroup pG=(JXTAPeerGroup) super.group;
            JXTAPipeAdvertisement pA=(JXTAPipeAdvertisement) super.pipeadv;
            server = new JxtaServerPipe(pG.getPeerGroup(), pA.getPipe(), backlog, timeout);
            server.bind(pG.getPeerGroup(), pA.getPipe(), backlog);
        } catch (IOException ex) {
            System.err.println("impossibile avviare server");
            System.exit(-1);
        }
            System.err.println("server pipe avviato");
            while(running){
                JxtaBiDiPipe pipe;
                try {
                    pipe = server.accept();
                    System.out.println("connection handler <-> connessione accettata");
                    pipe.setMaxRetryTimeout(10*1000);
                    pipe.setRetryTimeout(10*1000);
                    Message msg=pipe.getMessage(0);
                    RisikoMessage message=MessageUtil.messageCreator(msg);
                    if(running){
                        JXTARisikoPipe risikoPipe=new JXTARisikoPipe();
                        pipe.setMessageListener(risikoPipe);
                        risikoPipe.setPipe(pipe);
                        connectionListener.notifyConnection(risikoPipe,message);
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
