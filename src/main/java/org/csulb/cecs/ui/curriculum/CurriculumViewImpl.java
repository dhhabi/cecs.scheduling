package org.csulb.cecs.ui.curriculum;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Curriculum;
import org.csulb.cecs.ui.curriculum.CurriculumPresenter.CurriculumView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@UIScope
@VaadinComponent
public class CurriculumViewImpl extends AbstractMvpView implements
		CurriculumView, ClickListener {

	private CurriculumPresenterHandlers mvpPresenterHandlers;

	private VerticalLayout layout;
	private FormLayout curriculumForm;
	private TextField txtName;
	private ComboBox boxAllCurriculum;
	private ComboBox boxAllCourses;
	private ListSelect curriculumCourseList;
	private Button btnAddToCurriculum;
	private Button btnSubmit;
	private Button btnUpdate;
	private Button btnRemove;

	private BeanFieldGroup<Curriculum> binder = new BeanFieldGroup<Curriculum>(
			Curriculum.class);

	final BeanItemContainer<Course> allCourseContainer = new BeanItemContainer<Course>(
			Course.class);
	final BeanItemContainer<Curriculum> allCurriculumContainer = new BeanItemContainer<Curriculum>(
			Curriculum.class);
	
	final BeanItemContainer<Course> curriculumCourseContainer = new BeanItemContainer<Course>(
			Course.class);
	

	@Override
	public void postConstruct() {
		super.postConstruct();
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		setCompositionRoot(layout);

		
		curriculumForm = new FormLayout();
		layout.addComponent(curriculumForm);
		
		curriculumForm.addComponent(new Label("*Select curriculum from all curriculum to update"));
		
		HorizontalLayout nameLayout = new HorizontalLayout();
		curriculumForm.addComponent(nameLayout);
		txtName = new TextField("Curriculum Name");
		txtName.setWidth("300px");
		txtName.setNullRepresentation("");
		nameLayout.addComponent(txtName);

		boxAllCurriculum = new ComboBox("All Curriculum");
		//boxAllCurriculum.setContainerDataSource(allCurriculumContainer);
		boxAllCurriculum.setWidth("300px");
		boxAllCurriculum.setNullSelectionAllowed(false);
		boxAllCurriculum.setNullSelectionAllowed(false);
		nameLayout.addComponent(boxAllCurriculum);
		boxAllCurriculum.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO All Curriculum Value Change Listener
				txtName.setValue((String)boxAllCurriculum.getValue());
				
			}
		});

		HorizontalLayout courseLayout = new HorizontalLayout();
		curriculumForm.addComponent(courseLayout);
		boxAllCourses = new ComboBox();
        boxAllCourses.setContainerDataSource(allCourseContainer);	        
        boxAllCourses.setNullSelectionAllowed(false);
       // boxAllCourses.setItemCaptionPropertyId("title");
        boxAllCourses.setInputPrompt("Select preferred courses");
        boxAllCourses.setWidth("300px");
		courseLayout.addComponent(boxAllCourses);
		btnAddToCurriculum = new Button("Add to Curriculum");
		courseLayout.addComponent(btnAddToCurriculum);

		btnAddToCurriculum.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Add to Curriculum Btn Click
				curriculumCourseContainer.addItem(allCourseContainer.getItem(
						boxAllCourses.getValue()).getBean());
			}
		});

		curriculumCourseList = new ListSelect("Selected Courses");
		curriculumCourseList.setContainerDataSource(curriculumCourseContainer);
		curriculumCourseList.setNullSelectionAllowed(false);
		curriculumCourseList.setWidth("300px");
		curriculumForm.addComponent(curriculumCourseList);

		btnRemove = new Button("Remove from select courses");
		curriculumForm.addComponent(btnRemove);
		btnRemove.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Remove Button Click 
				curriculumCourseList.removeItem(curriculumCourseList.getValue());
				
			}
		});
		
		btnSubmit = new Button("Submit");
		curriculumForm.addComponent(btnSubmit);

		btnSubmit.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Submit Click listener
				try {
					binder.commit();
					
					Curriculum curriculum = binder.getItemDataSource().getBean();
					curriculum.setName(txtName.getValue());
					//init the course list
					for(Object itemId:curriculumCourseContainer.getItemIds()){
						curriculum.getCurriculumCourseList().add(curriculumCourseContainer.getItem(itemId).getBean());
					}
					if (mvpPresenterHandlers.isAlreadyExist(curriculum.getName())) {
						Notification.show("Curriculum already Exist!");
						//btnUpdate.setVisible(true);
					} else {
						if (mvpPresenterHandlers.saveCurriculum(curriculum)) {
							//allCurriculumContainer.addBean(curriculum);
							boxAllCurriculum.addItem(curriculum.getName());
							Notification.show("Curriculum Saved Successfully!",
									Notification.TYPE_TRAY_NOTIFICATION);
						} else {
							Notification.show(
									"Something went wrong please check log !",
									Notification.TYPE_ERROR_MESSAGE);
						}
					}
				} catch (CommitException e) {
					// TODO Auto-generated catch block
					txtName.setValidationVisible(true);
					//e.printStackTrace();
				}
			}
		});

		btnUpdate = new Button("Update");
		curriculumForm.addComponent(btnUpdate);
		
		btnUpdate.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Update Click Listener
				Curriculum curriculum = binder.getItemDataSource().getBean();
				curriculum.setName(txtName.getValue());
				//init the course list
				for(Object itemId:curriculumCourseContainer.getItemIds()){
					curriculum.getCurriculumCourseList().add(curriculumCourseContainer.getItem(itemId).getBean());
				}
				if (mvpPresenterHandlers.saveCurriculum(curriculum)) {
					Notification.show("Curriculum Updated Successfully",
							Notification.TYPE_TRAY_NOTIFICATION);
				} else {
					Notification.show(
							"Something went wrong please see the log!",
							Notification.TYPE_ERROR_MESSAGE);
				}
			}
		});

		binder.bind(txtName, "name");

		setSizeFull();
	}

	@Override
	public void initView() {
		Curriculum curriculum = new Curriculum();
		binder.setItemDataSource(curriculum);

		txtName.setValidationVisible(false);

		// TODO populate all course
		boxAllCourses.removeAllItems();
		if (mvpPresenterHandlers.getAllCourses() != null) {
			for (Course course : mvpPresenterHandlers.getAllCourses()) {
				allCourseContainer.addItem(course);
			}
		}
		// Populate all curriculum in Combobox
		boxAllCurriculum.removeAllItems();
		if (mvpPresenterHandlers.getAllCurriculum() != null) {
			for (Curriculum c : mvpPresenterHandlers.getAllCurriculum()) {
				boxAllCurriculum.addItem(c.getName());
			}
		}
	}

	@Override
	public void buttonClick(ClickEvent event) {

	}

	private void buildForm() {

	}

	@Override
	public void setErrorMessage(String message) {

	}

	@Override
	public void setPresenterHandlers(CurriculumPresenterHandlers arg0) {
		this.mvpPresenterHandlers = arg0;
	}

}
