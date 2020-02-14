package it.sapienza.cs.biometrics.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import it.sapienza.cs.biometrics.model.Course;

@Repository
public interface CourseDAO extends CrudRepository<Course, Integer> {

	Set<Course> findByProfessorMatricola(String matricola);
	
	@Query(
			  value = "SELECT * FROM Course c WHERE c.registrationClosed=false ORDER BY c.code", 
			  nativeQuery = true)
	Set<Course> findByOpenBooking();

}
