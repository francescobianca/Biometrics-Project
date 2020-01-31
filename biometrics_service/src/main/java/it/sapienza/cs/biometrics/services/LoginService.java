package it.sapienza.cs.biometrics.services;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sapienza.cs.biometrics.model.Professor;
import it.sapienza.cs.biometrics.model.Student;
import it.sapienza.cs.biometrics.model.User;
import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.DTO.UserLoginDTO;
import it.sapienza.cs.biometrics.repositories.CourseDAO;
import it.sapienza.cs.biometrics.repositories.ProfessorDAO;
import it.sapienza.cs.biometrics.repositories.StudentDAO;
import it.sapienza.cs.biometrics.repositories.UserDAO;

@Service
public class LoginService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProfessorDAO professorDAO;
	@Autowired
	private StudentDAO studentDAO;
	@Autowired
	private CourseDAO courseDAO;

	public User login(UserLoginDTO userLoginDTO) {

		System.out.println(userLoginDTO.getMatricola());
		System.out.println(userLoginDTO.getPassword());

		Professor isProfessor = null;
		Student isStudent = null;
		
		// Implementato come serie di try/catch altrimenti continuava a dare errori.

		try {
			isProfessor = professorDAO.findById(userLoginDTO.getMatricola()).get();

			if (isProfessor != null) {
				if (isProfessor.getPassword().equals(userLoginDTO.getPassword())) {
					System.out.println("Sono loggato come professore");
					
					// Gestione dei casi professoressa - carico i miei corsi
					Set<Course> courses = courseDAO.findByProfessorMatricola(isProfessor.getMatricola());
					isProfessor.setTeachedCourses(courses);
					for (Course course : isProfessor.getTeachedCourses()) {
						course.setLectures(new HashSet<>());
					}
					
					return isProfessor;
				}
			}

		} catch (NoSuchElementException e) {
			;
		}

		try {
			isStudent = studentDAO.findById(userLoginDTO.getMatricola()).get();

			if (isStudent != null) {
				if (isStudent.getPassword().equals(userLoginDTO.getPassword())) {
					System.out.println("Sono loggato come studente");
					
					// Gestioni casi studente
					Set<Course> followingCourse = courseDAO.findByStudentsMatricola(isStudent.getMatricola());
					isStudent.setFollowingCourses(followingCourse);
					
					return isStudent;
				}
			}

		} catch (NoSuchElementException e) {
			;
		}

		return null;
	}

}