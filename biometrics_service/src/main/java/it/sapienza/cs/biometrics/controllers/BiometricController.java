package it.sapienza.cs.biometrics.controllers;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.json.JSONArray;
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
import it.sapienza.cs.biometrics.services.StudentService;
import it.sapienza.cs.biometrics.services.UploadImageService;
import it.sapienza.cs.biometrics.model.Attendances;
import it.sapienza.cs.biometrics.model.AttendancesKey;
import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.Lecture;
import it.sapienza.cs.biometrics.model.Student;
import it.sapienza.cs.biometrics.model.User;
import it.sapienza.cs.biometrics.model.DTO.AttendancesJsonDTO;
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
	private StudentService studentService;

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

	@PostMapping("/faceRecognitionOutput")
	public void faceRecognitionOutput(@RequestBody ArrayList<AttendancesJsonDTO> attendances,
			@RequestParam Integer lectureId, @RequestParam String courseCode) {

		System.out.println("Numero di presenze face:" + attendances.size());
		System.out.println("LectureID: " + lectureId);
		System.out.println("CourseID: " + courseCode);

		// Mi arriva una lista composta da oggetti matricola, count, avg e accuracy
		for (AttendancesJsonDTO item : attendances) {
			Optional<Student> s = studentService.findById(item.getMatricola());
			if (s.isPresent()) {
				Student exists = s.get();

				// Va fatto il controllo sul corso
				Set<Course> followedCourse = exists.getFollowingCourses();
				boolean seguoCorso = false;
				for (Course c : followedCourse) {
					if (c.getCode() == Integer.parseInt(courseCode)) {
						// Vuol dire che il seguente studente segue il corso.
						seguoCorso = true;
						AttendancesKey attendancesKey = new AttendancesKey(exists.getMatricola(), lectureId);
						Attendances newAttendences = new Attendances();
						newAttendences.setId(attendancesKey);
						newAttendences.setFace_recognition_attendances(true);
						newAttendences.setCount(item.getCount());
						newAttendences.setFace_recognition_accuracy(item.getAccuracy());
						newAttendences.setAvg_accuracy(item.getAvg_accuracy());
						attendanceService.createAttendences(newAttendences);
						break;
					}
				}
			}

		}

	}

	@GetMapping("/startFingerprintRecognition")
	public void startFingerprintRecognition() {
		System.out.println("Start fingerprint recognition");

		// Inizio comunicazione con arduino
		BufferedReader in = null;
		PrintStream out = null;
		Socket socket = null;

		try {

			System.out.println("OK sto provando ad effettuare una comunicazione");

			socket = new Socket("127.0.0.1", 4000);

			// Apre i canali di I/O
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);

			out.println("a");
			out.flush();

			out.close();
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@GetMapping("/endFingerprintRecognition")
	public void startFingerprintRecognition(@RequestParam Integer lectureId, @RequestParam String courseCode) {
		System.out.println("End fingerprint recognition");

		// Qua va inserito il codice per comunicare con arduino e prendere le presenze
		BufferedReader in = null;
		PrintStream out = null;
		Socket socket = null;

		try {
			socket = new Socket("127.0.0.1", 4000);

			// Apre i canali di I/O
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintStream(socket.getOutputStream(), true);

			// String s = in.readLine();

			out.println("Presenze");
			out.flush();

			String attendences = in.readLine();
			attendences = method(attendences);
			System.out.println("Attendences: " + attendences);

			HashMap<String, String> fingerprint = new HashMap<>();

			String[] split = attendences.split(";");

			for (String a : split) {
				System.out.println(a);
				String[] secondSplit = a.split(":");

				String value = fingerprint.get(secondSplit[0]);
				if (value != null) {
					int valueInt = Integer.valueOf(value);
					if (Integer.valueOf(secondSplit[1]) > valueInt) {
						// Il nuovo valore è maggiore
						String newValue = secondSplit[1];
						fingerprint.put(secondSplit[0], newValue);
					}
				} else
					fingerprint.put(secondSplit[0], secondSplit[1]);
			}

			for (Entry<String, String> entry : fingerprint.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				System.out.println("ID: " + key + " Value: " + value);

				// Inserimento nel db: prima mi prendo l'user collegato a quell'id.
				Student s = studentService.findByFingerprint(key);
				// System.out.println(s.getLastName());
				// Vedo se sono iscritto al corso
				Set<Course> followedCourse = s.getFollowingCourses();
				boolean seguoCorso = false;
				for (Course c : followedCourse) {
					if (c.getCode() == Integer.parseInt(courseCode)) {
						// Vuol dire che il seguente studente segue il corso.
						seguoCorso = true;
						break;
					}
				}
				if (!seguoCorso)
					fingerprint.remove(key);

			}

			// Entry rimaste --> Sono gli studenti iscritti al corso. Le impronte fasulle
			// sono eliminate.
			for (Entry<String, String> entry : fingerprint.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				System.out.println("ID_2: " + key + " Value_2: " + value);

				// A questo punto devo andare a mettere le presenze. Ho lectureId
				Student s = studentService.findByFingerprint(key);
				AttendancesKey attendancesKey = new AttendancesKey(s.getMatricola(), lectureId);
				// attendancesKey.setLecture_lectureId(lectureId);
				// attendancesKey.setStudent_matricola(s.getMatricola());
				Optional<Attendances> a = attendanceService.findById(attendancesKey);
				if (!a.isPresent()) {
					// Qua ancora non è stata presa alcuna presenze
					Attendances newAttendences = new Attendances();
					// Ho solo i dati relativi al fingerprint
					newAttendences.setId(new AttendancesKey(s.getMatricola(), lectureId));
					newAttendences.setStudent(s);
					Lecture l = courseService.findLectureById(lectureId);
					newAttendences.setLecture(l);
					newAttendences.setFingerprint_attendances(true);

					// Bisogna normalizzare la confidance che viene data nel range 0 - 255
					Float confidance = Float.valueOf(value);
					confidance = confidance / 255.0f;

					newAttendences.setFingerprint_confidance(confidance);
					attendanceService.createAttendences(newAttendences);
				} else {
					// Qua c'è già un'istanza della presenza
					a.get().setFingerprint_attendances(true);

					// Bisogna normalizzare la confidance che viene data nel range 0 - 255
					Float confidance = Float.valueOf(value);
					confidance = confidance / 255.0f;

					a.get().setFingerprint_confidance(confidance);
					attendanceService.createAttendences(a.get());
				}

			}

			out.close();
			in.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@GetMapping("/closeLecture")
	public Set<Lecture> closeLecture(@RequestParam Integer lectureId, @RequestParam String courseCode) {
		return courseService.closeLecture(lectureId, courseCode);
	}

	public String method(String str) {
		if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ';') {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	@GetMapping("/getSheet")
	public String downloadSheet(@RequestParam Integer lectureId) {
		System.out.println(lectureId);
		// Ho un lectureId : mi servono le presenze per quella lectureId
		Set<Attendances> lectureAttendances = attendanceService.getLectureAttendances(lectureId);
		// Adesso tramite il set delle presenze posso prendere lo sheet delle presenze
		String[] HEADERS = { "lecture_id", "student_id", "face_recognition_attendanes", "face_recognition_count",
				"face_recognition_accuracy", "face_recognition_avg_accuracy", "fingerprint_attendances",
				"fingerprint_confidance" };

		try {
			String file_location = "sheets/sheet_lecture" + Integer.toString(lectureId) + ".csv";
			FileWriter out = new FileWriter("src/main/resources/static/" + file_location);
			try (CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(HEADERS))) {

				for (Attendances attendances_line : lectureAttendances) {
					printer.printRecord(attendances_line.getLecture().getLectureId(),
							attendances_line.getStudent().getMatricola(),
							attendances_line.isFace_recognition_attendances(), attendances_line.getCount(),
							attendances_line.getFace_recognition_accuracy(), attendances_line.getAvg_accuracy(),
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
