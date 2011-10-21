package org.domain.risikoweb.entity;


public interface RisikoRemote {

	public User authenticate(String username, String pwd, String ipaddress);
	public String getAddressFor(String username);
	public Gameregistration registerPlayer(Game game, User player);
	public boolean isOnline(String username);
	public Game[] getAvailableGames();
	public boolean deleteRegistration(Gameregistration reg);
	public boolean deleteGame(Game game);
	  
	public void changeManager(Game game, String username);
	public boolean updateGame(Game game);
	public Game createGame(Game game);
	 
	public UserOnLine[] getAuthenticateUsers();
	public User[] getPlayers(Game game);
	public Game getGame(int id);
	public Gameregistration getActiveRegistrationGame(String username); 
	
}
