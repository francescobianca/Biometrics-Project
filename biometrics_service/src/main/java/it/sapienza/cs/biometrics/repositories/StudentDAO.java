package it.sapienza.cs.biometrics.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.sapienza.cs.biometrics.model.Course;
import it.sapienza.cs.biometrics.model.Student;

@Repository
public interface StudentDAO extends CrudRepository<Student, String> {
	
	Student findByFingerPrint (String fingerprint);

}
