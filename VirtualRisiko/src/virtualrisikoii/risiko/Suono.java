/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

/**
 *
 * @author Administrator
 */
public class Suono {
      public static synchronized void playSound(final String url) {
    new Thread(new Runnable() {
            @Override
      public void run() {
       /* try {
          Clip clip = AudioSystem.getClip();
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(Main.class.getResourceAsStream(url));
          clip.open(inputStream);
          clip.start();
        } catch (Exception e) {
          System.err.println(e.getMessage());
        }*/
      }
    }).start();
  }

}
