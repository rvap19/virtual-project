package corba;


/**
* corba/StatisticsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* venerd� 17 giugno 2011 4.01.34 CEST
*/

abstract public class StatisticsHelper
{
  private static String  _id = "IDL:corba/Statistics:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.Summary[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.Summary[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = corba.SummaryHelper.type ();
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (corba.StatisticsHelper.id (), "Statistics", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.Summary[] read (org.omg.CORBA.portable.InputStream istream)
  {
    corba.Summary value[] = null;
    int _len0 = istream.read_long ();
    value = new corba.Summary[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = corba.SummaryHelper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.Summary[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      corba.SummaryHelper.write (ostream, value[_i0]);
  }

}