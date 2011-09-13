
package middle.management.advertisement;


public interface PlayerAdvertisement  extends Advertisement{
    
    
    public  String getName() ;

    public  void setName(String name) ;

    public  String getPeerID();

    public  void setPeerID(String peerID);

    public  int getPresenceStatus() ;

    public  void setPresenceStatus(int presenceStatus);

    public  String getAdvType() ;
    
}
