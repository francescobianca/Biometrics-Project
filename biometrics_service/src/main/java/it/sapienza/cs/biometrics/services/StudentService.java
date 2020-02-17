package it.sapienza.cs.biometrics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.sapienza.cs.biometrics.model.Student;
import it.sapienza.cs.biometrics.repositories.StudentDAO;

@Service
public class StudentService {
	
	@Autowired
	private StudentDAO studentDAO;
	
	public Student findByFingerprint(String fingerprint) {
		return studentDAO.findByFingerPrint(fingerprint);
	}

}
