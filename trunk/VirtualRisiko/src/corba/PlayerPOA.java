package corba;


/**
* corba/PlayerPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* venerd� 1 luglio 2011 14.23.09 CEST
*/

public abstract class PlayerPOA extends org.omg.PortableServer.Servant
 implements corba.PlayerOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getUserInfo", new java.lang.Integer (0));
    _methods.put ("notifyNewGame", new java.lang.Integer (1));
    _methods.put ("notifyNewRegistration", new java.lang.Integer (2));
    _methods.put ("notifyStart", new java.lang.Integer (3));
    _methods.put ("notifyNewPlayer", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // corba/Player/getUserInfo
       {
         corba.UserInfo $result = null;
         $result = this.getUserInfo ();
         out = $rh.createReply();
         corba.UserInfoHelper.write (out, $result);
         break;
       }

       case 1:  // corba/Player/notifyNewGame
       {
         corba.PartitaInfo partita = corba.PartitaInfoHelper.read (in);
         this.notifyNewGame (partita);
         out = $rh.createReply();
         break;
       }

       case 2:  // corba/Player/notifyNewRegistration
       {
         corba.RegistrationInfo registration = corba.RegistrationInfoHelper.read (in);
         this.notifyNewRegistration (registration);
         out = $rh.createReply();
         break;
       }

       case 3:  // corba/Player/notifyStart
       {
         String managerName = in.read_wstring ();
         this.notifyStart (managerName);
         out = $rh.createReply();
         break;
       }

       case 4:  // corba/Player/notifyNewPlayer
       {
         corba.UserInfo userInfo = corba.UserInfoHelper.read (in);
         this.notifyNewPlayer (userInfo);
         out = $rh.createReply();
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:corba/Player:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public Player _this() 
  {
    return PlayerHelper.narrow(
    super._this_object());
  }

  public Player _this(org.omg.CORBA.ORB orb) 
  {
    return PlayerHelper.narrow(
    super._this_object(orb));
  }


} // class PlayerPOA
