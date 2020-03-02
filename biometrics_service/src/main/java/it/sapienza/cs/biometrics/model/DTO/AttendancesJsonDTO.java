package it.sapienza.cs.biometrics.model.DTO;

public class AttendancesJsonDTO {
	
	private String matricola;
	private int count;
	private float avg_accuracy;
	private float accuracy;
	
	public AttendancesJsonDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public AttendancesJsonDTO(String matricola, int count, float avg_accuracy, float accuracy) {
		this.matricola = matricola;
		this.count = count;
		this.avg_accuracy = avg_accuracy;
		this.accuracy = accuracy;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getAvg_accuracy() {
		return avg_accuracy;
	}

	public void setAvg_accuracy(float avg_accuracy) {
		this.avg_accuracy = avg_accuracy;
	}

	public float getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(float accuracy) {
		this.accuracy = accuracy;
	}

}
