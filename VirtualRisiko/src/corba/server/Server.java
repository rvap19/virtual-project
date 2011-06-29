package corba.server;


import corba.RisikoServer;
import corba.RisikoServerHelper;

import corba.impl.RisikoServerImpl;
import java.io.IOException;
import jxta.PlayerManager;
import net.jxta.exception.PeerGroupException;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
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
    private Rendezvous rendex;

    public Server(String[] args){
        this.args=args;
    }

    @Override
    public void run() {
        startORB();
    }


    public void startORB(){
         try{
              // create and initialize the ORB
              ORB orb = ORB.init(args, null);

              // get reference to rootpoa & activate the POAManager
              POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
              rootpoa.the_POAManager().activate();

              // create servant and register it with the ORB
              RisikoServerImpl helloImpl = new RisikoServerImpl();
              helloImpl.setORB(orb);

              // get object reference from the servant
              org.omg.CORBA.Object ref = rootpoa.servant_to_reference(helloImpl);
              RisikoServer href = RisikoServerHelper.narrow(ref);

              // get the root naming context
              // NameService invokes the name service
              org.omg.CORBA.Object objRef =
                  orb.resolve_initial_references("NameService");
              // Use NamingContextExt which is part of the Interoperable
              // Naming Service (INS) specification.
              NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

              // bind the Object Reference in Naming
              String name = "Hello";
              NameComponent path[] = ncRef.to_name( name );
              ncRef.rebind(path, href);

              System.out.println("HelloServer ready and waiting ...");

              // wait for invocations from clients
              orb.run();
        }catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }

        System.out.println("HelloServer Exiting ...");
    }

    public void startJXTA() throws IOException, PeerGroupException{
      //  this.playerManager=new PlayerManager("foggiano", Server.TCP_PORT);
      //  this.playerManager.init(null,true);
        this.rendex=new Rendezvous();
        rendex.start();
    }


    

    public static void main(String args[])throws Exception  {

        Server server=new Server(args);
        server.start();
        server.startJXTA();
        
   

    }



}
