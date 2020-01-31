package it.sapienza.cs.biometrics.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.sapienza.cs.biometrics.services.CourseService;
import it.sapienza.cs.biometrics.services.LoginService;
import it.sapienza.cs.biometrics.services.UploadImageService;
import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.User;
import it.sapienza.cs.biometrics.model.DTO.LectureDeletionDTO;
import it.sapienza.cs.biometrics.model.DTO.ProfessorDTO;
import it.sapienza.cs.biometrics.model.DTO.UserLoginDTO;

@CrossOrigin("*")
@RestController
public class BiometricController {
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private UploadImageService uploadImageService;
	
	@PostMapping("/login")
	public User login(@RequestBody UserLoginDTO userLoginDTO) {
		System.out.println("Sto effettuando un tentativo di login");
		return loginService.login(userLoginDTO);
	}

	@GetMapping("/getAllCourses")
	public Set<Course> getAllCourses() {
		System.out.println("getAllCourses");
		return courseService.getAllCourses();
	}

	@GetMapping("/getProfessorCourses")
	public Set<Course> getProfessorCourses(@RequestBody ProfessorDTO professorDTO) {
		return courseService.getProfessorCourses(professorDTO.getMatricola());
	}
	
	/*@GetMapping("/getAllCoursesAvailable")
	public Set<Course> getAllCoursesAvailable(@RequestBody UserDTO userDTO) {
		return courseService.getAllCoursesAvailable(userDTO.getEmail());
	}*/
	
	/*@PostMapping("/createLecture")
	public Lecture createLecture(@RequestBody LectureDTO lectureDTO) {
		return courseService.createLecture(lectureDTO);
	}*/
	
	/*@PostMapping("/deleteLecture")
	public String deleteLecture(@RequestBody LectureDeletionDTO deleteLectureDTO) {
		return courseService.deleteLecture(deleteLectureDTO);
	}*/
	
	/*@PostMapping("/updateProfilePicture")
	public String updateProfilePicture(@RequestBody MultipartFile file) {
		return uploadImageService.updateProfilePicture(file);
	}*/

}
