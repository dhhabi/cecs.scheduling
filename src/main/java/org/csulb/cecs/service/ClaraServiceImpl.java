package org.csulb.cecs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.csulb.cecs.domain.Account;
import org.csulb.cecs.domain.Availability;
import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.Course;
import org.csulb.cecs.domain.Curriculum;
import org.csulb.cecs.domain.Day;
import org.csulb.cecs.domain.LabType;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.domain.Section;
import org.csulb.cecs.domain.Survey;
import org.csulb.cecs.domain.TwoDayScheduleImportance;
import org.csulb.cecs.dto.CurrentSemesterDAO;
import org.csulb.cecs.dto.CurriculumDAO;
import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.dto.RoomDAO;
import org.csulb.cecs.dto.SurveyDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

/**
 * This class is used to generate a clara program based on the information
 * present in the database.
 * 
 * 
 * @author preet
 *
 */

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
	@Autowired
	private CurriculumDAO curriculumDAO;
	
	private List<String> allSectionsCourseList = new ArrayList<String>();
		
	public ClaraServiceImpl() {
		// TODO ClaraServiceImpl Constructor
	}

	/**
	 * Create a Clara program using information present in the database
	 * 
	 * @param semester
	 *            , Semester for which we want to find a schedule
	 * @param year
	 *            , Year for which we want to find a schedule
	 * 
	 * @return an object of StreamResource created using an inputstream
	 *         reference to the created clara file
	 */
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

				// TODO Add additional CampusRoom to accomodate extra sections

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

				claraString
						.append("//A constant for each instructor who will teach\n\n");

				StringBuilder instructorSubset = new StringBuilder(
						"subset CECSInstructor, [");

				for (Account instructor : projectDAO
						.getScheduleProjectWithInstructorListInit(semester,
								year).getInstructorList()) {

					Survey survey = surveyDAO.getSurvey(
							instructor.getUsername(), semester, year);

					StringBuilder constInstructor = new StringBuilder("const "
							+ instructor.getUsername() + ", Instructor(");
					constInstructor.append(instructor.getFirstName() + " "
							+ instructor.getLastName());
					constInstructor.append(",");
					constInstructor.append("3,");
					constInstructor.append("\""
							+ survey.getTwoDayScheduleImportance() + "\",\n");
					constInstructor.append("\t{");
					StringBuilder availabilityLine = new StringBuilder();
					boolean halfHour = false;
					for (Availability availability : survey
							.getAvailabilityList()) {
						if (halfHour) {
							availabilityLine.append("[");
							availabilityLine.append("\""
									+ availability.getDay() + "\",");
							// ,TeachHalfHour(8,0),true
							availabilityLine.append("TeachHalfHour("
									+ availability.getTime().getHourOfDay()
									+ ",1),");
							availabilityLine.append(availability.getAvailable()
									+ "],");
						} else {
							availabilityLine.append("[");
							availabilityLine.append("\""
									+ availability.getDay() + "\",");
							// ,TeachHalfHour(8,0),true
							availabilityLine.append("TeachHalfHour("
									+ availability.getTime().getHourOfDay()
									+ ",0),");
							availabilityLine.append(availability.getAvailable()
									+ "],");
						}
						halfHour = !halfHour;
					}
					availabilityLine
							.deleteCharAt(availabilityLine.length() - 1);
					constInstructor.append(availabilityLine);
					constInstructor.append("})\n");
					instructorSubset.append(instructor.getUsername());
					instructorSubset.append(",");
					claraString.append(constInstructor);

				}
				instructorSubset.deleteCharAt(instructorSubset.length() - 1);
				instructorSubset.append("]\n\n");
				claraString.append(instructorSubset);

				// Up to InstructorSubset

				claraString.append("subset DayCode, [\"mw\",\"tth\",\"f\"]\n");
				claraString
						.append("function day_of_week, DayOfWeek, DayCode: dc, Integer: n\n");
				claraString.append("rule day_of_week\n");
				claraString.append("\tif dc == \"mw\"\n");
				claraString.append("\t\tif n == 1\n");
				claraString.append("\t\t\treturn \"m\"\n\n");
				claraString.append("\t\treturn \"w\"\n\n");
				claraString.append("\tif dc == \"tth\"\n");
				claraString.append("\t\tif n == 1\n");
				claraString.append("\t\t\treturn \"t\"\n\n");
				claraString.append("\t\treturn \"th\"\n\n");
				claraString.append("\treturn \"f\"\n\n");

				claraString.append("model LectureSlot\n");
				claraString.append("\tTeachHalfHour: begin\n");
				claraString.append("\tTeachHalfHour: end\n");
				claraString.append("\tDayCode: days\n");
				claraString.append("\tCECSLectureRoom: room\n\n");

				claraString
						.append("function duration, HalfHour, LectureSlot: ls\n\n");

				claraString.append("rule duration\n");
				claraString.append("\tif ls.days == \"f\"\n");
				claraString.append("\t\treturn duration(ls.begin,ls.end)\n\n");
				claraString.append("\treturn 2*duration(ls.begin,ls.end)\n\n");

				claraString
						.append("function is_available, Boolean, Instructor: ins, LectureSlot: ls\n\n");

				claraString.append("local is_available\n");
				claraString.append("\tTeachHalfHour: hh := ls.begin\n");
				claraString
						.append("\tDayOfWeek: d1 := day_of_week(ls.days,1)\n");
				claraString
						.append("\tDayOfWeek: d2 := day_of_week(ls.days,2)\n\n");

				claraString.append("rule is_available\n\n");

				claraString.append("\twhile true\n");
				claraString
						.append("\t\tif !ins.is_available(d1,hh) | !ins.is_available(d2,hh)\n");
				claraString.append("\t\t\treturn false\n\n");

				claraString.append("\t\tif hh == ls.end\n");
				claraString.append("\t\t\treturn true\n\n");

				claraString.append("\thh := next_teach_half_hour(hh)\n\n");

				claraString.append("\treturn false\n\n");

				// TODO Declare as constants each of the lecture slots

				// TODO Declare subset CECSLectureSlot,
				// [ecs_302_mw_830_1000,...,campus_room_1_mw_800_900]

				claraString.append("model LabSlot\n");
				claraString.append("\tTeachHalfHour: begin\n");
				claraString.append("\tDayCode: days\n");
				claraString.append("\tCECSLab: lab\n");

				claraString.append("subset LabSlotIndex, 1..3\n\n");

				claraString.append("index lsi, LabSlotIndex\n\n");

				claraString.append("model CECSSection\n");
				claraString.append("\tCECSCourse: course\n");
				claraString.append("\tSectionNumber: section\n");
				claraString.append("\tCECSInstructor: instructor\n");
				claraString.append("\tCECSLectureSlot: lecture_slot\n");
				claraString.append("\tCECSLab: lab\n");
				claraString.append("\tMap(LabSlotIndex, LabSlot): lab_slot\n");

				claraString.append("constraints CECSSection");

				claraString
						.append("\t//If a course does not require a lab, then this section should use the \"NO_LAB\" lab\n");
				claraString
						.append("\tcourse.activity != \"lab\" -> lab == \"NO_LAB\"\n\n");

				claraString
						.append("\t//If a course has no activity then the lecture must total 3 hours.\n");
				claraString
						.append("\tcourse.activity == \"No Activity\" -> duration(lecture_slot) == HalfHour(3,0)\n\n");

				claraString
						.append("\t//If a course have a discussion then the lecture must total 5 hours.\n");
				claraString
						.append("\tcourse.activity == \"No Activity\" -> duration(lecture_slot) == HalfHour(5,0)\n\n");

				claraString
						.append("\t//The lecture room must be in the list of approved rooms for this course\n");
				claraString
						.append("\tlength(course.rooms) > 0 -> in(course.rooms,lecture_slot.room)\n\n");

				claraString
						.append("\t//The lab must be in the list of approved labs for this course\n");
				claraString
						.append("\tlength(course.labs) > 0 -> in(course.labs,lab)\n\n");

				claraString
						.append("\t//The lab must be the same lab as in the lab_slots.\n");
				claraString
						.append("\tforall lsi, lab == lab_slot(lsi).lab\n\n");

				claraString
						.append("\t//The days of the lecture_slot must match the days of the lab_slots\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> forall(lsi, lecture_slot.days == lab_slot(lsi).days)\n\n");

				claraString
						.append("\t//The first lab_slot must begin when the lecture slot ends.\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> lecture_slot.end == lab_slot(1).begin\n\n");

				claraString
						.append("\t//The second lab_slot must follow the fist lab slot\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> lab_slot(2).begin == next_teach_half_hour(lab_slot(1).begin)\n\n");

				claraString
						.append("\t//The third lab_slot must follow the second lab slot\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> lab_slot(3).begin == next_teach_half_hour(lab_slot(2).begin)\n\n");

				claraString
						.append("\t//The instructor must be available during lab\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> instructor.is_available(day_of_week(lab_slot(1).days,1),lab_slot(1).begin)\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> instructor.is_available(day_of_week(lab_slot(1).days,2),lab_slot(1).begin)\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> instructor.is_available(day_of_week(lab_slot(2).days,1),lab_slot(2).begin)\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> instructor.is_available(day_of_week(lab_slot(2).days,2),lab_slot(2).begin)\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> instructor.is_available(day_of_week(lab_slot(3).days,1),lab_slot(3).begin)\n");
				claraString
						.append("\tlab != \"NO_LAB\" -> instructor.is_available(day_of_week(lab_slot(3).days,2),lab_slot(3).begin)\n\n");

				claraString
						.append("\t//The instructor must be available during lecture\n");
				claraString
						.append("\tis_available(instructor,lecture_slot)\n\n");

				claraString
						.append("\t//If a CECS instructor is Staff, then his section's lecture slot uses a Campus room.\n");

				claraString
						.append("\tinstructor == staff -> has_prefix(lecture_slot.room,\"CampusRoom\")\n\n");

				claraString
						.append("function non_overlapping2, Boolean, CECSSection:(s1,s2)\n\n");

				claraString.append("rule non_overlapping2\n");
				claraString
						.append("\tif s1.course.activity == \"Lab Activity\"\n");
				claraString
						.append("\t\tif s2.course.activity == \"Lab Activity\"\n");
				claraString
						.append("\t\t\treturn non_overlapping(s1.lecture_slot.begin,s1.lab_slot(3).begin,s2.lecture_slot.begin,s2.lab_slot(3).begin)\n\n");

				claraString
						.append("\t\treturn non_overlapping(s1.lecture_slot.begin,s1.lab_slot(3).begin,s2.lecture_slot.begin,s2.lecture_slot.end)\n\n");

				claraString
						.append("\tif s2.course.activity == \"Lab Activity\"\n");
				claraString
						.append("\t\treturn non_overlapping(s1.lecture_slot.begin,s1.lecture_slot.end,s2.lecture_slot.begin,s2.lab_slot(3).begin)\n\n");

				claraString
						.append("\treturn non_overlapping(s1.lecture_slot.begin,s1.lecture_slot.end,s2.lecture_slot.begin,s2.lecture_slot.end)\n\n");

				claraString
						.append("function non_overlapping3, Boolean, CECSSection:(s1,s2,s3)\n");

				claraString
						.append("function non_overlapping3, Boolean, CECSSection:(s1,s2,s3)\n\n");

				claraString.append("rule non_overlapping3\n");
				claraString
						.append("\treturn non_overlapping2(s1,s2) & non_overlapping2(s1,s3) & non_overlapping2(s2,s3)\n\n");

				claraString
						.append("//num_section is a map that maps each CECS course to the number of sections of that course being offered");

				claraString
						.append("const num_section, {[cecs100,2],...,[cecs590_690,1]}, Map(CECSCourse,SectionNumber)\n\n");

				claraString
						.append("const SOFT_WEIGHT, 10000 //amount of weight to be assigned to each soft constraint\n\n");

				claraString.append("model Curriculum\n");
				claraString
						.append("\tList(CECSCourse): courses //the courses that make up the curriculum\n");
				claraString
						.append("\tBoolean: is_essential	//true if it is essential that at least one conbination of sections be non-overlapping\n");
				claraString.append("\tInteger: combinations\n");
				claraString
						.append("\tBoolean: is_bottlenecked //true if one or more of the courses only offers a single section\n");
				claraString
						.append("//Define a constant for each curriculum. For each course, define an index variable whose domain is 1..(num_section(c))\n\n");

				//const ai_curriculum, Curriculum([cecs451,cecs429],false,1,true) example
				
				for(Section section:projectDAO.getScheduleProjectWithSectionListInit(semester, year).getSections()){
					allSectionsCourseList.add(section.getCourse().toString());
				}
				boolean isBottlneck=false;
				
				for(Curriculum curriculum:curriculumDAO.getAllCurriculum()){
					StringBuilder curriculumConst = new StringBuilder("const "+curriculum.getName()+", Curriculum(");
					StringBuilder curriculumCourseList = new StringBuilder("[");
					//curriculumConst.append(curriculum.getCurriculumCourseList());
					for(Course course:curriculum.getCurriculumCourseList()){
						curriculumCourseList.append(course.getPrefix()+""+course.getCourseNo()+",");
					}
					curriculumCourseList.deleteCharAt(curriculumCourseList.length()-1);
					curriculumCourseList.append("]");
					curriculumConst.append(curriculumCourseList);
					curriculumConst.append(",");
					curriculumConst.append(curriculum.isEssential());
					curriculumConst.append(",");
					//TODO add combinations 
					
					curriculumConst.append("combinations");
					curriculumConst.append(",");
					//Check if curriculum is bottleneck
					for(Course course:curriculum.getCurriculumCourseList()){
						if(Collections.frequency(allSectionsCourseList, course.toString())<2){
							isBottlneck = true;
							break;
						}else{
							isBottlneck = false;
						}
					}
					curriculumConst.append(isBottlneck);
					curriculumConst.append(")");
					
					claraString.append(curriculumConst);
					claraString.append("\n");
				}
				claraString.append("\n");
				
				//TODO what is index variable here ? 
				/*index i451, 1..(num_section(cecs451))
				index i429, 1..(num_section(cecs429))
				.
				.
				.
				const cpe_sophomore_fall, Curriculum([cecs271,cecs346],true,4,false)*/
				
				
				//TODO how to assign section code here 
				//subset SectionCode, [["CECS_100",1], ["CECS_100",2], ..., ["CECS_590_690",1]]
				
				
				claraString.append("index sc, sc1, sc2, SectionCode\n\n");

				claraString.append("index i, 0..2\n\n");

				claraString.append("model CECSSchedule\n");
					claraString.append("\tMap(SectionCode,CECSSection) section\n\n");
					
				claraString.append("constraints CECSSchedule\n");
				
				claraString.append("\t//No two sections may use the same lecture slot\n");
				claraString.append("\talldifferent enum(sc,section(sc).lecture_slot)\n\n");

				claraString.append("\t//No two sections may use the same lab at the same time\n");
				claraString.append("\talldifferent enum_if(sc,i,section(sc).lab_slot(i), section(sc).course.activity == \"Lab Activity\")\n\n");

				claraString.append("\t//No two sections that have the same non-staff instructor can overlap\n");
				claraString.append("\tforall sc1, sc2, section(sc1).instructor == section(sc2).instructor & section(sc1).instructor != staff\n"); 
						claraString.append("\t\t-> non_overlapping2(section(sc1),section(sc2))\n\n");

				claraString.append("\t//If it is essential for an instructor to have a two-day schedule, then any two sections he teaches must have same lecture_slot days\n");
				claraString.append("\t//For example, assume section1, section2, and section3 are three sections taught by the instructor\n");
				claraString.append("\tsection1.lecture_slot.days == section2.lecture_slot.days\n");
				claraString.append("\tsection2.lecture_slot.days == section3.lecture_slot.days\n");

				claraString.append("\t//If it is very important for an instructor to have a two-day schedule, then make it a soft constraint:\n");
				claraString.append("\tsoft(section1.lecture_slot.days == section2.lecture_slot.days & section2.lecture_slot.days == section3.lecture_slot.days,\n");
					claraString.append("\t\tupper(SOFT_WEIGHT))\n\n");
				
				//TODO Curriculum Constraints
					
				//TODO Ad hoc constraints
				
				
				
				
				
				
				// write program to file and flush the buffer
				writer.write(claraString.toString());
				writer.flush();
				return new ByteArrayInputStream(bos.toByteArray());
			}
		}, "cecsSchedule.clara");
	}

}
