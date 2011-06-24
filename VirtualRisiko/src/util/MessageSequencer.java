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
    public MessageSequencer(int bufSize){
        currentMessageID=0;
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



    public void insertMessage(Message message){
        int i=new Integer(message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.ID_MSG).toString()).intValue();
        if(currentMessageID==i){
            notifyMessage(message);
        }else{
            try{
                lock.lock();
                buffer[i%buffer.length]=message;
            }finally{
                lock.unlock();
            }
        }
    }

    private void notifyMessage(Message message){
        try{
            lock.lock();
            this.notifier.notifyMessage(message);
            currentMessageID++;
            int position=currentMessageID%buffer.length;
            while(buffer[position]!=null){
                this.notifier.notifyMessage(buffer[position]);
                buffer[position]=null;
                currentMessageID++;
                position=currentMessageID%buffer.length;
            }
        }finally{
            lock.unlock();
        }
    }

    

}
