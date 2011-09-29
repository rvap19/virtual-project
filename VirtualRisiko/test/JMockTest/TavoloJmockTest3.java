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
import virtualrisikoii.risiko.Spostamento;
import virtualrisikoii.risiko.Territorio;



/**
 *
 * @author Administrator
 */

 @RunWith(JMock.class)
public class TavoloJmockTest3 {

     Mockery context_1 = new Mockery();

     @Test
     public void testAttaccoJMock() throws Exception
	 {
         //setUp

         final JmockTavolo mockTavolo_1 = context_1.mock( JmockTavolo.class );



         final Giocatore g1= new Giocatore(1);
         final Spostamento sp= new Spostamento();

         final Territorio daTerritorio=new Territorio();
         final Territorio aTerritorio=new Territorio();

         g1.getNazioni().add(daTerritorio);
        
         g1.getNazioni().add(aTerritorio);


         daTerritorio.setOccupante(g1);
         aTerritorio.setOccupante(g1);
         daTerritorio.setNumeroUnita(5);
         aTerritorio.setNumeroUnita(3);

         sp.setTerritori(daTerritorio, aTerritorio);


               //Expectation

          context_1.checking( new Expectations()
		 {
			 {
				 oneOf( mockTavolo_1 ).preparaSpostamento(with(daTerritorio), with( aTerritorio));
				 will( returnValue(sp));

			 }
		 });

         //Excution

                 Spostamento azione= mockTavolo_1.preparaSpostamento(daTerritorio,  aTerritorio);
                 int truppe=4;
                 azione.setNumeroTruppe(truppe);
                 azione.eseguiAzione();

                 int numTruppeDaTer=daTerritorio.getNumeroUnita();
                 int numTruppeATer=aTerritorio.getNumeroUnita();

                 Assert.assertEquals(numTruppeDaTer, 1);
                  Assert.assertEquals(numTruppeATer, 7);

     }

}
