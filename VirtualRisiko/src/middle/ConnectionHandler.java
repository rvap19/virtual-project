/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle;

import middle.management.advertisement.PipeAdvertisement;
import java.io.IOException;
import middle.ConnectionListener;


/**
 *
 * @author root
 */
public  class ConnectionHandler extends Thread{

    protected static ConnectionHandler instance;

    protected ConnectionListener connectionListener;

    protected PeerGroup group;
    protected PipeAdvertisement pipeadv;
    protected boolean running;
    protected int backlog;
    protected int timeout;

    public static ConnectionHandler getInstance(PeerGroup group, PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
        if(instance==null){
            instance=new ConnectionHandler(group, pipeadv, backlog, timeout);
        }
        return instance;
    }
    protected ConnectionHandler(PeerGroup group, PipeAdvertisement pipeadv, int backlog, int timeout) throws IOException{
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


    public void init(){
        
    }
    
    public void waitForConnection(){
        
    }
    
    public void run() {
        running=true;
        while(running){
            waitForConnection();
        }
        
    }

   


}
