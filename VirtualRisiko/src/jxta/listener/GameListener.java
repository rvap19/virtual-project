/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.listener;

import jxta.advertisement.GameAdvertisement;

/**
 *
 * @author root
 */
public interface GameListener {
    public void gameUpdated(GameAdvertisement adv);

}
