/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import java.util.logging.Level;
import java.util.logging.Logger;
import virtualrisikoii.risiko.Tavolo;

/**
 *
 * @author root
 */
public class GameTurnController  extends Thread{
    public static GameTurnController instance;
    public static long TURN_TIME=5 * 60 * 1000;

    private TimeoutListener notifier;
    private int currentTurn;
    




    public GameTurnController(TimeoutListener notifier,int initialTurn){
        this.currentTurn=initialTurn;
        this.notifier=notifier;
        
    }

    public void updateTurn(int newTurn){
        
        this.currentTurn=newTurn;
    }

    @Override
    public void run() {
        try {
            this.sleep(TURN_TIME);
        } catch (InterruptedException ex) {
            System.err.println("interrupted exception ......");
        }
        
            notifier.notifyTimeoutForTurn(currentTurn);
        

    }



}
