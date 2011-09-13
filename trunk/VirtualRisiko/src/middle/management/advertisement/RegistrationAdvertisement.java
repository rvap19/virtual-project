
package middle.management.advertisement;


public interface RegistrationAdvertisement  extends  Advertisement{
    
    public  String getGameID();

    public  void setGameID(String gameID) ;

    public  String getPeerID();

    public  void setPeerID(String peerID) ;

    public  long getTime() ;

    public  void setTime(long time) ;
}
