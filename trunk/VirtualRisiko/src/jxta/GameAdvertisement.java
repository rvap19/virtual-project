
package jxta;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import net.jxta.document.Advertisement;
import net.jxta.document.AdvertisementFactory;
import net.jxta.document.Document;
import net.jxta.document.MimeMediaType;
import net.jxta.document.StructuredDocument;
import net.jxta.document.StructuredDocumentFactory;
import net.jxta.document.Element;
import net.jxta.document.TextElement;
import net.jxta.id.ID;
import net.jxta.id.IDFactory;

public class GameAdvertisement extends Advertisement {
    
    public static final String Name = "Game advertisement";

    // Advertisement elements, tags and indexables
    public final static String AdvertisementType = "jxta:GameAdvertisement";
    
    private ID AdvertisementID = ID.nullID;

    
    private final static String IDTag = "MyIDTag";
    

    private static final String tagName = "NameTag";
    
    private static final String tagCreatorID = "CreatorIDTag";

    private static final String tagMaxPlayers = "MaxPlayersTag";

    private static final String tagCurrentPlayers="CurrentPlayersTag";
    
    private final static String[] IndexableFields = { tagCreatorID, tagName };

    

 
     

    public GameAdvertisement() {
        
        // Accepting default values

    }

    public GameAdvertisement(Element Root) {
        
        // Retrieving the elements
        TextElement MyTextElement = (TextElement) Root;

        Enumeration TheElements = MyTextElement.getChildren();
        
        while (TheElements.hasMoreElements()) {
            
            TextElement TheElement = (TextElement) TheElements.nextElement();
            
            ProcessElement(TheElement);
            
        }        

    }
    
    public void ProcessElement(TextElement TheElement) {
        
        String TheElementName = TheElement.getName();
        String TheTextValue = TheElement.getTextValue();
        
        if (TheElementName.compareTo(IDTag)==0) {
            
            try {
                
                URI ReadID = new URI(TheTextValue);
                AdvertisementID = IDFactory.fromURI(ReadID);
                return;
                
            } catch (URISyntaxException Ex) {
                
                // Issue with ID format
                Ex.printStackTrace();
                
            } catch (ClassCastException Ex) {
                
                // Issue with ID type
                Ex.printStackTrace();
                
            }
            
        }
        
        if (TheElementName.compareTo(tagName)==0) {
            
            name = TheTextValue;
            return;
            
        }
        
        if (TheElementName.compareTo(tagMaxPlayers)==0) {
            
            presenceStatus = Integer.parseInt(TheTextValue);
            return;
            
        }

        if (TheElementName.compareTo(tagCreatorID)==0) {

            peerID = TheTextValue;
            return;

        }
        
    }
    
    @Override
    public Document getDocument(MimeMediaType TheMimeMediaType) {
        
        // Creating document
        StructuredDocument TheResult = StructuredDocumentFactory.newStructuredDocument(
                TheMimeMediaType, AdvertisementType);
        
        // Adding elements
        Element MyTempElement;
        
        MyTempElement = TheResult.createElement(tagName, name);
        TheResult.appendChild(MyTempElement);
        
        MyTempElement = TheResult.createElement(tagMaxPlayers, Integer.toString(presenceStatus));
        TheResult.appendChild(MyTempElement);

        MyTempElement = TheResult.createElement(tagCreatorID, peerID);
        TheResult.appendChild(MyTempElement);
        
        return TheResult;
        
    }

    public void SetID(ID TheID) {
        AdvertisementID = TheID;
    }

    @Override
    public ID getID() {
        return AdvertisementID;
    }

    @Override
    public String[] getIndexFields() {
        return IndexableFields;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }

    public int getPresenceStatus() {
        return presenceStatus;
    }

    public void setPresenceStatus(int presenceStatus) {
        this.presenceStatus = presenceStatus;
    }

    
    
    @Override
    public GameAdvertisement clone() throws CloneNotSupportedException {
        
        GameAdvertisement Result =
                (GameAdvertisement) super.clone();

        Result.AdvertisementID = this.AdvertisementID;
        Result.name = this.name;
        Result.presenceStatus = this.presenceStatus;
        Result.peerID=this.peerID;
        
        return Result;
        
    }
    
    @Override
    public String getAdvType() {
        
        return AdvertisementType;
        
    }
    
    public static String getAdvertisementType() {
        return AdvertisementType;
    }    
    
    public static class Instantiator implements AdvertisementFactory.Instantiator {

        public String getAdvertisementType() {
            return GameAdvertisement.getAdvertisementType();
        }

        public Advertisement newInstance() {
            return new GameAdvertisement();
        }

        public Advertisement newInstance(net.jxta.document.Element root) {
            return new GameAdvertisement(root);
        }
        
    }

}
