package mn.mcs.electronics.court.util;

import mn.mcs.electronics.court.entities.Permission;

import org.apache.tapestry5.ValueEncoder;
import org.hibernate.Session;


public class PaletteValueEncoder implements ValueEncoder<Permission>{
	
	private Session session;
	
	public PaletteValueEncoder(Session session) {
		this.session = session;
	}


	public Permission toValue(String value){
		Long id = new Long (value);
		return (Permission)session.get(Permission.class, id);
	}

	@Override
	public String toClient(Permission value) {
		return value.getId().toString();
	}
}
