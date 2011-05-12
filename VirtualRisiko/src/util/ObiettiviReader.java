package util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import virtualrisikoii.risiko.Mappa;
import virtualrisikoii.risiko.Obiettivo;
import virtualrisikoii.risiko.ObiettivoChallenge;
import virtualrisikoii.risiko.Territorio;

/**
 *
 * @author root
 */
public class ObiettiviReader {
     private DocumentBuilder builder;
     public List readObiettivi(Territorio[] territori,String fileName) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        builder=factory.newDocumentBuilder();
        Document document = builder.parse(fileName);

        ArrayList<ObiettivoChallenge> obiettivi=new ArrayList<ObiettivoChallenge>();
        NodeList obiettiviList=document.getDocumentElement().getChildNodes();
        for(int i=0;i<obiettiviList.getLength();i++){
            Node currentObiettivo=obiettiviList.item(i);
            if(currentObiettivo.getNodeName().equals("obiettivo")){
                int id=Integer.parseInt(currentObiettivo.getAttributes().getNamedItem("id").getTextContent())-1;
                NodeList list=currentObiettivo.getChildNodes();
                HashSet<Territorio> territoriSet=new HashSet<Territorio>();
                for(int j=0;j<list.getLength();j++){
                    Node currentTerritorio=list.item(j);
                    if(currentTerritorio.getNodeName().equals("territorio")){
                        int territorioId=Integer.parseInt(currentTerritorio.getTextContent())-1;
                        territoriSet.add(territori[territorioId]);
                    }
                }
                ObiettivoChallenge obiettivo=new ObiettivoChallenge();
                obiettivo.setCodice(id);
                obiettivo.setTerritoriDaConquistare(territoriSet);
                obiettivi.add(obiettivo);
            }
        }
        
        return obiettivi;
    }

     

}
