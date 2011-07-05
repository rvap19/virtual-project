package corba;


/**
* corba/RisikoServerHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Corba.idl
* marted� 5 luglio 2011 16.28.52 CEST
*/

abstract public class RisikoServerHelper
{
  private static String  _id = "IDL:corba/RisikoServer:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.RisikoServer that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.RisikoServer extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (corba.RisikoServerHelper.id (), "RisikoServer");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.RisikoServer read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_RisikoServerStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.RisikoServer value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static corba.RisikoServer narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.RisikoServer)
      return (corba.RisikoServer)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba._RisikoServerStub stub = new corba._RisikoServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static corba.RisikoServer unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.RisikoServer)
      return (corba.RisikoServer)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba._RisikoServerStub stub = new corba._RisikoServerStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
