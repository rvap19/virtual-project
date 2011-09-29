/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JMockTest;

import java.util.HashSet;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Continente;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Rinforzo;



/**
 *
 * @author Administrator
 */

 @RunWith(JMock.class)
public class TavoloJmockTest1 {

     Mockery context_1 = new Mockery();

     @Test
     public void testGetRinforzoJMock() throws Exception
	 {
         //setUp

         final JmockTavolo mockTavolo_1 = context_1.mock( JmockTavolo.class );

         final Continente[] continenti= new Continente[0];
         final Mappa m=new Mappa();
         m.setContinenti(continenti);
         final Giocatore g= new Giocatore(1);
         final Carta c1= new Carta(Carta.CANNONE);
         final Carta c2= new Carta(Carta.CAVALIERE);
         final Carta c3= new Carta(Carta.FANTE);
         final Carta c4 = new Carta(Carta.JOLLY);

               //Expectation

          context_1.checking( new Expectations()
		 {
			 {
				 oneOf( mockTavolo_1 ).getMappa();
				 will( returnValue(m));


			 }
		 });

         //Excution
                 g.addCarta(c1);
                 g.addCarta(c2);
                 g.addCarta(c3);
                 g.addCarta(c4);
                 Mappa mappa=mockTavolo_1.getMappa();
                 
                 int r1=Rinforzo.getRinfornzo(g, c1, c2, c3, mappa);
                 Assert.assertEquals(r1, 10);

                 int r2= Rinforzo.getRinfornzo(g, c1, c1, c1, mappa);
                 Assert.assertEquals(r2, 4);

                 int r3= Rinforzo.getRinfornzo(g, c1, c2, c2, mappa);
                 Assert.assertEquals(r3, 0);

                 int r4= Rinforzo.getRinfornzo(g, c1, c1, c4, mappa);


                 Assert.assertEquals(r4, 12);





     }

}
