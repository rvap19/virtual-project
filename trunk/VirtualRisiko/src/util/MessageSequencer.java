/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import jxta.communication.messages.VirtualRisikoMessage;

/**
 *
 * @author root
 */
public class MessageSequencer {
    private VirtualRisikoMessage[] buffer;
    private int currentMessageID;
    private VirtualRisikoMessageNotifier notifier;
    private Lock lock;
    public MessageSequencer(int bufSize){
        currentMessageID=0;
        buffer=new VirtualRisikoMessage[bufSize];
        lock=new ReentrantLock(true);
    }

    public void setCurrentMessageID(int id){
        this.currentMessageID=id;
    }

    public int getCurrentMessageID(){
        return this.currentMessageID;
    }

    public void insertMessage(VirtualRisikoMessage message){
        int i=message.getMSG_ID();
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

    private void notifyMessage(VirtualRisikoMessage message){
        try{
            lock.lock();
            this.notifier.notifyMessage(message);
            currentMessageID++;
            while(buffer[currentMessageID%buffer.length]!=null){
                this.notifier.notifyMessage(buffer[currentMessageID%buffer.length]);
                buffer[currentMessageID]=null;
                currentMessageID++;
            }
        }finally{
            lock.unlock();
        }
    }

}
