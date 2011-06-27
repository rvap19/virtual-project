/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import net.jxta.endpoint.Message;

/**
 *
 * @author root
 */
public interface VirtualRisikoMessageNotifier {


    public void notifyMessage(Message message, int ID);
}
