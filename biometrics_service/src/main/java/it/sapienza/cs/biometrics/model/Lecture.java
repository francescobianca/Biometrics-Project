package it.sapienza.cs.biometrics.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Lecture")
public class Lecture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer lectureId;
	private String description;
	@Temporal(TemporalType.DATE)
	private Date date;

	@OneToMany(mappedBy = "lecture")
	@JsonIgnore
	Set<Attendances> attendances;

	@ManyToOne
	@JsonIgnore
	private Course course;
	
	private boolean lectureEnd;

	public Lecture() {

	}

	public Integer getLectureId() {
		return lectureId;
	}

	public void setLectureId(Integer lectureId) {
		this.lectureId = lectureId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Set<Attendances> getAttendances() {
		return attendances;
	}

	public void setAttendances(Set<Attendances> attendances) {
		this.attendances = attendances;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	public boolean isLectureEnd() {
		return lectureEnd;
	}
	
	public void setLectureEnd(boolean lectureEnd) {
		this.lectureEnd = lectureEnd;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Course))
			return false;

		Lecture lecture = (Lecture) obj;

		return lecture.lectureId.equals(lectureId);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + lectureId.hashCode();
		return result;
	}

}
