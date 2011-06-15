/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import virtualrisikoii.RecoveryParameter;
import virtualrisikoii.risiko.Giocatore;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.MappaException;
import virtualrisikoii.risiko.Obiettivo;
import virtualrisikoii.risiko.Tavolo;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class RecoveryUtil {
    public RecoveryParameter createBackup(){
        Tavolo tavolo=Tavolo.getInstance();
        RecoveryParameter parameter=new RecoveryParameter();
        parameter.setCards_lanch(tavolo.getCardLanch());
        parameter.setDice_lanch(tavolo.getDiceLanch());
        parameter.setIdOccupante(this.getOccupanti(tavolo));
        parameter.setInizializzazione(tavolo.isInizializzazione());
        parameter.setMapName(tavolo.getNameMap());
        parameter.setArmateDisponibili(getNumeroArmateDisponibili(tavolo));
        parameter.setNumeroGiocatori(tavolo.getGiocatori().size());
        parameter.setNumeroTruppe(getNumeroTruppe(tavolo));
        parameter.setObjectives(getObiettivi(tavolo));
        parameter.setSeed_card(tavolo.getCardSeed());
        parameter.setSeed_dice(tavolo.getDiceSeed());
        parameter.setTurno(tavolo.getTurno());
        parameter.setPlayersNames(getPlayersNames(tavolo));
        parameter.setTurnoMyGiocatore(tavolo.getGiocatori().indexOf(tavolo.getMyGiocatore()));
        return parameter;
    }

    public void recoveryTable(RecoveryParameter parameter) throws MappaException, ObiettiviException{
        GameFactory gameFactory=new GameFactory();
        gameFactory.loadGame(parameter.getMapName());

        Mappa mappa=gameFactory.getMappa();
        List<Obiettivo> obiettivi=gameFactory.getObiettivi();

        Tavolo tavolo=Tavolo.createInstance(mappa, obiettivi, parameter.getTurno(), parameter.getNumeroGiocatori(), parameter.getTurnoMyGiocatore(), parameter.getSeed_dice(), 0, parameter.getSeed_card(),parameter.getPlayersNames());
        tavolo.setInizializzazione(parameter.isInizializzazione());
        int diceLanch=tavolo.getDiceLanch();
        int limit=parameter.getDice_lanch();
        for(int i=diceLanch;i<=limit;i++){
            tavolo.lanciaDado();
        }

         limit=parameter.getCards_lanch();
        for(int i=0;i<=limit;i++){
            tavolo.getNuovaCartaID();
        }

        setOccupanti(tavolo,parameter.getIdOccupante());
        setNumeroTruppe(tavolo, parameter.getNumeroTruppe());
        setNumeroArmateDisponibili(tavolo,parameter.getArmateDisponibili());
       
    }

    private int[] getObiettivi(Tavolo tavolo){
        List<Giocatore> giocatori=tavolo.getGiocatori();
        int[] obiettivi=new int[giocatori.size()];

        for(int i=0;i<obiettivi.length;i++){
            obiettivi[i]=giocatori.get(i).getObiettivo().getCodice();
        }

        return obiettivi;
    }

    private int[] getOccupanti(Tavolo tavolo){
        Mappa mappa=tavolo.getMappa();
        Territorio[] territori=mappa.getNazioni();
        int[] occupanti=new int[territori.length];

        for(int i=0;i<territori.length;i++){
            occupanti[i]=territori[i].getOccupante().getColore();
        }

        return occupanti;
    }

    private void setOccupanti(Tavolo tavolo,int[] occupanti){
        Mappa mappa=tavolo.getMappa();
        List<Giocatore> giocatori=tavolo.getGiocatori();
        for(int i=0;i<occupanti.length;i++){
            mappa.getTerritorio(i).setOccupante(giocatori.get(occupanti[i]));
        }
    }

    private int[] getNumeroTruppe(Tavolo tavolo){
        Mappa mappa=tavolo.getMappa();
        Territorio[] territori=mappa.getNazioni();
        int[] numeroTruppe=new int[territori.length];

        for(int i=0;i<territori.length;i++){
            numeroTruppe[i]=territori[i].getNumeroUnita();
        }
        return numeroTruppe;
    }

    private void setNumeroTruppe(Tavolo tavolo,int[] numeroTruppe){
        Mappa mappa=tavolo.getMappa();
        Territorio[] territorio=mappa.getNazioni();
        for(int i=0;i<territorio.length;i++){
            territorio[i].setNumeroUnita(numeroTruppe[i]);
        }
    }

    private void setNumeroArmateDisponibili(Tavolo tavolo,int[] armate){
        List<Giocatore> giocatori=tavolo.getGiocatori();
        for(int i=0;i<armate.length;i++){
            giocatori.get(i).setNumeroTruppe(armate[i]);
        }
    }

    public int[] getNumeroArmateDisponibili(Tavolo tavolo){
        List<Giocatore> giocatori=tavolo.getGiocatori();
        int[] armate=new int[giocatori.size()];

        for(int i=0;i<armate.length;i++){
            armate[i]=giocatori.get(i).getNumeroTruppe();
        }
        return armate;
    }

    public List<String> getPlayersNames(Tavolo tavolo){
        Iterator<Giocatore> giocatori=tavolo.getGiocatori().iterator();
        List<String> names=new ArrayList<String>();
        while(giocatori.hasNext()){
            names.add(giocatori.next().getUsername());
        }
        return names;

    }

}
