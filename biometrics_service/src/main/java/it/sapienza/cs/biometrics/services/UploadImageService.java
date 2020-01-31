package it.sapienza.cs.biometrics.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import it.sapienza.cs.biometrics.model.User;
import it.sapienza.cs.biometrics.repositories.UserDAO;

@Service
public class UploadImageService {

	// Save the uploaded file to this folder
	private static String FOLDER = "uploads/profilePictures/";

	@Autowired
	private UserDAO userDAO;

	public String updateProfilePicture(MultipartFile file) {
		try {
			String fileName = file.getOriginalFilename();
			if (!isImage(file))
				return "Wrong extension";
			byte[] bytes = file.getBytes();
			Path path = Paths.get(FOLDER + fileName);
			File currFile = new File(FOLDER + fileName);
			currFile.delete();
			Files.write(path, bytes);

			System.out.println(fileName);

			String[] splitOnDot = fileName.split("\\.");

			String extension = splitOnDot[splitOnDot.length - 1];
			String matricola = fileName.replace("." + extension, "");

			System.out.println(matricola);

			User user = userDAO.findById(matricola).get();
			user.setProfilePicture(FOLDER + fileName);
			userDAO.save(user);
			return "uploads/profilePictures/" + fileName;
		} catch (IOException e) {
			e.printStackTrace();
			return "Failed";
		}
	}

	// Check the extension of the file.
	private boolean isImage(MultipartFile file) {
		String extension = com.google.common.io.Files.getFileExtension(file.getOriginalFilename());
		return ((extension.equals("jpeg") || extension.equals("png") || extension.equals("jpg")
				|| extension.equals("bmp")));
	}

}