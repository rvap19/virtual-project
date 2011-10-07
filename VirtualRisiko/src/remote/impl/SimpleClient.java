/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/

package remote.impl;

import java.net.InetAddress;
import remote.Risiko;
import domain.Game;
import domain.Gameregistration;
import domain.User;
import java.util.Scanner;
import org.jboss.remoting.Client;
import org.jboss.remoting.InvokerLocator;

import remote.data.AuthenticateData;
import remote.data.ChangeManagerData;
import remote.data.GameResults;
import remote.data.RegistrationData;

/**
 * Simple test client to make an invocation on remoting server.
 *
 * @author <a href="mailto:telrod@e2technologies.net">Tom Elrod</a>
 */
public  class SimpleClient implements Risiko
{
   // Default locator values
   private static String transport = "socket";
   private static String host = "localhost";
   private static int port = SimpleServer.port;
   private String locatorURI;
   
   public SimpleClient() throws Throwable{
       init();
        locatorURI = transport + "://" + host + ":" + port;
        
        sayHello(locatorURI);
        
   }
   
   public void init(){
       System.out.println("### inserire indirizzo server");
       Scanner scanner=new Scanner(System.in);
       String address=scanner.nextLine();
       if(address!=null && !address.trim().equals("")){
           host=address;
       }
   }
   public void sayHello(String locatorURI) throws Throwable
   {
      // create InvokerLocator with the url type string
      // indicating the target remoting server to call upon.
      InvokerLocator locator = new InvokerLocator(locatorURI);
      System.out.println("Calling remoting server with locator uri of: " + locatorURI);

      Client remotingClient = new Client(locator);
      remotingClient.connect();
      remotingClient.setSubsystem(SimpleServer.HELLO);
      String request = "Do something";
      System.out.println("Invoking server with request of '" + request + "'");
      Object response = remotingClient.invoke(request);
   //   remotingClient.disconnect();
      System.out.println("Invocation response: " + response);
      
      
     
   }
   
    @Override
   public User authenticate (String username, String pwd){
        try {
            AuthenticateData data=new AuthenticateData();
            data.setUsername(username);
            data.setPassword(pwd);
            
            InvokerLocator locator = new InvokerLocator(locatorURI);

           Client remotingClient = new Client(locator);
           remotingClient.connect();
           remotingClient.setSubsystem(SimpleServer.AUTHENTICATE);
           Object response = remotingClient.invoke(data);
           remotingClient.disconnect();
        //   remotingClient.disconnect();
           return (User) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        } 
        return null;
             
   }
   
    @Override
   public Gameregistration registerPlayer (Game game, User player){
        try {
            RegistrationData data=new RegistrationData();
            data.setGame(game);
            data.setUser(player);
            InvokerLocator locator = new InvokerLocator(locatorURI);

           Client remotingClient = new Client(locator);
           remotingClient.connect();
           remotingClient.setSubsystem(SimpleServer.REGISTRATION);
           Object response = remotingClient.invoke(data);
           remotingClient.disconnect();
           return (Gameregistration) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
   }
   
   
    @Override
      public boolean isOnline (String username){
        try {
            String data=username;
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.ISONLINE);
            Object response = remotingClient.invoke(data);
            remotingClient.disconnect();
            return ((Boolean) response).booleanValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
      }
      
      @Override
      public Game[] getAvailableGames (){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.AVAILABLEGAMES);
            Object response = remotingClient.invoke("games");
            remotingClient.disconnect();
            return (Game[]) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;

      }
      
      @Override
	  public boolean deleteRegistration (Gameregistration reg){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.DELETE_REGISTRATION);
            Object response = remotingClient.invoke(reg);
            remotingClient.disconnect();
            return ((Boolean) response).booleanValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
          }
      
      
      
    @Override
	  public boolean deleteGame (Game game){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.DELETE_GAME);
            Object response = remotingClient.invoke(game);
            remotingClient.disconnect();
            return ((Boolean) response).booleanValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
          }
	  
    @Override
	  public void changeManager (Game game, String username){
        try {
            ChangeManagerData data=new ChangeManagerData();
            data.setGame(game);
            data.setUsername(username);
            
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.CHANGE_MANAGER);
            remotingClient.invoke(data);
            remotingClient.disconnect();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
          }
          
          
    @Override
	  public boolean saveResult (Gameregistration[] res){
        try {
            GameResults data=new GameResults();
            data.setRegistrations(res);
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.SAVE_RESULTS);
            Object response = remotingClient.invoke(data);
            remotingClient.disconnect();
            return ((Boolean) response).booleanValue();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return false;
          }
          
          @Override
	  public Game createGame (Game game){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.CREATE_GAME);
            Object response = remotingClient.invoke(game);
            remotingClient.disconnect();
            return (Game) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
          }
	 
    @Override
	  public User[] getAuthenticateUsers (){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.GET_AUTHENTICATES);
            Object response = remotingClient.invoke("users");
            remotingClient.disconnect();
            return (User[]) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
          }
          
          
    @Override
	  public User[] getPlayers (Game game){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.PLAYERS_IN_GAME);
            Object response = remotingClient.invoke(game);
            remotingClient.disconnect();
            return (User[]) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
              
          }
    @Override
	  public Game getGame (int id){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.GAME_FORID);
            Object response = remotingClient.invoke(new Integer(id));
            remotingClient.disconnect();
            return (Game) response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
          }
          
    @Override
	  public Gameregistration getActiveRegistrationGame (String username){
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.REGISTRATION_FOR_PLAYER);
            Object response = remotingClient.invoke(username);
            remotingClient.disconnect();
            return (Gameregistration)response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
          }

    public String getLocatorURI() {
        return locatorURI;
    }

    public void setLocatorURI(String locatorURI) {
        this.locatorURI = locatorURI;
    }
	
          
    
   
   public static void main(String[] args)throws Throwable
   {
      
      String locatorURI = transport + "://" + host + ":" + port;
      SimpleClient client = new SimpleClient();
      client.setLocatorURI(locatorURI);
         client.sayHello(locatorURI);
         User u=client.authenticate("foggiano", "giuseppe");
         System.out.println(u);
         
         Game[] games=client.getAvailableGames();
         if(games!=null){
             for(int i=0;i<games.length;i++){
                 System.out.println(games[i]);
             }
         }
         
         User[] users=client.getAuthenticateUsers();
         if(users!=null){
             for(int i=0;i<users.length;i++){
                 System.out.println(users[i]);
             }
         }
         
   }

    public InetAddress getAddressFor(String username) {
        try {
            InvokerLocator locator = new InvokerLocator(locatorURI);

            Client remotingClient = new Client(locator);
            remotingClient.connect();
            remotingClient.setSubsystem(SimpleServer.ADDRESS);
            Object response = remotingClient.invoke(username);
            remotingClient.disconnect();
            return (InetAddress)response;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
          }
    }


