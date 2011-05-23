/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import javax.swing.JFrame;

/**
 *
 * @author Administrator
 */
public class TuoThread extends Thread {
   private JFrame finestra;
   public TuoThread(JFrame finestra) { this.finestra = finestra; }
    @Override
   public void run() {
      try {
         sleep(15000);   // Attendo 30 secondi
         if (finestra != null) {
                finestra.dispose();
            }   // Chiudo la finestra
      } catch (Exception e) {}
   }
}
