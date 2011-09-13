/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jxta.advertisement;

import middle.management.discover.DiscoveryResponseMsg;
import middle.management.discover.RisikoDiscoveryEvent;

/**
 *
 * @author root
 */
public class JXTARisikoDiscoveryEvent implements RisikoDiscoveryEvent{
    private DiscoveryResponseMsg msg;
    
    public JXTARisikoDiscoveryEvent(DiscoveryResponseMsg msg){
        this.msg=msg;
    }

    public DiscoveryResponseMsg getResponse() {
        return msg;
                
    }
    
}
