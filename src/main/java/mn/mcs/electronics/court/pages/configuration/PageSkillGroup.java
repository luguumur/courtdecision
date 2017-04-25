package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SkillGroup;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageSkillGroup {
	
	@Inject
	private CoreDAO dao;
	
	@Inject
	@Property
	@Path("context:/images/b_edit.png")
	private Asset editIcon;

	@Property
	@Inject
	@Path("context:/images/b_drop.png")
	private Asset deleteIcon;
	
	@Property
	@Persist
	private SkillGroup skill;
	
	@Property
	private List<SkillGroup> listSkill;
	
	@Property
	private SkillGroup valueSkill;
	
	void beginRender(){
		if(skill ==null)
			skill = new SkillGroup();
		
		listSkill = dao.getListSkillGroup();
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(skill);
		skill = new SkillGroup();
	}
	
	void onActionFromEdit(SkillGroup skill){
		this.skill = skill;
	}
	
	@CommitAfter
	void onActionFromDelete(SkillGroup skill){
		dao.deleteObject(skill);
	}

}
