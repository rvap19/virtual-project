/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.messages;

/**
 *
 * @author root
 */
public interface RisikoMessage {
    public String getType();

    public String playerName();

    public int getMSG_ID();
    
    public void setPlayerName(String name);

    public void setMSG_ID(int id);
    
    public boolean isPropagationMessage();
    public void setPropagationMessage(boolean prop);

    
}
