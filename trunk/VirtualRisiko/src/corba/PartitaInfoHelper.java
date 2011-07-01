package corba;


/**
* corba/PartitaInfoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* venerd� 1 luglio 2011 14.23.09 CEST
*/

abstract public class PartitaInfoHelper
{
  private static String  _id = "IDL:corba/PartitaInfo:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.PartitaInfo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.PartitaInfo extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [7];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "name",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "type",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[2] = new org.omg.CORBA.StructMember (
            "playersNumber",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ulong);
          _members0[3] = new org.omg.CORBA.StructMember (
            "id",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[4] = new org.omg.CORBA.StructMember (
            "maxTurns",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_ushort);
          _members0[5] = new org.omg.CORBA.StructMember (
            "maxPlayers",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[6] = new org.omg.CORBA.StructMember (
            "managerUsername",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (corba.PartitaInfoHelper.id (), "PartitaInfo", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.PartitaInfo read (org.omg.CORBA.portable.InputStream istream)
  {
    corba.PartitaInfo value = new corba.PartitaInfo ();
    value.name = istream.read_wstring ();
    value.type = istream.read_wstring ();
    value.playersNumber = istream.read_ushort ();
    value.id = istream.read_ulong ();
    value.maxTurns = istream.read_ushort ();
    value.maxPlayers = istream.read_ushort ();
    value.managerUsername = istream.read_wstring ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.PartitaInfo value)
  {
    ostream.write_wstring (value.name);
    ostream.write_wstring (value.type);
    ostream.write_ushort (value.playersNumber);
    ostream.write_ulong (value.id);
    ostream.write_ushort (value.maxTurns);
    ostream.write_ushort (value.maxPlayers);
    ostream.write_wstring (value.managerUsername);
  }

}
