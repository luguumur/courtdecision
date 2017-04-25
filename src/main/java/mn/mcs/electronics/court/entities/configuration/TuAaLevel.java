/* 
 * File Name: 		TuAaLevel.java
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
@Table(name="tu_aa_level")
public class TuAaLevel extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2902578352511638070L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@ManyToOne
	@JoinColumn(name="sort")
	private TuAaSort tuAaSort;

	@ManyToOne
	@JoinColumn(name="rank")
	private OccupationRank tuAaRank;
	
	/*getter, setter*/
	
	public TuAaSort getTuAaSort() {
		return tuAaSort;
	}

	public void setTuAaSort(TuAaSort tuAaSort) {
		this.tuAaSort = tuAaSort;
	}

	public OccupationRank getTuAaRank() {
		return tuAaRank;
	}

	public void setTuAaRank(OccupationRank tuAaRank) {
		this.tuAaRank = tuAaRank;
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

	public TuAaLevel(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof TuAaLevel)) {
			return false;
		}
		
		final TuAaLevel e = (TuAaLevel) o;

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
