/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.Iterator;

/**
 *
 * @author root
 */
public class Rinforzo {

    public  static int getRinfornzo(Giocatore g,Carta c1,Carta c2,Carta c3,Mappa m){
        int rinforzo=0;
        rinforzo=rinforzo+getRinforzoSuCombinazione(c1, c2, c3);
        if(rinforzo==0){
            return 0;
        }
        rinforzo=rinforzo+getRinforzoSuPossesso(c1, c2, c3, g);
        rinforzo=rinforzo+getRinforzoSuPossessoContinenti(g, m);
        return rinforzo;

    }

    public static int getRinforzo(Giocatore g){
        
        return g.getNazioni().size()/3+getRinforzoSuPossessoContinenti(g, Tavolo.getInstance().getMappa());
    }

    private static int getRinforzoSuPossesso(Carta c1,Carta c2,Carta c3,Giocatore g){
        int territori=0;
        if(g.getNazioni().contains(c1.getTerritorio())){
            territori++;
        }
        if(g.getNazioni().contains(c2.getTerritorio())){
            territori++;
        }
        if(g.getNazioni().contains(c3.getTerritorio())){
            territori++;
        }

        return 2*territori;
    }


    private static int getRinforzoSuPossessoContinenti(Giocatore g,Mappa m){
        Continente[] continenti=m.getContinenti();
        Continente c;
        int rinforzo=0;
        for(int i=0;i<continenti.length;i++){
            c=continenti[i];
            if(g.getNazioni().containsAll(c.getTerritori())){
                rinforzo=rinforzo+c.getRinforzo();
            }
        }
        return rinforzo;

    }



    private  static int getRinforzoSuCombinazione(Carta c1,Carta c2,Carta c3){
       int somma=c1.getCodice()+c2.getCodice()+c3.getCodice();

       if(somma==(3*Carta.CANNONE)){
           return 4;
       }

       if(somma==(3*Carta.CAVALIERE)){
           return 8;
       }

       if(somma==(3*Carta.FANTE)){
           return 6;
       }

       if(somma==(Carta.CANNONE+Carta.CAVALIERE+Carta.FANTE)){
           return 10;
       }

       if(somma==(Carta.JOLLY+2*Carta.CANNONE)){
           return 12;
       }

       if(somma==(Carta.JOLLY+2*Carta.CAVALIERE)){
           return 12;
       }

       if(somma==(Carta.JOLLY+2*Carta.FANTE)){
           return 12;
       }

       return 0;
    }

}
