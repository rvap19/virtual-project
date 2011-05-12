package util;
import java.io.IOException;
import java.util.HashSet;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import virtualrisikoii.risiko.Continente;
import virtualrisikoii.risiko.Territorio;

public class MapReader {
    private DocumentBuilder builder;
    private Territorio[] territori;
    private Continente[] continenti;
    
    public void readMap(String fileName) throws SAXException, IOException, ParserConfigurationException {
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        builder=factory.newDocumentBuilder();
        
        Document document = builder.parse(fileName);
        Element element=document.getDocumentElement();
        int territoriSize=Integer.parseInt(element.getAttribute("territori"));
        int continentiSize=Integer.parseInt(element.getAttribute("continenti"));
        territori=new Territorio[territoriSize];
        continenti=new Continente[continentiSize];
        System.out.println("territori "+territoriSize);
        System.out.println("continenti "+continentiSize);
        String s= element.getNodeName();
        NodeList list=element.getChildNodes();
        
        for(int i=0;i<list.getLength();i++){
           Node currentNode=list.item(i);
           if(currentNode.getNodeName().equals("Territorio")){
                NamedNodeMap attributes=currentNode.getAttributes();
                int id=Integer.parseInt(attributes.getNamedItem("id").getTextContent())-1;
                String name=attributes.getNamedItem("nome").getTextContent();
                int punti=Integer.parseInt(attributes.getNamedItem("punti").getTextContent());
                Territorio nuovoTerritorio=new Territorio(id, name);
                nuovoTerritorio.setPunteggio(punti);
                territori[id]=nuovoTerritorio;
                
           }
        }

        list=document.getDocumentElement().getChildNodes();
        for(int i=0;i<list.getLength();i++){
            Node currentNode=list.item(i);
            
            if(currentNode.getNodeName().equals("Territorio")){
                int currentNodeID=Integer.parseInt(currentNode.getAttributes().getNamedItem("id").getTextContent())-1;
                NodeList child=currentNode.getChildNodes();
                for(int j=0;j<child.getLength();j++){
                    Node currentChild=child.item(j);
                    if(currentChild.getNodeName().equals("confina") ){
                        int currentChildID=Integer.parseInt(currentChild.getTextContent())-1;
                        territori[currentNodeID].addNazione(territori[currentChildID]);
                        territori[currentChildID].addNazione(territori[currentNodeID]);
                    }
                }
            }
        }

        for(int i=0;i<list.getLength();i++){
            Node currentNode=list.item(i);
            if(currentNode.getNodeName().equals("continente")){
                NamedNodeMap attributes=currentNode.getAttributes();
                int continenteID=Integer.parseInt(attributes.getNamedItem("id").getTextContent())-1;
                String nome=attributes.getNamedItem("nome").getTextContent();
                int rinforzi=Integer.parseInt(attributes.getNamedItem("rinforzi").getTextContent());
                Continente continente=new Continente(continenteID);
                continente.setNome(nome);
                continente.setRinforzo(rinforzi);
                NodeList terrList=currentNode.getChildNodes();
                HashSet<Territorio> terrSet=new HashSet<Territorio>();
                for(int j=0;j<terrList.getLength();j++){
                   Node currentT=terrList.item(j);
                   if(currentT.getNodeName().equals("territorio")){
                        terrSet.add(territori[Integer.parseInt(currentT.getTextContent())-1]);
                        continente.setTerritori(terrSet);
                    }
                }
                continenti[continenteID]=continente;
            }
        }

    }

    public Continente[] getContinente(){
        return this.continenti;
    }

    public Territorio[] getTerritori(){
        return this.territori;
    }
}
