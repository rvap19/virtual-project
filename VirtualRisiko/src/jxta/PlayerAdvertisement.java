
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

public class PlayerAdvertisement extends Advertisement {
    
    public static final String Name = "Player advertisement";

    // Advertisement elements, tags and indexables
    public final static String AdvertisementType = "jxta:PresenceAdvertisement";
    
    private ID AdvertisementID = ID.nullID;

    
    private final static String IDTag = "MyIDTag";
    

    private static final String tagName = "NameTag";

    /**
     * The element name for the presence advertisement's Peer ID.
     */
    private static final String tagPeerID = "PeerIDTag";

    /**
     * The element name for the presence advertisement's status info.
     */
    private static final String tagPresenceStatus = "PresenceStatusTag";

     private final static String[] IndexableFields = { tagPeerID, tagName };

    public static final int IN_GAME=0;

    public static final int OUT_GAME=1;

    /**
     * A simple name for the user specified by the advertisement's
     * email address.
     */
    private String name = null;

    /**
     * The Peer ID locating the peer on the network.
     */
    private String peerID = null;

    /**
     * A simple descriptor identifying the user's presence status.
     * The user can indicate that he or she is online, offline, busy, or
     * away.
     */
    private int presenceStatus = IN_GAME;
     

    public PlayerAdvertisement() {
        
        // Accepting default values

    }

    public PlayerAdvertisement(Element Root) {
        
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
        
        if (TheElementName.compareTo(tagPresenceStatus)==0) {
            
            presenceStatus = Integer.parseInt(TheTextValue);
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
        
        MyTempElement = TheResult.createElement(tagName, name);
        TheResult.appendChild(MyTempElement);
        
        MyTempElement = TheResult.createElement(tagPresenceStatus, Integer.toString(presenceStatus));
        TheResult.appendChild(MyTempElement);

        MyTempElement = TheResult.createElement(tagPeerID, peerID);
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
    public PlayerAdvertisement clone() throws CloneNotSupportedException {
        
        PlayerAdvertisement Result =
                (PlayerAdvertisement) super.clone();

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
            return PlayerAdvertisement.getAdvertisementType();
        }

        public Advertisement newInstance() {
            return new PlayerAdvertisement();
        }

        public Advertisement newInstance(net.jxta.document.Element root) {
            return new PlayerAdvertisement(root);
        }
        
    }

}
