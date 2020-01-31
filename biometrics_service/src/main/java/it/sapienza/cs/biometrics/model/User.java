package it.sapienza.cs.biometrics.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User {

	@Id
	private String matricola;
	private String password;
	private String firstName;
	private String lastName;
	private String profilePicture;
	
	public User() {
	
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof User))
			return false;

		User user = (User) obj;

		return user.matricola.equals(matricola);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + matricola.hashCode();
		return result;
	}

}
