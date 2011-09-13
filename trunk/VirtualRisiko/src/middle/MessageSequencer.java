/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package middle;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import middle.messages.RetrasmissionRequestMessage;
import middle.messages.RisikoMessage;


/**
 *
 * @author root
 */
public class MessageSequencer implements RisikoMessageListener{
    private AtomicInteger currentMSG_ID;
    private RisikoMessage[] buffer;
    //private int currentMessageID;
    private RisikoMessageListener notifier;
    private Lock lock;
    private boolean enabled;
    private boolean permitRetrasmissionRequest=true;

    private String myPlayername;
    public MessageSequencer(String player,int bufSize){
        this.myPlayername=player;
      //  currentMessageID=0;
        currentMSG_ID=new AtomicInteger(0);
        enabled=true;
        buffer=new RisikoMessage[bufSize];
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

    public RisikoMessageListener getNotifier() {
        return notifier;
    }

    public void setNotifier(RisikoMessageListener notifier) {
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

    

    

    public void insertMessage(RisikoMessage message)throws NumberFormatException{

        int i=0;
        String type=message.getType();
        String player=message.playerName();
        
        i=message.getMSG_ID();
       
       // System.out.println("Waiting for ::: "+currentMessageID);
        System.out.println("Waiting for ::: "+currentMSG_ID.get());

        System.out.println("@@@@ NEW MESSAGE RECEIVED ::: "+type+" FROM "+player+" MSG_ID "+i);


        if(type.equals(MessageTypes.CHAT)){
            this.notifier.notifyMessage(message);
            return;
        }

        if(player.equals(myPlayername)){

                return;
            }

   /*     if(type.equalsIgnoreCase(MessageTypes.RETRASMISSION_REQUEST)){
            RetrasmissionRequestMessage request=new RetrasmissionRequestMessage(message);
            int index=request.getMessageID();
            RisikoMessage msg=buffer[index%buffer.length];
             String msgType=msg.getType();
            int ID=message.getMSG_ID();
            if(ID==index&&(!msgType.equals(MessageTypes.INIT))&&(!msgType.equals(MessageTypes.RECOVERY))){
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

        }*/

        if(type.equals(MessageTypes.INIT)){
            System.out.println("# connessione con msg id "+i);
            this.permitRetrasmissionRequest=true;
         //   this.currentMessageID=i;
            this.currentMSG_ID.set(i);
            notifyMessage(i,message);
            return;
        }

        if(type.equals(MessageTypes.RECOVERY)){
            System.out.println("## riconnessione con msg id "+i);
            this.permitRetrasmissionRequest=true;
           // this.currentMessageID=i-1;
            this.currentMSG_ID.set(i-1);
            notifyMessage(i,message);
            return;
        }





    /*    if(i>currentMSG_ID.get()&&permitRetrasmissionRequest){
                RetrasmissionRequest retrasmit=new RetrasmissionRequest(currentMSG_ID.get());
                try {
                    VirtualCommunicator.getInstance().sendMessage(retrasmit,false);
                    System.out.println("## inviata richiesta ritrasmissione per "+currentMSG_ID.get());

                } catch (IOException ex) {
                    System.out.println("###### impossibile richiedere ritrasmissione per messaggio "+currentMSG_ID.get());
                }
                
            }*/


        if(!enabled){
            this.notifier.notifyMessage(message);
            return;
        }

        
        





        if(type.equals(MessageTypes.CHAT)||type.equals(MessageTypes.PING)||type.equals(MessageTypes.PONG)||type.equals(MessageTypes.ACK)||type.equals(MessageTypes.STATUS_PEER)){
            this.notifier.notifyMessage(message);
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

    private void notifyMessage(int i,RisikoMessage message){
        try{
            lock.lock();
            currentMSG_ID.getAndIncrement();
            this.notifier.notifyMessage(message);
            
            System.out.println("Messaggio "+i+" notificato");
            //currentMessageID++;
            
            int position=currentMSG_ID.get()%buffer.length;
            while(buffer[position]!=null && buffer[position].getMSG_ID()==currentMSG_ID.get()){
                
                    currentMSG_ID.getAndIncrement();
                    this.notifier.notifyMessage(buffer[position]);
                    System.out.println("notificato msg "+currentMSG_ID.get()+" e rimosso del buffer");
                    
                    position=currentMSG_ID.get()%buffer.length;
                
            }
        }finally{
            lock.unlock();
        }
    }

  

    

    public void notifyMessage(RisikoMessage message) {
        this.insertMessage(message);
    }

    public int getAndIncrementID(){
        return this.currentMSG_ID.getAndIncrement();
    }
    

    

}
