/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package TestErrati;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import virtualrisikoii.risiko.Carta;
import services.CardChangeController;
import virtualrisikoii.risiko.Giocatore;


/**
 *
 * @author Administrator
 */
 @RunWith(JMock.class)
public class CardChangeControllerJmokeTest {

    Mockery context_1 = new Mockery();

    @Test
    public void TestJollyJMock() throws Exception{
    //Set-up

        final JmockCardChangeController mockCardChangeCtr_1 = context_1.mock(JmockCardChangeController.class);
            final Carta c;
            final List<Carta> listCarta;

            
            
            //Expectation

		 context_1.checking( new Expectations()
		 {
			 {
				 oneOf( mockCardChangeCtr_1 ).hasJolly();
				 will(returnValue(true));

			 }
		 });


            c = new Carta();
            c.setCodice(Carta.JOLLY);
            listCarta = new ArrayList<Carta>();
            listCarta.add(c);
            CardChangeController ccc= new CardChangeController();
           Giocatore g=new Giocatore(0);
           g.addCarta(c);
           ccc.setGiocatore(g);
            boolean ris=false;
            ris = mockCardChangeCtr_1.hasJolly();
            Assert.assertEquals(ris, ccc.isJollyAvailable());






    }

}
