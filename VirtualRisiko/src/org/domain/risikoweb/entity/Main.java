package org.domain.risikoweb.entity;


import java.util.Hashtable;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
public class Main {

	public static void main(String[] a) throws Exception {
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		env.put("java.naming.provider.url", "jnp://localhost:1099");
		env.put("java.naming.factory.url.pkgs","org.jboss.naming:org.jnp.interfaces");

		Object obj = new InitialContext(env).lookup("RisikoRemoteService");
		
		RisikoRemote service = (RisikoRemote)PortableRemoteObject.narrow(obj,RisikoRemote.class);

  }

}
