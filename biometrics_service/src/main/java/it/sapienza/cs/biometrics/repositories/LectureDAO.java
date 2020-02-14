package it.sapienza.cs.biometrics.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import it.sapienza.cs.biometrics.model.Lecture;

@Repository
public interface LectureDAO extends CrudRepository<Lecture, Integer> {

	Set<Lecture> findByCourseCode(Integer code);
	
	@Query(
			  value = "SELECT * FROM Lecture l WHERE l.course_code = ?1 and l.lectureEnd = true ORDER BY l.lectureId", 
			  nativeQuery = true)
	Set<Lecture> findByCourseAndTerminate(Integer code);
	
	@Modifying
	@Query (value = "UPDATE Lecture l SET l.lectureEnd = true WHERE l.lectureId = ?1")
	@Transactional
	void updateLectureEnd(Integer lectureId);
	
}