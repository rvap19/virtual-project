/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.services.impl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class TableLocker {

    
    private Lock lock;

    public TableLocker(){
        
        this.lock=new ReentrantLock(true);
    }

    public Tavolo acquireTavolo(){
        lock.lock();
        return Tavolo.getInstance();
    }

    public void releaseTavolo(){
        lock.unlock();
    }




}
