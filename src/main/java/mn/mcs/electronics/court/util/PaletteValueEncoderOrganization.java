package mn.mcs.electronics.court.util;

import mn.mcs.electronics.court.entities.organization.Organization;

import org.apache.tapestry5.ValueEncoder;
import org.hibernate.Session;


public class PaletteValueEncoderOrganization implements ValueEncoder<Organization>{
	
	private Session session;
	
	public PaletteValueEncoderOrganization(Session session) {
		this.session = session;
	}


	public Organization toValue(String value){
		Long id = new Long (value);
		return (Organization)session.get(Organization.class, id);
	}

	@Override
	public String toClient(Organization value) {
		return value.getId().toString();
	}
}
