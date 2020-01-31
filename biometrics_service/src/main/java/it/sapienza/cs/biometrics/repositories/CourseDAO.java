package it.sapienza.cs.biometrics.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.sapienza.cs.biometrics.model.Course;

@Repository
public interface CourseDAO extends CrudRepository<Course, Integer> {

	Set<Course> findByProfessorMatricola(String matricola);

}
