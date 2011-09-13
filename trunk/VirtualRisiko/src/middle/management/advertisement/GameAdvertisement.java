
package middle.management.advertisement;

import java.util.List;


public interface  GameAdvertisement  extends  Advertisement{
    
    
    
    public  String getCreatorID() ;

    public  void setCreatorID(String creatorID);

    public  String getGameName() ;

    public  void setGameName(String gameName);

    public  String getMapName() ;

    public  void setMapName(String mapName);

    
    public  int getMaxPlayer() ;

    public  void setMaxPlayer(int maxPlayer);

    public  List<String> getPlayerIds();

    public  String getGameID() ;

    public  void setGameID(String gameID) ;

    

    public  void setPlayerIds(List<String> playerIds) ;



    
    
    
    
    
    public  String getAdvType() ;
    

}
