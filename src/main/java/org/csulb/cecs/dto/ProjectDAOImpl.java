package org.csulb.cecs.dto;

import javax.transaction.Transactional;

import org.csulb.cecs.domain.ScheduleProject;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ProjectDAOImpl implements ProjectDAO{

	@Autowired
	SessionFactory _sessionFactory;
	
	private Session getSession(){
		return _sessionFactory.getCurrentSession();
	}
	
	@Override
	public void saveScheduleProject(ScheduleProject scheduleProject) {
		getSession().save(scheduleProject);		
	}

	@Override
	public ScheduleProject getScheduleProject(String semester, String year) {
		ScheduleProject scheduleProject = (ScheduleProject) getSession().createQuery("from ScheduleProject where semester=:semester and year=:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		Hibernate.initialize(scheduleProject);
		scheduleProject.getCourseList().size();
		scheduleProject.getInstructorList().size();
		scheduleProject.getRoomList().size();
		scheduleProject.getSections().size();
		return scheduleProject;
	}

	@Override
	public ScheduleProject getScheduleProjectWithCourseListInit(
			String semester, String year) {
		ScheduleProject scheduleProject = (ScheduleProject) getSession().createQuery("from ScheduleProject where semester=:semester and year=:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		Hibernate.initialize(scheduleProject);
		scheduleProject.getCourseList().size();
		return scheduleProject;
	}

	@Override
	public ScheduleProject getScheduleProjectWithRoomListInit(String semester,
			String year) {
		ScheduleProject scheduleProject = (ScheduleProject) getSession().createQuery("from ScheduleProject where semester=:semester and year=:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		Hibernate.initialize(scheduleProject);
		scheduleProject.getRoomList().size();
		return scheduleProject;
	}

	@Override
	public ScheduleProject getScheduleProjectWithSectionListInit(
			String semester, String year) {
		ScheduleProject scheduleProject = (ScheduleProject) getSession().createQuery("from ScheduleProject where semester=:semester and year=:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		Hibernate.initialize(scheduleProject);
		scheduleProject.getSections().size();
		return scheduleProject;
	}

	@Override
	public ScheduleProject getScheduleProjectWithInstructorListInit(
			String semester, String year) {
		ScheduleProject scheduleProject = (ScheduleProject) getSession().createQuery("from ScheduleProject where semester=:semester and year=:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult();
		Hibernate.initialize(scheduleProject);
		scheduleProject.getInstructorList().size();
		return scheduleProject;
	}

	@Override
	public boolean isAlreadyExists(String semester, String year) {
		return (getSession().createQuery("select 1 from ScheduleProject p where p.semester =:semester and p.year =:year")
				.setParameter("semester", semester)
				.setParameter("year", year)
				.uniqueResult() != null);
	}
	

}
