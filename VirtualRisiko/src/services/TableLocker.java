/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class TableLocker {

    private Tavolo tavolo;
    private Lock lock;

    public TableLocker(Tavolo tavolo){
        this.tavolo=tavolo;
        this.lock=new ReentrantLock(true);
    }

    public Tavolo acquireTavolo(){
        lock.lock();
        return tavolo;
    }

    public void releaseTavolo(){
        lock.unlock();
    }




}
