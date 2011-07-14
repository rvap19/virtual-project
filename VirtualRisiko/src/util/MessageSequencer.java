/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxta.communication.VirtualCommunicator;
import jxta.communication.messages.RetrasmissionRequest;
import jxta.communication.messages.VirtualRisikoMessage;
import jxta.communication.messages.listener.RetrasmissionListener;
import net.jxta.endpoint.Message;

/**
 *
 * @author root
 */
public class MessageSequencer {
    private AtomicInteger currentMSG_ID;
    private Message[] buffer;
    //private int currentMessageID;
    private VirtualRisikoMessageNotifier notifier;
    private Lock lock;
    private boolean enabled;
    private boolean permitRetrasmissionRequest=true;

    private String myPlayername;
    public MessageSequencer(String player,int bufSize){
        this.myPlayername=player;
      //  currentMessageID=0;
        currentMSG_ID=new AtomicInteger(0);
        enabled=true;
        buffer=new Message[bufSize];
        lock=new ReentrantLock(true);

        for(int i=0;i<buffer.length;i++){
            buffer[i]=null;
        }
    }

    public void setCurrentMessageID(int id){
        this.currentMSG_ID.set(id);
    }

    public boolean isPermitRetrasmissionRequest() {
        return permitRetrasmissionRequest;
    }

    public void setPermitRetrasmissionRequest(boolean permitRetrasmissionRequest) {
        this.permitRetrasmissionRequest = permitRetrasmissionRequest;
    }



    public int getCurrentMessageID(){
        return this.currentMSG_ID.get();
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

    public AtomicInteger getCurrentMSG_ID() {
        return currentMSG_ID;
    }

    

    

    public void insertMessage(Message message)throws NumberFormatException{

        int i=0;
        String type=message.getMessageElement(VirtualRisikoMessage.namespace,VirtualRisikoMessage.TYPE).toString();
        String player=message.getMessageElement(VirtualRisikoMessage.namespace,VirtualRisikoMessage.GAMER).toString();
        
        i=new Integer(message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.ID_MSG).toString()).intValue();
       
       // System.out.println("Waiting for ::: "+currentMessageID);
        System.out.println("Waiting for ::: "+currentMSG_ID.get());

        System.out.println("@@@@ NEW MESSAGE RECEIVED ::: "+type+" FROM "+player+" MSG_ID "+i);


        if(player.equals(myPlayername)){

                return;
            }

        if(type.equalsIgnoreCase(VirtualRisikoMessage.RETRASMIT_REQUEST)){
            RetrasmissionRequest request=new RetrasmissionRequest(message);
            int index=request.getMessageID();
            Message msg=buffer[index%buffer.length];
             String msgType=msg.getMessageElement(VirtualRisikoMessage.namespace,VirtualRisikoMessage.TYPE).toString();
            int ID=Integer.parseInt(message.getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.ID_MSG).toString());
            if(ID==index&&(!msgType.equals(VirtualRisikoMessage.INIT))&&(!msgType.equals(VirtualRisikoMessage.RECOVERY))){
                try {
                    VirtualCommunicator.getInstance().sendMessage(msg,true);
                    System.out.println(" @ inviata ritrasmissione per "+ID);
                } catch (IOException ex) {
                    System.out.println("impossibile inviare ritrasmissione per "+ID);
                }
                return;
            }

           // this.notifier.notifyMessage(message, i);
            return;

        }

        if(type.equals(VirtualRisikoMessage.INIT)){
            System.out.println("# connessione con msg id "+i);
            this.permitRetrasmissionRequest=true;
         //   this.currentMessageID=i;
            this.currentMSG_ID.set(i);
            notifyMessage(i,message);
            return;
        }

        if(type.equals(VirtualRisikoMessage.RECOVERY)){
            System.out.println("## riconnessione con msg id "+i);
            this.permitRetrasmissionRequest=true;
           // this.currentMessageID=i-1;
            this.currentMSG_ID.decrementAndGet();
            notifyMessage(i,message);
            return;
        }





        if(i>currentMSG_ID.get()&&permitRetrasmissionRequest){
                RetrasmissionRequest retrasmit=new RetrasmissionRequest(currentMSG_ID.get());
                try {
                    VirtualCommunicator.getInstance().sendMessage(retrasmit,false);
                    System.out.println("## inviata richiesta ritrasmissione per "+currentMSG_ID.get());

                } catch (IOException ex) {
                    System.out.println("###### impossibile richiedere ritrasmissione per messaggio "+currentMSG_ID.get());
                }
                
            }


        if(!enabled){
            this.notifier.notifyMessage(message,0);
            return;
        }

        
        





        if(type.equals(VirtualRisikoMessage.CHAT)||type.equals(VirtualRisikoMessage.PING)||type.equals(VirtualRisikoMessage.PONG)||type.equals(VirtualRisikoMessage.ACK)||type.equals(VirtualRisikoMessage.STATUS)){
            this.notifier.notifyMessage(message, 0);
            return;
        }

        
            try{
                lock.lock();
                buffer[i%buffer.length]=message;
                System.out.println("Messaggio "+i+" bufferizzato");
            }finally{
                lock.unlock();
            }
            

            if(currentMSG_ID.get()==i){

                notifyMessage(i,message);

            }
            
        
    }

    private void notifyMessage(int i,Message message){
        try{
            lock.lock();
            
            this.notifier.notifyMessage(message,currentMSG_ID.getAndIncrement());
            
            System.out.println("Messaggio "+i+" notificato");
            //currentMessageID++;
            
            int position=currentMSG_ID.get()%buffer.length;
            while(buffer[position]!=null && Integer.parseInt(buffer[position].getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.ID_MSG).toString())==currentMSG_ID.get()){
                
                
                    this.notifier.notifyMessage(buffer[position],currentMSG_ID.getAndIncrement());
                    System.out.println("notificato msg "+currentMSG_ID.get()+" e rimosso del buffer");
                    
                    position=currentMSG_ID.get()%buffer.length;
                /*else{
                    buffer[position]=null;
                }*/
            }
        }finally{
            lock.unlock();
        }
    }

  /*   private void notifyMessage(int i,Message message){
        try{
            lock.lock();
            this.notifier.notifyMessage(message,currentMessageID);
            System.out.println("Messaggio "+i+" notificato");
            currentMessageID++;

            int position=currentMessageID%buffer.length;
            while(buffer[position]!=null && ){
                int ID=-1;
                try{
                    ID=Integer.parseInt(buffer[position].getMessageElement(VirtualRisikoMessage.namespace, VirtualRisikoMessage.ID_MSG).toString());
                }catch(Exception ex){
                    System.out.println("id msg format error");
                }
                if(ID==currentMessageID){
                    this.notifier.notifyMessage(buffer[position],currentMessageID);
                    System.out.println("notificato msg "+currentMessageID+" e rimosso del buffer");
                    currentMessageID++;
                    position=currentMessageID%buffer.length;
                }/*else{
                    buffer[position]=null;
                }*/
         //   }
      /*  }finally{
            lock.unlock();
        }
    }*/

    public void bufferize(Message message, int currentMessageID) {
       try{
                lock.lock();
                buffer[currentMessageID%buffer.length]=message;
            }finally{
                lock.unlock();
            }
    }

    

    

}
