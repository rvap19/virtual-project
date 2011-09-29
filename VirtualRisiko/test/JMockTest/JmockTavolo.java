/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JMockTest;


import java.util.List;
import virtualrisikoii.risiko.Continente;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Spostamento;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author Administrator
 */
public interface JmockTavolo {

    public  Mappa getMappa();

    public int[] lanciaDadi(int numdadi);

    public Spostamento preparaSpostamento(Territorio da,Territorio a);

 

     public List<Giocatore> getGiocatori();

}
