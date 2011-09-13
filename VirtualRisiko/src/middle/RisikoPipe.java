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
        int ID=this.sequencer.getAndIncrementID();
        message.setMSG_ID(ID);
        
        try {
            this.send(message);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    
    protected abstract boolean send(RisikoMessage message)throws IOException;

    public abstract void close();
    
}
