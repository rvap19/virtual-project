/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.StatusPeerMessage;

/**
 *
 * @author root
 */
public interface StatusPeerListener {
    public void updateStatus(StatusPeerMessage message);

}
