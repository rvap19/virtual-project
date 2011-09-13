/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.messages;

/**
 *
 * @author root
 */
public interface MovementMessage extends RisikoMessage{
    public int getFrom() ;

    public int getTo() ;

    public int getTroopNumber();
}
