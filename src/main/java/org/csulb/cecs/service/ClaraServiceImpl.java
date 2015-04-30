package org.csulb.cecs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import org.csulb.cecs.domain.AvailableActivities;
import org.csulb.cecs.domain.LabType;
import org.csulb.cecs.domain.Room;
import org.csulb.cecs.dto.ProjectDAO;
import org.csulb.cecs.dto.RoomDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;

@Service
public class ClaraServiceImpl implements ClaraService {

	@Autowired
	private ProjectDAO projectDAO;
	@Autowired
	private RoomDAO roomDAO;

	@SuppressWarnings("serial")
	@Override
	public StreamResource createProgram() {
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
				
				
				
				writer.write(claraString.toString());
				writer.flush();
				return new ByteArrayInputStream(bos.toByteArray());
			}
		}, "cecsSchedule.clara");
	}

}
