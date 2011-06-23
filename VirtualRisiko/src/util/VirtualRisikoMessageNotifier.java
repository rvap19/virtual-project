/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import jxta.communication.messages.VirtualRisikoMessage;

/**
 *
 * @author root
 */
public interface VirtualRisikoMessageNotifier {

    void notifyMessage(VirtualRisikoMessage message);
}
