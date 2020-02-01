package it.sapienza.cs.biometrics.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.Lecture;
import it.sapienza.cs.biometrics.model.Professor;
import it.sapienza.cs.biometrics.model.DTO.LectureDTO;
import it.sapienza.cs.biometrics.repositories.CourseDAO;
import it.sapienza.cs.biometrics.repositories.LectureDAO;
import it.sapienza.cs.biometrics.repositories.ProfessorDAO;

@Service
public class CourseService {

	@Autowired
	private ProfessorDAO professorDAO;

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

	public Set<Lecture> getCourseLectures(String code) {
		Integer id = Integer.parseInt(code);
		return lectureDAO.findByCourseCode(id);
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

}
