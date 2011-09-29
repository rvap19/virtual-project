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
import virtualrisikoii.risiko.Attacco;
import virtualrisikoii.risiko.Carta;
import virtualrisikoii.risiko.Continente;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Rinforzo;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;



/**
 *
 * @author Administrator
 */

 @RunWith(JMock.class)
public class TavoloJMockTest2 {

     Mockery context_1 = new Mockery();

     @Test
     public void testAttaccoJMock() throws Exception
	 {
         //setUp

         final JmockTavolo mockTavolo_1 = context_1.mock( JmockTavolo.class );
          

         final int dadi1=2;
         final int[]p=new int[dadi1];
         p[0]=6;
         p[1]=6;

         final int dadi2=2;
         final int[]p2=new int[dadi2];
         p2[0]=1;
         p2[1]=1;
         final Giocatore g1= new Giocatore(1);
         final Giocatore g2=new Giocatore(2);

         final Territorio t1=new Territorio();
         final Territorio t2=new Territorio();

         g1.getNazioni().add(t1);
         g2.getNazioni().add(t2);

         t1.setOccupante(g1);
         t2.setOccupante(g2);
         t1.setNumeroUnita(dadi1+1);
         t2.setNumeroUnita(dadi2);

         Attacco azione=new Attacco();
         azione.setTerritori(t1, t2);
         azione.setNumeroTruppe(dadi1);

          int att=azione.getNumeroTruppeInAttacco();
        int avv=azione.getNumeroTruppeAvversario();
          t1.setNumeroUnita(t1.getNumeroUnita()-att);
        t2.setNumeroUnita(t2.getNumeroUnita()-avv);

         


               //Expectation

          context_1.checking( new Expectations()
		 {
			 {
				 oneOf( mockTavolo_1 ).lanciaDadi(with(dadi1));
				 will( returnValue(p));
                                 oneOf( mockTavolo_1 ).lanciaDadi(with(dadi2));
				 will( returnValue(p2));



			 }
		 });

         //Excution
                 
                 azione.setPunteggio(mockTavolo_1.lanciaDadi(dadi1));
                 azione.setPunteggioAvversario(mockTavolo_1.lanciaDadi(dadi2));
                 azione.eseguiAzione();

                 int numTruppeAttaccante=t1.getNumeroUnita();
                 Assert.assertEquals(numTruppeAttaccante, 1);

                 int numerotruppeDifesa=t2.getNumeroUnita();
                 Assert.assertEquals(numerotruppeDifesa, 2);

                 Assert.assertEquals(t1.getOccupante(), g1);
                 Assert.assertEquals(t2.getOccupante(), g1);

                 Assert.assertTrue(azione.isVittoria());

                 Assert.assertTrue(g1.getNazioni().contains(t1));
                 Assert.assertTrue(g1.getNazioni().contains(t2));






     }

}
