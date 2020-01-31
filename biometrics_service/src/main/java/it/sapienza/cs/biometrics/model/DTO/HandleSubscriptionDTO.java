package it.sapienza.cs.biometrics.model.DTO;

public class HandleSubscriptionDTO {

	private String student; //matricola of the student

	private Integer courseId;

	public String getStudent() {
		return student;
	}

	public void setStudent(String student) {
		this.student = student;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

}