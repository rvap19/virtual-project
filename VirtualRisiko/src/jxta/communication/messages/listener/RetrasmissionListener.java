/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.communication.messages.listener;

import jxta.communication.messages.RetrasmissionRequest;
import net.jxta.endpoint.Message;


/**
 *
 * @author root
 */
public interface RetrasmissionListener {
    public Message notifyRetrasmissionRequest(RetrasmissionRequest request);

}
