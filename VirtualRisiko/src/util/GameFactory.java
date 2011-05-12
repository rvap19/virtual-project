/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.util.List;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.MappaException;
import virtualrisikoii.risiko.Obiettivo;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class GameFactory {
    private List<Obiettivo> obiettivi;
    private Mappa mappa;
    private String name;
    public GameFactory(){

    }

    public void loadGame(String name) throws MappaException, ObiettiviException{
        this.name=name;
        this.loadMappa();
        this.loadObiettivi();
    }

    public Mappa getMappa(){
        return mappa;
    }

    public List<Obiettivo> getObiettivi(){
        return obiettivi;
    }

    private void loadMappa() throws MappaException{

        String file="src/util/"+name+".xml";
        mappa=creaMappa(file);
        if(mappa==null){
            throw new MappaException("Impossibile accedere alla mappa "+name);
        }
    }

    private  Mappa creaMappa(String file){
        try{
            MapReader reader=new MapReader();
            reader.readMap(file);
            Mappa m=new Mappa();
            m.setContinenti(reader.getContinente());
            m.setTerritori(reader.getTerritori());
            return m;
        }catch(Exception ex){
            System.err.println("impossibile leggere mappa in "+file);
            return null;
        }
    }

    

    private void loadObiettivi() throws ObiettiviException{
        String file="src/util/"+name+"Obiettivi.xml";
        this.obiettivi=this.creaObiettivi(file, mappa.getNazioni());
        if(obiettivi==null){
            throw new ObiettiviException("Impossibile accedere agli obiettivi "+name);
        }
    }

    private List<Obiettivo> creaObiettivi(String file,Territorio[] territori){
        try{
            ObiettiviReader reader=new ObiettiviReader();
            return reader.readObiettivi(territori, file);

        }catch(Exception  ex){
            return null;
        }
    }

}
