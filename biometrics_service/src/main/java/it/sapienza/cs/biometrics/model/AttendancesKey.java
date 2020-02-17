package it.sapienza.cs.biometrics.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AttendancesKey implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "student_id")
	private String student_matricola;
	@Column(name = "lecture_id")
	private Integer lecture_lectureId;
	
	public AttendancesKey() {
	
	}
	
	public AttendancesKey(String student_matricola, Integer lecture_lectureId) {
		this.student_matricola = student_matricola;
		this.lecture_lectureId = lecture_lectureId;
	}
	
	public String getStudent_matricola() {
		return student_matricola;
	}
	public void setStudent_matricola(String student_matricola) {
		this.student_matricola = student_matricola;
	}
	public Integer getLecture_lectureId() {
		return lecture_lectureId;
	}
	public void setLecture_lectureId(Integer lecture_lectureId) {
		this.lecture_lectureId = lecture_lectureId;
	}
	
	
}
