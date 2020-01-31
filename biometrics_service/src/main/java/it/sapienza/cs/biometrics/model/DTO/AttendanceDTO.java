package it.sapienza.cs.biometrics.model.DTO;

public class AttendanceDTO {

	private Integer lectureId;
	private String matricola;

	public Integer getLectureId() {
		return lectureId;
	}

	public void setLectureId(Integer lectureId) {
		this.lectureId = lectureId;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

}