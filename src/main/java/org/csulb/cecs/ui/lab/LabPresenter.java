package org.csulb.cecs.ui.lab;

import java.util.List;

import org.csulb.cecs.domain.Lab;
import org.csulb.cecs.dto.LabDAO;
import org.csulb.cecs.ui.ViewToken;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.events.EventBus;
import org.vaadin.spring.mvp.MvpHasPresenterHandlers;
import org.vaadin.spring.mvp.MvpView;
import org.vaadin.spring.mvp.presenter.AbstractMvpPresenterView;
import org.vaadin.spring.navigator.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

@SuppressWarnings("serial")
@UIScope
//@Secured({"ROLE_ADMIN"})
@VaadinView(name=ViewToken.LABS)
public class LabPresenter extends AbstractMvpPresenterView<LabPresenter.LabView> implements LabPresenterHandlers {

	@Autowired
	private LabDAO labDAO;
	
	public LabDAO getLabDAO() {
		return labDAO;
	}

	public void setLabDAO(LabDAO labDAO) {
		this.labDAO = labDAO;
	}

	public interface LabView extends MvpView, MvpHasPresenterHandlers<LabPresenterHandlers> {
		void initView();
		void setErrorMessage(String message);
	}
	
	@Autowired
	public LabPresenter(LabView view, EventBus eventBus) {
		super(view, eventBus);
		getView().setPresenterHandlers(this);
	
	}	

	@Override
	public void enter(ViewChangeEvent event) {
		getView().initView();
		
	}

	@Override
	public int saveLab(Lab lab) {
		try{
			if(!labDAO.isAlreadyExist(lab.getBuilding(), lab.getRoomNo()))
				labDAO.addLab(lab);
			else
				return 0;
		}catch(HibernateException he){
			he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public int updateLab(Lab Lab) {
		try{
			labDAO.updateLab(Lab);
		}catch(HibernateException he){
			he.printStackTrace();
			return 2;
		}
		return 1;
	}

	@Override
	public List<Lab> getAllLabs() {
		try{
			return labDAO.getAllLabs();
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Lab> searchLab(String serchString) {
		try{
			return labDAO.searchLab(serchString);
		}catch(HibernateException he){
			he.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteLab(Lab lab) {
		try{
			labDAO.deleteRoom(lab);
		}catch(HibernateException he){
			he.printStackTrace();
			return 2;
		}
		return 1;
	}

}
