/* 
 * File Name: 		UtTzTtTuLevel.java
 * 
 * Created By: 		B.Erdenetuya
 * Created Date: 	Jul 4, 2012
 * 
 * History
 * ------------------------------------------------------------------------------
 * Date							Programmer						Description
 * ------------------------------------------------------------------------------
 * Jul 4, 2012 1.0.0 			B.Erdenetuya					Шинээр үүсгэв.
 * 
 * -----------------------------------------------------------------------------
 * 
 * ALL RIGHTS RESERVED COPYRIGHT (C) 2011 MCS ELECTRONICS CO.,LTD SOFTWARE DEPARTMENT
 */

package mn.mcs.electronics.court.entities.configuration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="ut_tz_tt_tu_level")
public class UtTzTtTuLevel extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4982734388696759849L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	
	@ManyToOne
	@JoinColumn(name="sort")
	private UtTzTtTuSort utTzTtTuSort;

	@ManyToOne
	@JoinColumn(name="rank")
	private OccupationRank utTzTtTuRank;
	
		
	/*getter, setter*/


	public UtTzTtTuSort getUtTzTtTuSort() {
		return utTzTtTuSort;
	}

	public void setUtTzTtTuSort(UtTzTtTuSort utTzTtTuSort) {
		this.utTzTtTuSort = utTzTtTuSort;
	}

	public OccupationRank getUtTzTtTuRank() {
		return utTzTtTuRank;
	}

	public void setUtTzTtTuRank(OccupationRank utTzTtTuRank) {
		this.utTzTtTuRank = utTzTtTuRank;
	}
	

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

	public UtTzTtTuLevel(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof UtTzTtTuLevel)) {
			return false;
		}
		
		final UtTzTtTuLevel e = (UtTzTtTuLevel) o;

		if (e.getId() == null)
			return false;
		
		if (this.getId() == null)
			return false;

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
