/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.messages;

/**
 *
 * @author root
 */
public interface ChatMessage extends RisikoMessage{
    public String getFrom() ;

    public String getMessageString();

    public String getTo() ;
}
