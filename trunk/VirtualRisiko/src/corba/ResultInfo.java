package corba;


/**
* corba/ResultInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 14.30.15 CEST
*/

public final class ResultInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String gameName = null;
  public String date = null;
  public int score = (int)0;
  public boolean victory = false;

  public ResultInfo ()
  {
  } // ctor

  public ResultInfo (String _gameName, String _date, int _score, boolean _victory)
  {
    gameName = _gameName;
    date = _date;
    score = _score;
    victory = _victory;
  } // ctor

} // class ResultInfo
