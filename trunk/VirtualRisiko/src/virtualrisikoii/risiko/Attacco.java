/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

import java.util.Arrays;

/**
 *
 * @author root
 */
public class Attacco extends Azione{

    private int[] punteggio=null;
    private int[] punteggioAvversario=null;
    private int numeroTruppeAvversario;
    private int numeroTruppeAttacco;

    private boolean vittoria=false;

    public Attacco(){
        super.type=Azione.ATTACCO;
    }
    @Override
    public void setNumeroTruppe(int n){
        this.numeroTruppeTotali=n;
        if(numeroTruppeTotali>3){
            numeroTruppeAttacco=3;

        }else{
            numeroTruppeAttacco=numeroTruppeTotali;
        }
     

        if(super.aTerritorio.getNumeroUnita()<numeroTruppeAttacco){
            numeroTruppeAvversario=aTerritorio.getNumeroUnita();
        }else if(super.aTerritorio.getNumeroUnita()>=numeroTruppeAttacco){
            if(super.aTerritorio.getNumeroUnita()>3)
                numeroTruppeAvversario=3;
            else
                numeroTruppeAvversario=aTerritorio.getNumeroUnita();
        }

    }

    public void eseguiAzione() {

        int dadi;
        if(this.numeroTruppeAttacco<this.numeroTruppeAvversario){
            dadi=numeroTruppeAttacco;
        }else{
            dadi=numeroTruppeAvversario;
        }

        numeroTruppeTotali=numeroTruppeTotali-numeroTruppeAttacco;
        for(int i=0;i<dadi;i++){
            if(punteggio[i]>punteggioAvversario[i]){
                this.numeroTruppeAvversario--;
            }else{
                this.numeroTruppeAttacco--;
                
            }
        }
       

        this.aTerritorio.setNumeroUnita(this.aTerritorio.getNumeroUnita()+this.numeroTruppeAvversario);
        
        if(aTerritorio.getNumeroUnita()<=0){
            vittoria=true;
            
            Giocatore attaccante=daTerritorio.getOccupante();
            Giocatore difensore=aTerritorio.getOccupante();
            
            difensore.getNazioni().remove(aTerritorio);
           // aTerritorio.getOccupante().getNazioni().remove(aTerritorio);
            aTerritorio.setOccupante(attaccante);
           // aTerritorio.setOccupante(daTerritorio.getOccupante());

            aTerritorio.setNumeroUnita(numeroTruppeTotali+numeroTruppeAttacco);
            
            attaccante.getNazioni().add(aTerritorio);
            
        }else{
            daTerritorio.setNumeroUnita(daTerritorio.getNumeroUnita()+numeroTruppeTotali+numeroTruppeAttacco);
        }
        
    }

    public void setPunteggio(int[] p){
        punteggio=p;
        Arrays.sort(punteggio);
        int temp=punteggio[punteggio.length-1];
        punteggio[punteggio.length-1]=punteggio[0];
        punteggio[0]=temp;
    }

    public void setPunteggioAvversario(int[] p){
        punteggioAvversario=p;
        Arrays.sort(punteggioAvversario);
        int temp=punteggioAvversario[punteggioAvversario.length-1];
        punteggioAvversario[punteggioAvversario.length-1]=punteggioAvversario[0];
        punteggioAvversario[0]=temp;
    }
    
   

    

    public int[] getPunteggio() {
        return punteggio;
    }

    public int[] getPunteggioAvversario() {
        return punteggioAvversario;
    }


    public boolean isVittoria(){
        return vittoria;
    }

    public int getNumeroTruppeAvversario() {
        return numeroTruppeAvversario;
    }

    public void setNumeroTruppeAvversario(int numeroTruppeAvversario) {
        this.numeroTruppeAvversario = numeroTruppeAvversario;
    }

    @Override
    public String toString() {
        return "Attacco da "+super.daTerritorio.getNome()+" a "+super.aTerritorio.getNome()+" con "+this.punteggio.length+" truppe ";
    }

    public int getNumeroTruppeInAttacco() {
        return this.numeroTruppeAttacco;
    }

    


}
