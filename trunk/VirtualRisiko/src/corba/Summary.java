package corba;


/**
* corba/Summary.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* mercoledý 6 luglio 2011 18.07.58 CEST
*/

public final class Summary implements org.omg.CORBA.portable.IDLEntity
{
  public String username = null;
  public int score = (int)0;

  public Summary ()
  {
  } // ctor

  public Summary (String _username, int _score)
  {
    username = _username;
    score = _score;
  } // ctor

} // class Summary
