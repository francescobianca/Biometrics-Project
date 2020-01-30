package it.sapienza.cs.biometrics.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.sapienza.cs.biometrics.model.Lecture;

@Repository
public interface LectureDAO extends CrudRepository<Lecture, Integer> {

	Set<Lecture> findByCourseCode(Integer code);
	
}