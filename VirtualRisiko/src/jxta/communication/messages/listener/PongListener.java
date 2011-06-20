/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.PongMessage;

/**
 *
 * @author root
 */
public interface PongListener {
    public void notifyPong(PongMessage msg);
}
