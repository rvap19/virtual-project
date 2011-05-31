/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.listener;

import net.jxta.endpoint.Message;
import net.jxta.util.JxtaBiDiPipe;

/**
 *
 * @author root
 */
public interface ConnectionListener {
    public void notifyConnection( JxtaBiDiPipe pipe,Message msg);

}
