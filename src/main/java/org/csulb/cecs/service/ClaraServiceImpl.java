package org.csulb.cecs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Day;
import org.csulb.cecs.domain.LabType;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.TwoDayScheduleImportance;
import org.csulb.cecs.dto.CurrentSemesterDAO;
import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.dto.RoomDAO;
import org.csulb.cecs.dto.SurveyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.spi.ScanException;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

@Service
public class ClaraServiceImpl implements ClaraService {

	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	private RoomDAO roomDAO;
	@Autowired
	private CurrentSemesterDAO currentSemesterDAO;
	@Autowired
	private SurveyDAO surveyDAO;

	public ClaraServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("serial")
	@Override
	public StreamResource createProgram(final String semester, final String year) {
		return new StreamResource(new StreamSource() {

			@Override
			public InputStream getStream() {

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				PrintWriter writer = new PrintWriter(bos, true);
				// Write the clara program lines from here
				writer.write("//This is a auto-generated program, Please make sure all the entries are correct \n");
				writer.write("//\tCECS Class Scheduling\n");
				StringBuilder claraString = new StringBuilder();
				StringBuilder roomString = new StringBuilder(
						"subset CECSLectureRoom, [");
				StringBuilder labString = new StringBuilder("subset CECSLab, [");
				// Different types of labs
				StringBuilder macLabs = new StringBuilder("const lab_mac, [");
				StringBuilder windowsLabs = new StringBuilder(
						"const lab_windows, [");
				StringBuilder linuxLabs = new StringBuilder(
						"const lab_linux, [");
				StringBuilder networkLabs = new StringBuilder(
						"const lab_network, [");
				StringBuilder otherLabs = new StringBuilder(
						"const lab_other, [");

				// Write all rooms to the clara program
				for (Room room : roomDAO.getAllRooms()) {
					if (!room.isLab()) {
						roomString.append("\"" + room.toString() + "\",");
					} else {
						labString.append("\"" + room.toString() + "\",");
						if (room.getLabType().equals(LabType.MAC))
							macLabs.append("\"" + room.toString() + "\",");
						else if (room.getLabType().equals(LabType.LINUX))
							linuxLabs.append("\"" + room.toString() + "\",");
						else if (room.getLabType().equals(LabType.NETWORKLAB))
							networkLabs.append("\"" + room.toString() + "\",");
						else if (room.getLabType().equals(LabType.OTHER))
							otherLabs.append("\"" + room.toString() + "\",");
						else if (room.getLabType().equals(LabType.WINDOWS))
							windowsLabs.append("\"" + room.toString() + "\",");
					}

				}
				// Delete last extra comma from both strings
				roomString.deleteCharAt(roomString.length() - 1);
				roomString.append("]");
				// labString.deleteCharAt(labString.length()-1);
				labString.append("\"NO_LAB\"]");
				macLabs.deleteCharAt(macLabs.length() - 1);
				macLabs.append("]");
				windowsLabs.deleteCharAt(windowsLabs.length() - 1);
				windowsLabs.append("]");
				linuxLabs.deleteCharAt(linuxLabs.length() - 1);
				linuxLabs.append("]");
				networkLabs.deleteCharAt(networkLabs.length() - 1);
				networkLabs.append("]");
				otherLabs.deleteCharAt(otherLabs.length() - 1);
				otherLabs.append("]");

				// Write labs to clara program
				claraString.append(roomString);
				claraString.append("\n\n");
				claraString.append(labString);
				claraString.append("\n\n");
				claraString.append(macLabs);
				claraString.append("\n");
				claraString.append(windowsLabs);
				claraString.append("\n");
				claraString.append(linuxLabs);
				claraString.append("\n");
				claraString.append(networkLabs);
				claraString.append("\n");
				claraString.append(otherLabs);
				claraString.append("\n\n");

				StringBuilder activities = new StringBuilder(
						"subset Activity, [");

				for (String activity : AvailableActivities.activities)
					activities.append("\"" + activity + "\", ");

				activities.deleteCharAt(activities.length() - 1);
				activities.append("]");
				claraString.append(activities);
				claraString.append("\n\n");
				claraString.append("subset WeeklyHours, 0..5\n\n");

				String courseModel = new String(
						"model Course\n"
								+ "\tString: name\n"
								+ "\tString: title\n"
								+ "\tActivity: activity\n"
								+ "\tWeeklyHours: lecture_hours\n"
								+ "\tWeeklyHours: activity_hours\n"
								+ "\tList(CECSLab): labs   //if this course has a lab activity, then it must be held in one of these labs\n"
								+ "\tList(CECSLectureRoom): rooms  //lecture rooms that need to be used for this course\n");

				claraString.append(courseModel);
				claraString.append("\n");
				StringBuilder courseSubset = new StringBuilder(
						"subset CECSCourse, [");
				// Create constants for all the courses
				for (Course course : projectDAO
						.getScheduleProjectWithCourseListInit(semester, year)
						.getCourseList()) {
					StringBuilder courseString = new StringBuilder("const "
							+ course.getPrefix() + "" + course.getCourseNo()
							+ ", Course(");
					courseString.append("\"" + course.getPrefix() + " "
							+ course.getCourseNo() + "\",");
					// Add course to subset
					courseSubset.append(course.getPrefix() + ""
							+ course.getCourseNo() + ",");

					courseString.append("\"" + course.getTitle() + "\",");
					courseString.append("\"" + course.getActivity() + "\",");
					courseString.append(course.getLectureHours() + ",");
					courseString.append(course.getActivityHours() + ",");
					if (course.getActivity().equals(
							AvailableActivities.LAB_ACTIVITY)) {
						if (course.getRequiredLab().equals(LabType.LINUX))
							courseString.append("lab_linux,");
						else if (course.getRequiredLab().equals(LabType.MAC))
							courseString.append("lab_mac,");
						else if (course.getRequiredLab().equals(
								LabType.NETWORKLAB))
							courseString.append("lab_network,");
						else if (course.getRequiredLab().equals(LabType.OTHER))
							courseString.append("lab_other,");
						else if (course.getRequiredLab()
								.equals(LabType.WINDOWS))
							courseString.append("lab_windows,");
					} else {
						courseString.append("[],");
					}
					courseString.append("[])");

					claraString.append(courseString);
					claraString.append("\n");
				}
				// add course subset to program
				claraString.append("\n");
				courseSubset.deleteCharAt(courseSubset.length() - 1);
				courseSubset.append("]\n\n");
				claraString.append(courseSubset);
				// add subset Seciton Number to program
				claraString.append("subset SectionNumber, 1..20\n");

				StringBuilder twoDayImportance = new StringBuilder(
						"subset ImportanceLevel, [");
				for (TwoDayScheduleImportance importance : TwoDayScheduleImportance
						.values()) {
					twoDayImportance.append("\"" + importance.toString()
							+ "\",");
				}
				// Remove last comma
				twoDayImportance.deleteCharAt(twoDayImportance.length() - 1);
				twoDayImportance.append("]\n");
				// write importance level subset to program
				claraString.append(twoDayImportance);
				claraString.append("\n");
				// Course load
				claraString.append("subset CourseLoad, 0..5\n");
				// Days of the week
				StringBuilder daySubset = new StringBuilder(
						"subset DayOfWeek, [");
				for (Day day : Day.values()) {
					daySubset.append(day.toString() + ",");
				}
				daySubset.deleteCharAt(daySubset.length() - 1);
				daySubset.append("]\n");

				claraString.append(daySubset);

				String teachHours = "subset TeachHour, 8..22\n"
						+ "subset Half, 0..1\n\n";
				claraString.append(teachHours);

				String functionDuration = "function duration, HalfHour, TeachHalfHour: (begin,end)\n"
						+ "rule duration\n"
						+ "\tif end.half == 1 | begin.half == 0\n"
						+ "\t\treturn HalfHour(end.hour-begin.hour,end.half-begin.half)\n\n"
						+ "\treturn HalfHour(end.hour-begin.hour-1,1)\n\n";
				claraString.append(functionDuration);

				String functionStar = "function *, HalfHour, Integer: n, HalfHour: hh\n\n"
						+ "assert *\n"
						+ "\tn >= 1\n\n"
						+ "rule *\n"
						+ "\tif hh.half == 0\n"
						+ "\t\treturn HalfHour(hh.hour*n,0)\n\n"
						+ "\tif n % 2 == 0\n"
						+ "\t\treturn HalfHour(hh.hour*n + n/2, 0)\n\n"
						+ "\treturn\n"
						+ "\t\treturn HalfHour(hh.hour*n + n/2, 1)\n\n";

				claraString.append(functionStar);

				String functionNextTeachHalfHour = "function next_teach_half_hour, TeachHalfHour, TeachHalfHour: hh\n\n"
						+ "rule next_teach_half_hour\n"
						+ "\tif hh.half == 0\n"
						+ "\t\treturn TeachHalfHour(hh.hour,1)\n\n"
						+ "\treturn TeachHalfHour(hh.hour+1,0)\n\n";

				claraString.append(functionNextTeachHalfHour);

				String functionLessThen = "function <, Boolean, TeachHalfHour: hh1, hh2\n\n"
						+ "rule <\n"
						+ "\tif hh1.hour < hh2.hour\n"
						+ "\t\treturn true\n\n"
						+ "\tif hh1.hour > hh2.hour\n"
						+ "\t\treturn false\n\n"
						+ "\treturn hh1.half < hh2.half\n\n";

				claraString.append(functionLessThen);

				String functionNonOverlapping = "function non_overlapping, Boolean, TeachHalfHour: (begin1,end1,begin2,end2)\n"
						+ "rule non_overlapping\n"
						+ "\treturn end1 < begin2 | end2 < begin1\n\n";

				claraString.append(functionNonOverlapping);

				String subsetTeachHalfHour = "subset TeachHalfHourSubset, enumerate(TeachHalfHour)\n\n";
				claraString.append(subsetTeachHalfHour);

				String modelInstructor = "model Instructor\n"
						+ "\tString: name\n"
						+ "\tCourseLoad: load\n"
						+ "\tImportanceLevel: two_day\n"
						+ "\tMap(DayOfWeek,TeachHalfHourSubset,Boolean): is_available\n\n";

				claraString.append(modelInstructor);
				
				claraString.append("//A constant for each instructor who will teach\n\n");
				
				for(Account instructor:projectDAO.getScheduleProjectWithInstructorListInit(semester, year).getInstructorList()){
					StringBuilder constInstructor = new StringBuilder("const "+instructor.getUsername()+", Instructor(");
					
					
				}
				
				
				
				// write program to file and flush the buffer
				writer.write(claraString.toString());
				writer.flush();
				return new ByteArrayInputStream(bos.toByteArray());
			}
		}, "cecsSchedule.clara");
	}

}
