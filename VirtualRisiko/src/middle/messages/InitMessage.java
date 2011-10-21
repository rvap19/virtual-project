/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package middle.messages;

import java.util.List;
import virtualrisikoii.util.GameParameter;

/**
 *
 * @author root
 */
public interface InitMessage extends RisikoMessage{
    public GameParameter getGameParameter();
    
    public List<String> getNames();
    
}
