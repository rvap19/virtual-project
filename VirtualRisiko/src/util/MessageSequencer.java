/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import jxta.communication.messages.VirtualRisikoMessage;
import net.jxta.endpoint.Message;

/**
 *
 * @author root
 */
public class MessageSequencer {
    private Message[] buffer;
    private int currentMessageID;
    private VirtualRisikoMessageNotifier notifier;
    private Lock lock;
    private boolean enabled;
    public MessageSequencer(int bufSize){
        currentMessageID=0;
        enabled=true;
        buffer=new Message[bufSize];
        lock=new ReentrantLock(true);
    }

    public void setCurrentMessageID(int id){
        this.currentMessageID=id;
    }

    public int getCurrentMessageID(){
        return this.currentMessageID;
    }

    public VirtualRisikoMessageNotifier getNotifier() {
        return notifier;
    }

    public void setNotifier(VirtualRisikoMessageNotifier notifier) {
        this.notifier = notifier;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    

    public void insertMessage(Message message)throws NumberFormatException{

        int i=0;
        String type=message.getMessageElement(VirtualRisikoMessage.namespace,VirtualRisikoMessage.TYPE).toString();
        String player=message.getMessageElement(VirtualRisikoMessage.namespace,VirtualRisikoMessage.GAMER).toString();
        
        i=new Integer(message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.ID_MSG).toString()).intValue();
       
        System.out.println("Waiting for ::: "+currentMessageID);

        System.out.println("@@@@ NEW MESSAGE RECEIVED ::: "+type+" FROM "+player+" MSG_ID "+i);
        
        




        if(!enabled){
            this.notifier.notifyMessage(message,0);
            return;
        }




        if(type.equals(VirtualRisikoMessage.CHAT)||type.equals(VirtualRisikoMessage.PING)||type.equals(VirtualRisikoMessage.PONG)||type.equals(VirtualRisikoMessage.ACK)||type.equals(VirtualRisikoMessage.STATUS)){
            this.notifier.notifyMessage(message, 0);
            return;
        }
        if(type.equals(VirtualRisikoMessage.RECOVERY)||type.equals(VirtualRisikoMessage.INIT)){
            this.currentMessageID=i;
            notifyMessage(message);
            return;
        }

        if(currentMessageID==i){
            
            notifyMessage(message);
            System.out.println("Messaggio "+i+" notificato");
        }else{
            try{
                lock.lock();
                buffer[i%buffer.length]=message;
            }finally{
                lock.unlock();
            }
            System.out.println("Messaggio "+i+" bufferizzato");
        }
    }

    private void notifyMessage(Message message){
        try{
            lock.lock();
            this.notifier.notifyMessage(message,currentMessageID);
            currentMessageID++;
            int position=currentMessageID%buffer.length;
            while(buffer[position]!=null){
                this.notifier.notifyMessage(buffer[position],currentMessageID);
                buffer[position]=null;
                currentMessageID++;
                position=currentMessageID%buffer.length;
            }
        }finally{
            lock.unlock();
        }
    }

    

}
