package it.sapienza.cs.biometrics.repositories;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;

import it.sapienza.cs.biometrics.model.Attendances;
import it.sapienza.cs.biometrics.model.AttendancesKey;

public interface AttendanceDAO extends CrudRepository<Attendances, AttendancesKey>{
	
	Set<Attendances> findByLectureLectureId(Integer lectureId);
	
}
