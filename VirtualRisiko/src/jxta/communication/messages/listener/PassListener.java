/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.PassMessage;

/**
 *
 * @author root
 */
public interface PassListener {
   

    public void updatePass(PassMessage m);
}
