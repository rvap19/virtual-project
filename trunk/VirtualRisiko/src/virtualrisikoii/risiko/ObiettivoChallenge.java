/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author root
 */
public class ObiettivoChallenge extends Obiettivo{
    private Set<Territorio> territoriDaConquistare;

    public ObiettivoChallenge(){
        
    }

   

    public Set<Territorio> getTerritoriDaConquistare() {
        return territoriDaConquistare;
    }

    public void setTerritoriDaConquistare(Set<Territorio> territoriDaConquistare) {
        this.territoriDaConquistare = territoriDaConquistare;
    }




    


    @Override
    public boolean controllaObiettivo(Giocatore giocatore, Tavolo tavolo) {
        
        Set<Territorio> territoriConquistati=giocatore.getNazioni();
        return territoriConquistati.containsAll(territoriDaConquistare);
    }

    @Override
    public int getPunteggio(Giocatore giocatore, Tavolo tavolo) {
        int total=0;
        int punteggioAltriTerritori=0;
        Iterator<Territorio> territoriIter=giocatore.getNazioni().iterator();
        while(territoriIter.hasNext()){
            Territorio current=territoriIter.next();
            if(this.territoriDaConquistare.contains(current)){
                total=total+current.getPunteggio();
            }else{
                punteggioAltriTerritori=punteggioAltriTerritori+current.getPunteggio();
            }
        }

        int punteggioCarte=giocatore.getCarte().size();
        int punteggioTruppe=giocatore.getNumeroTruppe();

        return total+punteggioAltriTerritori/3+punteggioCarte/2+punteggioTruppe/10;
    }

    public int getPunteggioMassimo(){
       int total=0;
       Iterator<Territorio> iter=this.territoriDaConquistare.iterator();
       while(iter.hasNext()){
           total=total+iter.next().getPunteggio();
       }
       return total;
    }


    @Override
    public String toString() {
        String newline="<br>";
        String s="<html>"+"Obiettivo "+Integer.toString(super.getCodice())+newline+
                "Territori da conquistare "+newline;
        String t="";
        Iterator<Territorio> iter=this.territoriDaConquistare.iterator();
        while(iter.hasNext()){
            t=t+iter.next().getNome()+" , "+newline;
        }
        return s+t+"</html>";
    }

}
