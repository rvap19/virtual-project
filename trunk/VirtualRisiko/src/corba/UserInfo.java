package corba;


/**
* corba/UserInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 16.28.52 CEST
*/

public final class UserInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String username = null;
  public String password = null;
  public boolean logged = false;

  public UserInfo ()
  {
  } // ctor

  public UserInfo (String _username, String _password, boolean _logged)
  {
    username = _username;
    password = _password;
    logged = _logged;
  } // ctor

} // class UserInfo
