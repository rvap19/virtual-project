/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle;

import java.io.IOException;
import middle.messages.RisikoMessage;

/**
 *
 * @author root
 */
public abstract class RisikoPipe {
    protected MessageSequencer sequencer;
    protected RisikoMessageListener messageNotifier;
    protected boolean isClose;
    
    public  void setRisikoMessageNotifier(RisikoMessageListener messageNotifier){
        this.messageNotifier=messageNotifier;
    }
    
    public  RisikoMessageListener getRisikoMessageNotifier(){
        return messageNotifier;
    }

    public MessageSequencer getSequencer() {
        return sequencer;
    }

    public void setSequencer(MessageSequencer sequencer) {
        this.sequencer = sequencer;
    }
    
    

    public  boolean sendMessage(RisikoMessage message) {
        if(!message.isPropagationMessage()){
            int ID=this.sequencer.getCurrentMessageID();
            message.setMSG_ID(ID);
        }
        
        
        try {
            
            this.send(message);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    protected abstract boolean send(RisikoMessage message)throws IOException;

    public abstract void close();

    public boolean isClose() {
        return isClose;
    }
    
}
