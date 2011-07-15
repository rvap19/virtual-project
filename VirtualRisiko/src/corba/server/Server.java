package corba.server;


import corba.RisikoServer;
import corba.RisikoServerHelper;

import corba.impl.RisikoServerImpl;
import java.io.IOException;
import java.util.Properties;
import jxta.PlayerManager;
import net.jxta.exception.PeerGroupException;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author root
 */
public class Server extends Thread{

    public static  final int TCP_PORT=9701;

    private PlayerManager playerManager;
    private String[] args;

    public Server(String[] args){
        this.args=args;
    }

    @Override
    public void run() {
        startORB();
    }



    public void startORB2(){
         try{
              //create and initialize the ORB
            Properties props = System.getProperties();
            props.put("org.omg.CORBA.ORBInitialPort", "1050");
            //Replace MyHost with the name of the host on which you are running the server
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");
            ORB orb = ORB.init(args, props);
	    System.out.println("Initialized ORB");

            //Instantiate Servant and create reference
	    POA rootPOA = POAHelper.narrow(
		orb.resolve_initial_references("RootPOA"));
	    RisikoServerImpl msImpl = new RisikoServerImpl();
	    rootPOA.activate_object(msImpl);
	    RisikoServer msRef = RisikoServerHelper.narrow(
		rootPOA.servant_to_reference(msImpl));

            //Bind reference with NameService
	    NamingContext namingContext = NamingContextHelper.narrow(
		orb.resolve_initial_references("NameService"));
            System.out.println("Resolved NameService");
            NameComponent[] nc = { new NameComponent("RisikoServer", "") };
	    namingContext.rebind(nc, msRef);

	    //Activate rootpoa
            rootPOA.the_POAManager().activate();

            //Start readthread and wait for incoming requests
            System.out.println("Risiko Server ready and running ....");

            orb.run();

        }catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        
    }
     public void startORB(){
         try{
              //create and initialize the ORB
            Properties props = System.getProperties();
            props.put("org.omg.CORBA.ORBInitialPort", "1050");
            //Replace MyHost with the name of the host on which you are running the server
            props.put("org.omg.CORBA.ORBInitialHost", "localhost");

            // Step 1: Instantiate the ORB
            ORB orb = ORB.init(args, props);
   System.out.println("Initialized ORB");
            // Step 2: Instantiate the servant
            RisikoServerImpl servant = new RisikoServerImpl();
   System.out.println("Initialized servant");
            // Step 3 : Create a POA with Persistent Policy
            // *******************
            // Step 3-1: Get the rootPOA
            POA rootPOA = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));

            rootPOA.the_POAManager().activate( );
            rootPOA.activate_object(servant);
            // ***********************



            // Step 5: Resolve RootNaming context and bind a name for the
            // servant.
            // NOTE: If the Server is persistent in nature then using Persistent
            // Name Service is a good choice. Even if ORBD is restarted the Name
            // Bindings will be intact. To use Persistent Name Service use
            // 'NameService' as the key for resolve_initial_references() when
            // ORBD is running.
            org.omg.CORBA.Object obj = orb.resolve_initial_references(
                "NameService" );
            System.out.println("Resolved NameService");
            NamingContextExt rootContext = NamingContextExtHelper.narrow( obj );

            NameComponent[] nc = rootContext.to_name(
                "RisikoServer" );
            rootContext.rebind( nc, rootPOA.servant_to_reference(
                servant ) );
   System.out.println("server ready");
            // Step 6: We are ready to receive client requests
            orb.run();
        } catch ( Exception e ) {
            System.err.println( "Exception in Persistent Server Startup " + e );
        }

       


    }


    public void startJXTA() throws IOException, PeerGroupException{
        this.playerManager=new PlayerManager("foggiano", Server.TCP_PORT);
        this.playerManager.init(null,true);
      //  this.rendex=new Rendezvous();
      //  rendex.start();
    }


    public static void install(org.omg.CORBA.ORB orb){

    }

    public static void main(String args[])  {

        Server server=new Server(args);
        server.start();
    //    server.startJXTA();
        
   

    }



}
