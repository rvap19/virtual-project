/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.ElectionMessage;

/**
 *
 * @author root
 */
public interface ElectionListener {

    public void notifyElection(ElectionMessage msg);

}
