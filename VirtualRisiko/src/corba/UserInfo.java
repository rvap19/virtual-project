package corba;


/**
* corba/UserInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 7 giugno 2011 20.10.13 CEST
*/

public final class UserInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String JXTA_ID = null;
  public String URI = null;
  public String username = null;
  public String password = null;
  public boolean logged = false;

  public UserInfo ()
  {
  } // ctor

  public UserInfo (String _JXTA_ID, String _URI, String _username, String _password, boolean _logged)
  {
    JXTA_ID = _JXTA_ID;
    URI = _URI;
    username = _username;
    password = _password;
    logged = _logged;
  } // ctor

} // class UserInfo
