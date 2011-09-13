/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.messages;

/**
 *
 * @author root
 */
public interface StatusPeerMessage extends RisikoMessage{
    public int getId() ;

    public boolean isOnline() ;
}
