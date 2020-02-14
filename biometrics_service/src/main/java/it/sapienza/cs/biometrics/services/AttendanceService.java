package it.sapienza.cs.biometrics.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sapienza.cs.biometrics.model.Attendances;
import it.sapienza.cs.biometrics.repositories.AttendanceDAO;

@Service
public class AttendanceService {
	
	@Autowired
	private AttendanceDAO attendanceDAO;
	
	public Set<Attendances> getLectureAttendances(Integer lectureId) {
		return attendanceDAO.findByLectureLectureId(lectureId);
	}

}
