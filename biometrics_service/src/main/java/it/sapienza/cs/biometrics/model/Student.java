package it.sapienza.cs.biometrics.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Student")
public class Student extends User {
	
	private String fingerPrint;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "subscriptions", joinColumns = { @JoinColumn(name = "matricola") }, inverseJoinColumns = {
			@JoinColumn(name = "code") })
	private Set<Course> followingCourses;

	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "attendances", joinColumns = { @JoinColumn(name = "matricola") }, inverseJoinColumns = {
			@JoinColumn(name = "lectureId") })
	@JsonIgnore
	private Set<Lecture> attendedLectures;*/
	
	@OneToMany(mappedBy = "student")
	@JsonIgnore
    Set<Attendances> attendances;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.ALL)
	private Set<StudentImage> images;
	
	public Student() {
	
	}
	
	public String getFingerPrint() {
		return fingerPrint;
	}
	
	public void setFingerPrint(String fingerPrint) {
		this.fingerPrint = fingerPrint;
	}
	
	public Set<Course> getFollowingCourses() {
		return followingCourses;
	}

	public void setFollowingCourses(Set<Course> followingCourses) {
		this.followingCourses = followingCourses;
	}

	public Set<Attendances> getAttendances() {
		return attendances;
	}
	
	public void setAttendances(Set<Attendances> attendances) {
		this.attendances = attendances;
	}
	
	public Set<StudentImage> getImages() {
		return images;
	}
	
	public void setImages(Set<StudentImage> images) {
		this.images = images;
	}

}
