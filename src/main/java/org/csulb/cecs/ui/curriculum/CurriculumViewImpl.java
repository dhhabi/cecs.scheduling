package org.csulb.cecs.ui.curriculum;

import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Curriculum;
import org.csulb.cecs.ui.curriculum.CurriculumPresenter.CurriculumView;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;
import org.vaadin.spring.mvp.view.AbstractMvpView;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
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
	private ComboBox boxAllCourse;
	private ListSelect curriculumCourseList;
	private Button btnSubmit;

	private BeanFieldGroup<Curriculum> binder = new BeanFieldGroup<Curriculum>(
			Curriculum.class);

	final BeanItemContainer<Course> allCourseContainer = new BeanItemContainer<Course>(
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

		HorizontalLayout nameLayout = new HorizontalLayout();
		curriculumForm.addComponent(nameLayout);
		txtName = new TextField("Curriculum Name");
		txtName.setWidth("300px");
		nameLayout.addComponent(txtName);

		boxAllCurriculum = new ComboBox("All Curriculum");
		boxAllCurriculum.setWidth("300px");
		boxAllCurriculum.setNullSelectionAllowed(false);
		nameLayout.addComponent(boxAllCurriculum);

		boxAllCourse = new ComboBox("All Courses");
		boxAllCourse.setWidth("300px");
		boxAllCourse.setContainerDataSource(allCourseContainer);
		boxAllCourse.setNullSelectionAllowed(false);
		curriculumForm.addComponent(boxAllCourse);

		curriculumCourseList = new ListSelect("Selected Courses");
		curriculumCourseList.setWidth("300px");
		curriculumForm.addComponent(curriculumCourseList);

		btnSubmit = new Button("Submit");
		curriculumForm.addComponent(btnSubmit);

		binder.bind(txtName, "name");

		setSizeFull();
	}

	@Override
	public void initView() {
		Curriculum curriculum = new Curriculum();
		binder.setItemDataSource(curriculum);

		txtName.setValidationVisible(false);

		// Populate all courses
		// TODO populate all course
		boxAllCourse.removeAllItems();
		if (mvpPresenterHandlers.getAllCourses() != null) {
			for (Course course : mvpPresenterHandlers.getAllCourses()) {
				allCourseContainer.addItem(course);
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
