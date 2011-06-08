
import corba.PartitaInfo;
import corba.RisikoServer;
import corba.RisikoServerHelper;
import corba.UserInfo;
import corba.impl.User;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class Client {

   static RisikoServer helloImpl;

    public static void main(String args[])
    {
	try {
            ORB orb = ORB.init(args, null);
            org.omg.CORBA.Object objRef =
                     orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            String name = "Hello";
            helloImpl = RisikoServerHelper.narrow(ncRef.resolve_str(name));
            UserInfo info=helloImpl.authenticate("pipolo", "xxxxxx");
            if(info.logged)
                System.out.println("Utente "+info.username+" autenticato");
            else
                System.out.println("Username o psw errata");

            PartitaInfo[] games=helloImpl.getAvailableGames();
            System.out.println("stampa delle partite avviate");
            for(int i=0;i<games.length;i++){
                System.out.println(games[i].id+" con "+games[i].playersNumber);
            }

            helloImpl.createGame(info,(short) 10,(short) 6, "prova");
            games=helloImpl.getAvailableGames();
            System.out.println("stampa delle partite avviate");
            for(int i=0;i<games.length;i++){
                System.out.println(games[i].id+" con "+games[i].playersNumber);
            }
            

	} catch(Exception e){
	    System.out.println("ERROR : " + e);
	    e.printStackTrace(System.out);
	}
    }


}
