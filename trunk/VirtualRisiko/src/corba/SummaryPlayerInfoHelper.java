package corba;


/**
* corba/SummaryPlayerInfoHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 14.30.15 CEST
*/

abstract public class SummaryPlayerInfoHelper
{
  private static String  _id = "IDL:corba/SummaryPlayerInfo:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.SummaryPlayerInfo that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.SummaryPlayerInfo extract (org.omg.CORBA.Any a)
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
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [2];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_wstring_tc (0);
          _members0[0] = new org.omg.CORBA.StructMember (
            "username",
            _tcOf_members0,
            null);
          _tcOf_members0 = corba.ResultInfoHelper.type ();
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_sequence_tc (0, _tcOf_members0);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().create_alias_tc (corba.ResultsHelper.id (), "Results", _tcOf_members0);
          _members0[1] = new org.omg.CORBA.StructMember (
            "info",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (corba.SummaryPlayerInfoHelper.id (), "SummaryPlayerInfo", _members0);
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

  public static corba.SummaryPlayerInfo read (org.omg.CORBA.portable.InputStream istream)
  {
    corba.SummaryPlayerInfo value = new corba.SummaryPlayerInfo ();
    value.username = istream.read_wstring ();
    value.info = corba.ResultsHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.SummaryPlayerInfo value)
  {
    ostream.write_wstring (value.username);
    corba.ResultsHelper.write (ostream, value.info);
  }

}
