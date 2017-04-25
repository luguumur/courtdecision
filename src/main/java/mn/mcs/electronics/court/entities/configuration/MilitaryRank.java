package mn.mcs.electronics.court.entities.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.enums.MilitaryRankType;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name = "military")
public class MilitaryRank extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791224537885740057L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name = "name")
	private String militaryName;

	@Column(name = "period")
	private Integer period;

	@Column(name = "type")
	@Enumerated(EnumType.ORDINAL)
	private MilitaryRankType militaryRankType;

	@Column(name = "is_top")
	private Boolean isTop;

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

	public String getMilitaryName() {
		return militaryName;
	}

	public void setMilitaryName(String militaryName) {
		this.militaryName = militaryName;
	}

	public MilitaryRankType getMilitaryRankType() {
		return militaryRankType;
	}

	public void setMilitaryRankType(MilitaryRankType militaryRankType) {
		this.militaryRankType = militaryRankType;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public MilitaryRank() {
		uuid = UUIDUtil.getUUID();
	}

	public Boolean getIsTop() {
		return isTop;
	}

	public void setIsTop(Boolean isTop) {
		this.isTop = isTop;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;

		if (o == null)
			return false;

		if (!(o instanceof MilitaryRank)) {
			return false;
		}

		final MilitaryRank e = (MilitaryRank) o;

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
		return new ToStringBuilder(this).append("id", this.id)
				.append("uuid", this.uuid).toString();
	}
}
