/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JMockTest;

import java.util.ArrayList;

import java.util.List;


import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import virtualrisikoii.risiko.Giocatore;




 @RunWith(JMock.class)
public class TavoloJmockTest4 {

     Mockery context_1 = new Mockery();

     @Test
     public void testAttaccoJMock() throws Exception
	 {
         //setUp

         final JmockTavolo mockTavolo_1 = context_1.mock( JmockTavolo.class );



         final Giocatore g1= new Giocatore(1);
         final Giocatore g2= new Giocatore(2);
         final Giocatore g3= new Giocatore(3);

         g1.setUsername("Forgione");
         g2.setUsername("Memmolo");
         g3.setUsername("Maffia");


        final List <Giocatore> giocatori = new ArrayList<Giocatore>();
        giocatori.add(g1);
        giocatori.add(g2);
        giocatori.add(g3);



               //Expectation

          context_1.checking( new Expectations()
		 {
			 {
				 oneOf( mockTavolo_1 ).getGiocatori();

				 will( returnValue(giocatori));

			 }
		 });

         //Excution

                  List <Giocatore> player = mockTavolo_1.getGiocatori();
                  Giocatore player1= player.get(0);
                  Giocatore player2 = player.get(1);
                  Giocatore player3 = player.get(2);



                 Assert.assertEquals(player1.getUsername(), "Forgione");
                 Assert.assertEquals(player2.getUsername(), "Memmolo");
                 Assert.assertEquals(player3.getUsername(), "Maffia");

     }

}
