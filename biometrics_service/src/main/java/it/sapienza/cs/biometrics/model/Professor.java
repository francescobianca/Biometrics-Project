package it.sapienza.cs.biometrics.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Professor")
public class Professor extends User{
	
	@OneToMany(mappedBy = "professor")
	private Set<Course> teachedCourses;
	
	public Professor() {
	
	}
	
	public Set<Course> getTeachedCourses() {
		return teachedCourses;
	}

	public void setTeachedCourses(Set<Course> teachedCourses) {
		this.teachedCourses = teachedCourses;
	}

}
