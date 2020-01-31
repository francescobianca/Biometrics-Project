package it.sapienza.cs.biometrics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import it.sapienza.cs.biometrics.model.Student;
import it.sapienza.cs.biometrics.model.User;
import it.sapienza.cs.biometrics.repositories.StudentDAO;

@SpringBootTest
class BiometricsServiceApplicationTests {
	
	@Autowired
	private StudentDAO studentDAO;
	
	@Test
	public void Login() {
		/*Student u = new Student();
		u.setMatricola("123");
		u.setPassword("asd");
		u.setFirstName("Luca");
		u.setLastName("Rossi");
		studentDAO.save(u);
		u = studentDAO.findById("123").get();
		assertEquals("123",u.getMatricola());*/
		
		
		Student isStudent = studentDAO.findById("matricolaStudente1").get();
		assertEquals("matricolaStudente1",isStudent.getMatricola());
		
	}

}
