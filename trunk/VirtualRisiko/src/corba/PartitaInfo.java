package corba;


/**
* corba/PartitaInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 7 giugno 2011 20.10.13 CEST
*/

public final class PartitaInfo implements org.omg.CORBA.portable.IDLEntity
{
  public boolean active = false;
  public short playersNumber = (short)0;
  public int id = (int)0;
  public short maxTurns = (short)0;
  public short maxPlayers = (short)0;
  public String name = null;

  public PartitaInfo ()
  {
  } // ctor

  public PartitaInfo (boolean _active, short _playersNumber, int _id, short _maxTurns, short _maxPlayers, String _name)
  {
    active = _active;
    playersNumber = _playersNumber;
    id = _id;
    maxTurns = _maxTurns;
    maxPlayers = _maxPlayers;
    name = _name;
  } // ctor

} // class PartitaInfo