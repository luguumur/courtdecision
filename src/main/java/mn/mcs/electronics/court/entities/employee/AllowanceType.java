package mn.mcs.electronics.court.entities.employee;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.util.UUIDUtil;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="allowance_type")
public class AllowanceType extends BaseObject {

	private static final long serialVersionUID = 1437889591200654434L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;
	
		
	@Column(name="allowance_type_name")
	private  String allowanceTypeName;

	
		/*getter, setter*/
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAllowanceTypeName() {
		return allowanceTypeName;
	}

	public void setAllowanceTypeName(String allowanceTypeName) {
		this.allowanceTypeName = allowanceTypeName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public AllowanceType(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof AllowanceType)) {
			return false;
		}
		
		final AllowanceType e = (AllowanceType) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

	public int hashCode() {
		return getUuid().hashCode();
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

	
}
