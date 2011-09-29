/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TestPackage;

import java.util.ArrayList;
import java.util.List;
import virtualrisikoii.risiko.Carta;
import org.junit.*;
import services.CardChangeController;
import virtualrisikoii.risiko.Giocatore;
import static org.junit.Assert.*;




/**
 *
 * @author Administrator
 */
public class CardChangeControllerTest {
   private Carta c;
   private List<Carta> listCarta;

   @Before
   public void setUp(){
   c = new Carta();
   c.setCodice(Carta.JOLLY);
   listCarta = new ArrayList<Carta>();
   listCarta.add(c);
    }

    @Test
     public void testHasJolly(){
         CardChangeController ccc= new CardChangeController();
         Giocatore g=new Giocatore(0);
         g.addCarta(c);
         ccc.setGiocatore(g);
         assertTrue(ccc.hasJolly());
    }

    
}
