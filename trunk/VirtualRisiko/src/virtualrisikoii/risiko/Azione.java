/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

/**
 *
 * @author root
 */
public abstract class Azione {

     protected static final int ATTACCO=0;
    protected static final int SPOSTAMENTO=1;
    protected int type;

    protected int numeroTruppe=-1;
    protected Giocatore giocatore=null;
    protected Territorio daTerritorio=null;
    protected Territorio aTerritorio=null;
    

    public int getNumeroTruppe() {
        return numeroTruppe;
    }

    public void setNumeroTruppe(int n) {
        if(daTerritorio.getNumeroUnita()==1){
            numeroTruppe=0;
            return;
        }
        if(n>=daTerritorio.getNumeroUnita()){
            this.numeroTruppe=daTerritorio.getNumeroUnita()-1;
        }else{
            this.numeroTruppe=n;
        }
    }

     public void setTerritori(Territorio da , Territorio a){



        aTerritorio=a;
        daTerritorio=da;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public void setGiocatore(Giocatore giocatore) {
        this.giocatore = giocatore;
    }

    public Territorio getaTerritorio() {
        return aTerritorio;
    }

    public Territorio getDaTerritorio() {
        return daTerritorio;
    }

    public boolean isAttacco(){
        return this.type==Azione.ATTACCO;
    }

    public abstract void eseguiAzione();

    public abstract String toString();


    

}
