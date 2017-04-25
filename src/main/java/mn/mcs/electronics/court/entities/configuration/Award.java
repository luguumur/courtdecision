package mn.mcs.electronics.court.entities.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.entities.employee.Honour;
import mn.mcs.electronics.court.enums.AwardType;


@Entity
@Table(name="award")
public class Award extends BaseObject{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3189142931530861637L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;	
	
	@Column(name="award_type")
	@Enumerated(EnumType.ORDINAL)
	private AwardType awardType;
	
	@Column(name="name")
	private String name;
	
	@Column(name="shagnal_zai")
	private Integer shagnalZai;

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

	public AwardType getAwardType() {
		return awardType;
	}

	public void setAwardType(AwardType awardType) {
		this.awardType = awardType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShagnalZai() {
		return shagnalZai;
	}

	public void setShagnalZai(Integer shagnalZai) {
		this.shagnalZai = shagnalZai;
	}

	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof Honour)) {
			return false;
		}
		
		final Honour e = (Honour) o;

		if (e.getId() == null)
			return false;
		
		/* May 31, 2011 У.Наранхүү Start */
		if (this.getId() == null)
			return false;
		/* May 31, 2011 У.Наранхүү End */

		return getId().equals(e.getId());
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("uuid",
				this.uuid).toString();
	}

	@Override
	public int hashCode() {
		return getUuid().hashCode();
	}

}
