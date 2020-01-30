package it.sapienza.cs.biometrics.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import it.sapienza.cs.biometrics.model.Professor;

@Repository
public interface ProfessorDAO extends CrudRepository<Professor, String>{

}
