package corba;


/**
* corba/PlayerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 14.30.15 CEST
*/

abstract public class PlayerHelper
{
  private static String  _id = "IDL:corba/Player:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.Player that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.Player extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (corba.PlayerHelper.id (), "Player");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.Player read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_PlayerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.Player value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static corba.Player narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.Player)
      return (corba.Player)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba._PlayerStub stub = new corba._PlayerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static corba.Player unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.Player)
      return (corba.Player)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba._PlayerStub stub = new corba._PlayerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
