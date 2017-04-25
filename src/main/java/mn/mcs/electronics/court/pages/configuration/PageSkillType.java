package mn.mcs.electronics.court.pages.configuration;

import java.util.List;

import mn.mcs.electronics.court.dao.CoreDAO;
import mn.mcs.electronics.court.entities.configuration.SkillType;
import mn.mcs.electronics.court.model.SkillGroupSelectionModel;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;

public class PageSkillType {
	
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
	private SkillType skill;
	
	@Property
	private List<SkillType> listSkillType;
	
	@Property
	private SkillType valueSkillType;
	
	void beginRender(){
		if(skill ==null)
			skill = new SkillType();
		
		listSkillType = dao.getListSkillType(null);
	}
	
	@CommitAfter
	void onSelectedFromSave(){
		dao.saveOrUpdateObject(skill);
		skill = new SkillType();
	}
	
	void onActionFromEdit(SkillType skill){
		this.skill = skill;
	}
	
	@CommitAfter
	void onActionFromDelete(SkillType skill){
		dao.deleteObject(skill);
	}

	/*
	 * Selection model
	 * */
	public SelectModel getSkillGroupSelectionModel(){
		SkillGroupSelectionModel sm = new SkillGroupSelectionModel(dao);
		return sm;
	}
}
