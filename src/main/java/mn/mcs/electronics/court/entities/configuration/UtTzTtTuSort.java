/* 
 * File Name: 		UtTzTtTuSort.java
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
import javax.persistence.Table;

import mn.mcs.electronics.court.entities.BaseObject;
import mn.mcs.electronics.court.enums.UtTzTtTuCategory;
import mn.mcs.electronics.court.util.UUIDUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@Table(name="ut_tz_tt_tu_sort")
public class UtTzTtTuSort extends BaseObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3139349613437771453L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", length = 32, insertable = true, updatable = false)
	private String uuid;

	@Column(name="name")
	private String name;
	
	@Column(name="name_detail")
	private String nameDetail;
	
//	@Column(name="category")
//	private UtTzTtTuCategory category; 
			
	/*getter, setter*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameDetail() {
		return nameDetail;
	}

	public void setNameDetail(String nameDetail) {
		this.nameDetail = nameDetail;
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
//
//	public UtTzTtTuCategory getCategory() {
//		return category;
//	}
//
//	public void setCategory(UtTzTtTuCategory category) {
//		this.category = category;
//	}

	public UtTzTtTuSort(){
		uuid = UUIDUtil.getUUID();
	}
	
	@Override
	public boolean equals(Object o) {
		
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof UtTzTtTuSort)) {
			return false;
		}
		
		final UtTzTtTuSort e = (UtTzTtTuSort) o;

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
