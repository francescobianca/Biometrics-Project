package it.sapienza.cs.biometrics.model.DTO;

import java.util.Date;

public class LectureDTO {

	private Integer course;
	private String description;
	private Date date;

	public Integer getCourse() {
		return course;
	}

	public void setCourse(Integer course) {
		this.course = course;
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

}