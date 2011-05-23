/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package virtualrisikoii.risiko;

/**
 *
 * @author root
 */
public class Spostamento extends Azione{

   public Spostamento(){
       super.type=Azione.SPOSTAMENTO;
   }


  

    @Override
    public void eseguiAzione() {
        super.aTerritorio.setNumeroUnita(numeroTruppeTotali+aTerritorio.getNumeroUnita());
        super.daTerritorio.setNumeroUnita(daTerritorio.getNumeroUnita()-numeroTruppeTotali);
    }

    @Override
    public String toString() {
        return "Spostamento da "+super.daTerritorio.getNome()+" a "+super.aTerritorio.getNome()+" con "+super.numeroTruppeTotali+" truppe" ;
    }

}
