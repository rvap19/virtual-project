/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.event;

import middle.messages.RisikoMessage;

/**
 *
 * @author root
 */
public class RisikoEvent {
    private RisikoMessage source;
    protected String type;
    public RisikoEvent(RisikoMessage source){
        this.source=source;
       
    }

    public RisikoMessage getSource() {
        return source;
    }

    public String getType() {
        return type;
    }
    
    
    
    
    
}
