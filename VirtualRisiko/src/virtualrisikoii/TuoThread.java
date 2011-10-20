/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii;

import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class TuoThread extends Thread {
   private JFrame finestra;
   public TuoThread(JFrame finestra, long time) {
   this.finestra = finestra;
   this.time=time;
   }
    @Override
   public void run() {

      try {
         sleep(time);
         if (finestra != null) {
                finestra.dispose();
            }   // Chiudo la finestra
      } catch (Exception e) {}
   }
    private long time;
}
