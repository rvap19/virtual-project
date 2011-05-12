/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta;

import net.jxta.protocol.PipeAdvertisement;

/**
 *
 * @author root
 */
public interface PipeListener {
     public void pipeUpdated(PipeAdvertisement pipeInfo);
}
