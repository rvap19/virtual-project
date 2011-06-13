/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class GameTimer extends Thread {


       public static final int ACTION=30;

   
    private TimeoutNotifier notifier;
    

    private AtomicBoolean notify;

    
    private AtomicInteger interval;


    public GameTimer(TimeoutNotifier notifier,int type){
        
        this.notifier=notifier;
        this.notify=new AtomicBoolean(true);
        this.interval=new AtomicInteger(type);
        
        
    }

    public GameTimer(TimeoutNotifier notifier){
        this(notifier,ACTION);

    }

    public int getInterval(){
        return this.interval.intValue();
    }


    
    public void stopTimer(){
        
        
        notify.set(false);
        interval.set(0);
    }

    

    public void setInterval(int newInterval){
        interval.set(newInterval);
        notify.set(true);
    }

   

    public void run(){
        
            while(interval.intValue()>0){
                try {
                    this.sleep(1 * 1000);
                } catch (InterruptedException ex) {
                    System.err.println("interrutp exception");
                }
                
                this.notifier.remaingTimeNotify(interval.intValue());
                interval.decrementAndGet();
            }

            if(notify.get()){
                notifier.timeoutNotify();
            }
            
        
    }


 



}
