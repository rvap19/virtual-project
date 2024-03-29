
package jxta.advertisement;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
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

public class GameAdvertisement extends Advertisement implements middle.management.advertisement.GameAdvertisement{
    
    public static final String Name = "Game advertisement";

    // Advertisement elements, tags and indexables
    public final static String AdvertisementType = "jxta:GameAdvertisement";
    
    private ID AdvertisementID = ID.nullID;

    
    private final static String IDTag = "MyIDTag";
    

    private static final String tagName = "NameTag";
    private String gameName=null;
    
    private static final String tagCreatorID = "CreatorIDTag";
    private String creatorID;

    private static final String tagMaxPlayers = "MaxPlayersTag";
    private int maxPlayer=0;

    private static final String tagIdGame="IDGameID";
    private String gameID;

    private static final String tagMapName="MapNameTag";
    private String mapName;

    private static final String tagPlayer="PlayerTag";
    private List<String> playerIds= new ArrayList<String>();
    
    
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
            
            gameName = TheTextValue;
            return;
            
        }

        if (TheElementName.compareTo(tagMapName)==0) {

            mapName = TheTextValue;
            return;

        }
        
        if (TheElementName.compareTo(tagMaxPlayers)==0) {
            
            maxPlayer = Integer.parseInt(TheTextValue);
            return;
            
        }

        if (TheElementName.compareTo(tagCreatorID)==0) {

            creatorID = TheTextValue;
            return;

        }

        if (TheElementName.compareTo(tagIdGame)==0) {

            gameID = TheTextValue;
            return;

        }

        if(TheElementName.compareTo(tagPlayer)==0){
            this.playerIds.add(TheTextValue);
        }
        
    }
    
    @Override
    public Document getDocument(MimeMediaType TheMimeMediaType) {
        
        // Creating document
        StructuredDocument TheResult = StructuredDocumentFactory.newStructuredDocument(
                TheMimeMediaType, AdvertisementType);
        
        // Adding elements
        Element MyTempElement;
        
        MyTempElement = TheResult.createElement(tagName, gameName);
        TheResult.appendChild(MyTempElement);
        
        MyTempElement = TheResult.createElement(tagMaxPlayers, Integer.toString(maxPlayer));
        TheResult.appendChild(MyTempElement);

        MyTempElement = TheResult.createElement(tagCreatorID, creatorID);
        TheResult.appendChild(MyTempElement);

        MyTempElement = TheResult.createElement(tagIdGame, gameID);
        TheResult.appendChild(MyTempElement);

        MyTempElement = TheResult.createElement(tagMapName, mapName);
        TheResult.appendChild(MyTempElement);

        Iterator<String> iter=this.playerIds.iterator();
        while(iter.hasNext()){
            MyTempElement = TheResult.createElement(tagPlayer, iter.next());
            TheResult.appendChild(MyTempElement);
        }
        
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

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    
    public int getMaxPlayer() {
        return maxPlayer;
    }

    public void setMaxPlayer(int maxPlayer) {
        this.maxPlayer = maxPlayer;
    }

    public List<String> getPlayerIds() {
        return playerIds;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    

    public void setPlayerIds(List<String> playerIds) {
        this.playerIds = playerIds;
    }



    
    
    @Override
    public GameAdvertisement clone() throws CloneNotSupportedException {
        
        GameAdvertisement Result =
                (GameAdvertisement) super.clone();

        Result.AdvertisementID = this.AdvertisementID;
        Result.gameName = this.gameName;
        Result.maxPlayer = this.maxPlayer;
        Result.creatorID=this.creatorID;
        Result.playerIds=new ArrayList<String>();
        Result.playerIds.addAll(playerIds);
        
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
