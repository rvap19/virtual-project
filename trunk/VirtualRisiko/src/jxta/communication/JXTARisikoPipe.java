/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta.communication;

import java.io.IOException;
import jxta.communication.messages.VirtualRisikoMessage;
import middle.RisikoPipe;
import middle.messages.RisikoMessage;
import net.jxta.endpoint.Message;
import net.jxta.pipe.PipeMsgEvent;
import net.jxta.pipe.PipeMsgListener;
import net.jxta.util.JxtaBiDiPipe;

/**
 *
 * @author root
 */
public class JXTARisikoPipe extends  RisikoPipe implements PipeMsgListener{
    
    private JxtaBiDiPipe pipe;
    
    
    public JXTARisikoPipe(){
        this(null);
    }
    public JXTARisikoPipe(JxtaBiDiPipe jxtaPipe){
        this.pipe=jxtaPipe;
    }

    public JxtaBiDiPipe getPipe() {
        return pipe;
    }

    public void setPipe(JxtaBiDiPipe pipe) {
        this.pipe = pipe;
    }

    
    
    protected boolean send(RisikoMessage message) {
        VirtualRisikoMessage msg=(VirtualRisikoMessage)message;
        System.out.println("invio msg "+message.getType()+" con ID "+message.getMSG_ID()+" propagation :: "+message.isPropagationMessage());
        boolean result=false;
        try{
            if(msg.isPropagationMessage()){
                result=this.pipe.sendMessage(msg.getSource());
            }else{
                result=this.pipe.sendMessage(msg); 
            }
        }catch(IOException io){
            io.printStackTrace();
        }
        
        return result;
    }

    public void close() {
        try {
            this.pipe.close();
        } catch (IOException ex) {
            
        }
    }

    

    public void pipeMsgEvent(PipeMsgEvent pme) {
        Message m=pme.getMessage();
        RisikoMessage msg=MessageUtil.messageCreator(m);
        
        if(messageNotifier!=null){
            this.messageNotifier.notifyMessage(msg);
        }
        
    }
    
    
    
}
