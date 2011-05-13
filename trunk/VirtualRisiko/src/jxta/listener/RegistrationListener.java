/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jxta.listener;

import jxta.advertisement.RegistrationAdvertisement;

/**
 *
 * @author root
 */
public interface RegistrationListener {
    public void registrationUpdated(RegistrationAdvertisement adv);

}
