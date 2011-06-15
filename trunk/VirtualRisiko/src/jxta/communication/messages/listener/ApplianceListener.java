/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.ApplianceMessage;

/**
 *
 * @author root
 */
public interface ApplianceListener {
    

    public void updateAppliance(ApplianceMessage m);

}
