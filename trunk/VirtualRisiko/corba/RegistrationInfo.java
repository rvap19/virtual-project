package corba;


/**
* corba/RegistrationInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 12 luglio 2011 17.24.43 CEST
*/

public final class RegistrationInfo implements org.omg.CORBA.portable.IDLEntity
{
  public int gameID = (int)0;
  public int score = (int)0;
  public boolean victory = false;
  public String username = null;

  public RegistrationInfo ()
  {
  } // ctor

  public RegistrationInfo (int _gameID, int _score, boolean _victory, String _username)
  {
    gameID = _gameID;
    score = _score;
    victory = _victory;
    username = _username;
  } // ctor

} // class RegistrationInfo
