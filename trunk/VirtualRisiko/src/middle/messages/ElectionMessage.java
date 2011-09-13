/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.messages;

/**
 *
 * @author root
 */
public interface ElectionMessage extends RisikoMessage{
    public String getPeerID() ;

    public int getCurrentTurn() ;
    
}
