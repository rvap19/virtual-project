/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.AttackMessage;

/**
 *
 * @author root
 */
public interface AttackListener {
    

    public void updateAttack(AttackMessage m);

}
