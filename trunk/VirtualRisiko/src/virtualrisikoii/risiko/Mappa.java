/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import virtualrisikoii.util.MapReader;

/**
 *
 * @author root
 */
public class Mappa {

    private Territorio[] nazioni;
    private Continente[] continenti;

    public Mappa(){
        
    }

    public Mappa(Territorio[] territori,Continente[] continenti){
        this.nazioni=territori;
        this.continenti=continenti;

    }

    public Continente[] getContinenti() {
        return continenti;
    }

    public Continente getContinente(int code){
        return this.continenti[code];
    }
    public void setContinenti(Continente[] continenti) {
        this.continenti = continenti;
    }

    public Territorio getTerritorio(int codice){
        return this.nazioni[codice];
    }
   
    

    public Territorio[] getNazioni(){
        return this.nazioni;
    }

    public void setTerritori(Territorio[] territori){
        this.nazioni=territori;
    }


    public String toString(){
        String result="Territori :: "+this.nazioni.length+"\n"+
                      "Continenti :: "+this.continenti.length+"\n";
        String s;
        String nazione;
        String confinante;
        for(int i=0;i<nazioni.length;i++){
            nazione=nazioni[i].getNome();
            s=nazione+" confina con :: ";
            Iterator<Territorio> iter=nazioni[i].getConfinanti().iterator();
            while(iter.hasNext()){
                Territorio currentTerritorio=iter.next();
                confinante=currentTerritorio.getNome();
                s=s+confinante+" ; ";
            }
            result=result+s+"\n";
        }
        return result;
    }


    
        
    


}
