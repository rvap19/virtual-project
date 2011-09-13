
package jxta.advertisement;

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
import net.jxta.protocol.PipeAdvertisement;

public class RegistrationAdvertisement  extends Advertisement implements Comparable<RegistrationAdvertisement>,middle.management.advertisement.RegistrationAdvertisement {
    
    public static final String Name = "Registration advertisement";

    // Advertisement elements, tags and indexables
    public final static String AdvertisementType = "jxta:RegistrationAdvertisement";
    
    private ID AdvertisementID = ID.nullID;

    
    private final static String IDTag = "MyIDTag";
    

    public static final String tagGameID = "GameIDTag";

    /**
     * The element name for the presence advertisement's Peer ID.
     */
    private static final String tagPeerID = "PeerIDTag";

    private static final String tagTime="TimeTag";

     private final static String[] IndexableFields = { tagPeerID, tagGameID };


    

    /**
     * The Peer ID locating the peer on the network.
     */
    private String peerID = null;

    private String gameID = null;

    private long time=0;
     

    public RegistrationAdvertisement() {
        
        // Accepting default values

    }

    public RegistrationAdvertisement(Element Root) {
        
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
        
        if (TheElementName.compareTo(tagGameID)==0) {
            
            gameID = TheTextValue;
            return;
            
        }
        
        if (TheElementName.compareTo(tagTime)==0) {
            
            time = Long.parseLong(TheTextValue);
            return;
            
        }

        if (TheElementName.compareTo(tagPeerID)==0) {

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
        
        MyTempElement = TheResult.createElement(tagGameID, gameID);
        TheResult.appendChild(MyTempElement);
        
        MyTempElement = TheResult.createElement(tagTime, Long.toString(time));
        TheResult.appendChild(MyTempElement);

        MyTempElement = TheResult.createElement(tagPeerID, peerID);
        TheResult.appendChild(MyTempElement);

        PipeAdvertisement a;
        
        
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

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public String getPeerID() {
        return peerID;
    }

    public void setPeerID(String peerID) {
        this.peerID = peerID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    

    
    
    @Override
    public RegistrationAdvertisement clone() throws CloneNotSupportedException {
        
        RegistrationAdvertisement Result =
                (RegistrationAdvertisement) super.clone();

        Result.AdvertisementID = this.AdvertisementID;
        Result.gameID  = this.gameID;
        Result.time = this.time;
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

    public int compareTo(RegistrationAdvertisement o) {
        return (int) (this.getPeerID().compareTo(o.getPeerID()));
    }
    
    public static class Instantiator implements AdvertisementFactory.Instantiator {

        public String getAdvertisementType() {
            return RegistrationAdvertisement.getAdvertisementType();
        }

        public Advertisement newInstance() {
            return new RegistrationAdvertisement();
        }

        public Advertisement newInstance(net.jxta.document.Element root) {
            return new RegistrationAdvertisement(root);
        }
        
    }

}
