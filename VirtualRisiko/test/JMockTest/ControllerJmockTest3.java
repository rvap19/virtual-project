/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package JMockTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jxta.JXTAMiddle;
import jxta.communication.messages.ApplianceMessage;
import middle.event.ApplianceEvent;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import services.GameController;
import services.HistoryListener;
import services.MapListener;
import services.PlayerDataListener;
import util.GameFactory;
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
public class ControllerJmockTest3 {

  Mockery context_1 = new Mockery();
  Mockery context_2 = new Mockery();
  Mockery context_3 = new Mockery();

  /*
   * test per la  notifica di un messaggio di disposizione armata inviato da un  giocatore cui non spetta di turno che la possibilita di disporre armate
   */
     @Test
     public void testNotifyApplianceJMock() throws Exception
	 {
         //setUp
         final GameFactory factory=new GameFactory();
         factory.loadGame("classicalMap");
         final List<String> names=new ArrayList<String>();
         names.add("cestone1");
         names.add("cestone2");
         names.add("cestone3");

         final  JXTAMiddle middle=new JXTAMiddle("cestone1");
          final Tavolo tavolo = Tavolo.createInstance(factory.getMappa() , factory.getObiettivi(), 0, 15, 3, 0, 0, 0, 0, names);
         final GameController controller=GameController.createGameController(middle);

        
         final Giocatore giocatore=tavolo.getGiocatori().get(0);
         giocatore.setNumeroTruppe(0);
         
         final MapListener mapListenerMock=context_1.mock(MapListener.class);
         final PlayerDataListener dataListenerMock=context_2.mock(PlayerDataListener.class);
         final HistoryListener historyListenerMock=context_3.mock(HistoryListener.class);

         
         final int numeroTruppeDopoMessaggio=1;
         final int idGiocatore=giocatore.getID();
         final int numerotruppeDisponibili=0;
         final int numeroTruppeDisposte=16;
         final int numeroNazioniOccupate=15;

         Mappa m=tavolo.getMappa();
         final int territorioID=giocatore.getNazioni().iterator().next().getCodice();
         


         final int numTruppeterritorioPrima=m.getTerritorio(territorioID).getNumeroUnita();
         final int numerotruppeGiocatorePrima=giocatore.getNumeroTruppe();
         final int truppe=1;
          final String message="Il giocatore rosso posiziona 1 in "+m.getTerritorio(territorioID).getNome()+" -> ERRORE :: turno 0 tavolo in init? true";
          controller.setHistoryListener(historyListenerMock);
          controller.setMapListener(mapListenerMock);
          controller.setPlayerDataListener(dataListenerMock);


               //Expectation

          context_1.checking( new Expectations()
		 {
			 {
				 never( mapListenerMock ).setLabelAttributes(with(territorioID), with(numeroTruppeDopoMessaggio), with(idGiocatore));

			 }
		 });

          context_2.checking( new Expectations()
		 {
			 {
				 never( dataListenerMock ).updateDatiGiocatore(with("giocatore rosso"), with(numerotruppeDisponibili), with(numeroTruppeDisposte),with(numeroNazioniOccupate));

			 }
		 });

           context_3.checking( new Expectations()
		 {
			 {
				 oneOf( historyListenerMock ).appendActionInHistory(message);

			 }
		 });

         //Excution

          ApplianceMessage msg=new ApplianceMessage(truppe, territorioID);

          ApplianceEvent event=new ApplianceEvent(msg);
          controller.notify(event);

          
          Assert.assertEquals(m.getTerritorio(territorioID).getNumeroUnita(), numTruppeterritorioPrima);
          Assert.assertEquals(giocatore.getNumeroTruppe(), numerotruppeGiocatorePrima);

     }

}
