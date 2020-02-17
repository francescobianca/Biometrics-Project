package it.sapienza.cs.biometrics.model;

import java.util.Optional;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class Attendances {
	
	public Attendances() {
	
	}
	
	@EmbeddedId
	private AttendancesKey id;
	
	@ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    private Student student;
 
    @ManyToOne
    @MapsId("lecture_id")
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    
    private boolean face_recognition_attendances;
    
    private float face_recognition_accuracy;
    
    private boolean fingerprint_attendances;
    
	private float fingerprint_confidance;

	public AttendancesKey getId() {
		return id;
	}

	public void setId(AttendancesKey id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Lecture getLecture() {
		return lecture;
	}

	public void setLecture(Lecture l) {
		this.lecture = l;
	}

	public boolean isFace_recognition_attendances() {
		return face_recognition_attendances;
	}

	public void setFace_recognition_attendances(boolean face_recognition_attendances) {
		this.face_recognition_attendances = face_recognition_attendances;
	}

	public float getFace_recognition_accuracy() {
		return face_recognition_accuracy;
	}

	public void setFace_recognition_accuracy(float face_recognition_accuracy) {
		this.face_recognition_accuracy = face_recognition_accuracy;
	}

	public boolean isFingerprint_attendances() {
		return fingerprint_attendances;
	}

	public void setFingerprint_attendances(boolean fingerprint_attendances) {
		this.fingerprint_attendances = fingerprint_attendances;
	}

	public float getFingerprint_confidance() {
		return fingerprint_confidance;
	}

	public void setFingerprint_confidance(float fingerprint_confidance) {
		this.fingerprint_confidance = fingerprint_confidance;
	}

}
