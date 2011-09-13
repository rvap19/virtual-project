package corba;


/**
* corba/PartitaInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 12 luglio 2011 17.24.43 CEST
*/

public final class PartitaInfo implements org.omg.CORBA.portable.IDLEntity
{
  public String name = null;
  public String type = null;
  public short playersNumber = (short)0;
  public int id = (int)0;
  public short maxTurns = (short)0;
  public short maxPlayers = (short)0;
  public String managerUsername = null;
  public String tournament = null;
  public short phase = (short)0;

  public PartitaInfo ()
  {
  } // ctor

  public PartitaInfo (String _name, String _type, short _playersNumber, int _id, short _maxTurns, short _maxPlayers, String _managerUsername, String _tournament, short _phase)
  {
    name = _name;
    type = _type;
    playersNumber = _playersNumber;
    id = _id;
    maxTurns = _maxTurns;
    maxPlayers = _maxPlayers;
    managerUsername = _managerUsername;
    tournament = _tournament;
    phase = _phase;
  } // ctor

} // class PartitaInfo