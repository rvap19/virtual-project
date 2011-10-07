package remote;

import domain.Game;
import domain.Gameregistration;
import domain.User;
import java.net.InetAddress;


public interface Risiko {
    
	  public User authenticate (String username, String pwd);
          public InetAddress getAddressFor(String username);
	  public Gameregistration registerPlayer (Game game, User player);
	  public boolean isOnline (String username);
	  public Game[] getAvailableGames ();
	  public boolean deleteRegistration (Gameregistration reg);
	  public boolean deleteGame (Game game);
	  
	  public void changeManager (Game game, String username);
	  public boolean saveResult (Gameregistration[] res);
	  public Game createGame (Game game);
	 
	  public User[] getAuthenticateUsers ();
	  public User[] getPlayers (Game game);
	  public Game getGame (int id);
	  public Gameregistration getActiveRegistrationGame (String username); 
	
}
