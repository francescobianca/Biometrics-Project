package it.sapienza.cs.biometrics.model.DTO;

import org.springframework.web.multipart.MultipartFile;

public class UserPictureUpdateDTO {

	private String matricola;
	private MultipartFile picture;

	public MultipartFile getPicture() {
		return picture;
	}

	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}

	public String getMatricola() {
		return matricola;
	}

	public void setMatricola(String matricola) {
		this.matricola = matricola;
	}

}