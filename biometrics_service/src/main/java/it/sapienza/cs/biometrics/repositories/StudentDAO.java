package it.sapienza.cs.biometrics.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.sapienza.cs.biometrics.model.Student;

@Repository
public interface StudentDAO extends CrudRepository<Student, String> {

}
