package it.sapienza.cs.biometrics.model.DTO;

public class UserLoginDTO {

	private String matricola;
	private String password;

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}