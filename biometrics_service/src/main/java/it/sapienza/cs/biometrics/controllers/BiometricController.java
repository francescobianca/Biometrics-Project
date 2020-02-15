package it.sapienza.cs.biometrics.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.sapienza.cs.biometrics.services.AttendanceService;
import it.sapienza.cs.biometrics.services.CourseService;
import it.sapienza.cs.biometrics.services.LoginService;
import it.sapienza.cs.biometrics.services.UploadImageService;
import it.sapienza.cs.biometrics.model.Attendances;
import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.Lecture;
import it.sapienza.cs.biometrics.model.User;
import it.sapienza.cs.biometrics.model.DTO.LectureDTO;
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
	private AttendanceService attendanceService;
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

	@GetMapping("/getCourseLectures")
	public Set<Lecture> getCourseLectures(@RequestParam String code) {
		System.out.println("OK");
		return courseService.getCourseLecturesTerminate(code);
	}
	
	@GetMapping("/getAvailableCourse")
	public Set<Course> getCourseAvailable(@RequestParam String matricola) {
		return courseService.getAvailableCourse(matricola);
	}
	
	@GetMapping("/subscribeCourse")
	public Set<Course> subscribeCourse(@RequestParam Integer courseId, @RequestParam String matricola) {
		return courseService.subscribe(courseId, matricola);
	}

	@PostMapping("/createLecture")
	public Lecture createLecture(@RequestBody LectureDTO lectureDTO) {
		return courseService.createLecture(lectureDTO);
	}
	
	@GetMapping("/closeLecture")
	public Set<Lecture> closeLecture(@RequestParam Integer lectureId, @RequestParam String courseCode) {
		
		// Qua va inserito il codice per comunicare con arduino e prendere le presenze del fingerprint
		
		return courseService.closeLecture(lectureId, courseCode);
	}

	@GetMapping("/getSheet")
	public String downloadSheet(@RequestParam Integer lectureId) {
		System.out.println(lectureId);
		// Ho un lectureId : mi servono le presenze per quella lectureId
		Set<Attendances> lectureAttendances = attendanceService.getLectureAttendances(lectureId);
		// Adesso tramite il set delle presenze posso prendere lo sheet delle presenze
		String[] HEADERS = { "lecture_id", "student_id", "face_recognition_attendanes", "face_recognition_accuracy",
				"fingerprint_attendances", "fingerprint_confidance" };
		
		try {
			String file_location = "sheets/sheet_lecture" + Integer.toString(lectureId) + ".csv";
			FileWriter out = new FileWriter("src/main/resources/static/"+file_location);
			try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {
				
				for (Attendances attendances_line : lectureAttendances) {
					printer.printRecord(attendances_line.getLecture().getLectureId(),
							attendances_line.getStudent().getMatricola(),
							attendances_line.isFace_recognition_attendances(),
							attendances_line.getFace_recognition_accuracy(),
							attendances_line.isFingerprint_attendances(), attendances_line.getFingerprint_confidance());
				}
			}
			
			return file_location;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
		return null;

	}

}
