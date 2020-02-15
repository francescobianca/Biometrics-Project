package it.sapienza.cs.biometrics.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.Lecture;
import it.sapienza.cs.biometrics.model.Professor;
import it.sapienza.cs.biometrics.model.Student;
import it.sapienza.cs.biometrics.model.DTO.LectureDTO;
import it.sapienza.cs.biometrics.repositories.CourseDAO;
import it.sapienza.cs.biometrics.repositories.LectureDAO;
import it.sapienza.cs.biometrics.repositories.ProfessorDAO;
import it.sapienza.cs.biometrics.repositories.StudentDAO;

@Service
public class CourseService {

	@Autowired
	private ProfessorDAO professorDAO;
	
	@Autowired
	private StudentDAO studentDAO;

	@Autowired
	private CourseDAO courseDAO;

	@Autowired
	private LectureDAO lectureDAO;

	public Set<Course> getAllCourses() {
		Iterable<Course> allCourses = courseDAO.findAll();
		Set<Course> courses = new HashSet<Course>();
		for (Course course : allCourses)
			courses.add(course);
		return courses;
	}

	public Set<Course> getProfessorCourses(String matricola) {
		Professor professor = professorDAO.findById(matricola).get();
		return professor.getTeachedCourses();
	}
	
	// Available Tutti quelli aperti tranne quelli a cui sono già iscritto
	public Set<Course> getAvailableCourse(String matricola) {
		//return courseDAO.findByOpenBooking();
		Set<Course> available = courseDAO.findByOpenBooking();
		Student s = studentDAO.findById(matricola).get();
		Set<Course> followed = s.getFollowingCourses();
		// Differenza tra i set 
		SetView<Course> difference = Sets.difference(available, followed);
		return difference;
	}
	
	public Set<Course> subscribe(Integer courseId, String matricola) {
		Student s = studentDAO.findById(matricola).get();
		Course c = courseDAO.findById(courseId).get();
		
		s.getFollowingCourses().add(c);
		studentDAO.save(s);
		
		return s.getFollowingCourses();
	}

	public Set<Lecture> getCourseLectures(String code) {
		Integer id = Integer.parseInt(code);
		return lectureDAO.findByCourseCode(id);
	}
	
	public Set<Lecture> getCourseLecturesTerminate(String code) {
		Integer id = Integer.parseInt(code);
		return lectureDAO.findByCourseAndTerminate(id);
	}

	public Lecture createLecture(LectureDTO lectureDTO) {
		Course course = courseDAO.findById(lectureDTO.getCourse()).get();

		Lecture lecture = new Lecture();
		lecture.setDescription(lectureDTO.getDescription());
		lecture.setDate(lectureDTO.getDate());
		lecture.setCourse(course);

		lectureDAO.save(lecture);

		int id = lecture.getLectureId();

		lecture = lectureDAO.findById(id).get();

		return lecture;
	}
	
	public Set<Lecture> closeLecture(Integer lectureId, String courseCode) {
		lectureDAO.updateLectureEnd(lectureId);
		Integer id = Integer.parseInt(courseCode);
		return lectureDAO.findByCourseAndTerminate(id);
	}

}
