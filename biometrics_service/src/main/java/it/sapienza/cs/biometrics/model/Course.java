package it.sapienza.cs.biometrics.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "Course")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer code;
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "followingCourses")
	@JsonIgnore
	private Set<Student> students;
	
	@OneToMany(mappedBy = "course")
	@JsonIgnore
	private Set<Lecture> lectures;
	
	@ManyToOne
	@JsonIgnore
	private Professor professor;
	
	private boolean registrationClosed;

	public Course() {

	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Set<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(Set<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	public boolean isRegistrationClosed() {
		return registrationClosed;
	}
	
	public void setRegistrationClosed(boolean registrationClosed) {
		this.registrationClosed = registrationClosed;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Course))
			return false;

		Course course = (Course) obj;

		return course.code.equals(code);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + code.hashCode();
		return result;
	}

}
