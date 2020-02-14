import face_recognition
import cv2
import numpy as np
import os
from imutils import paths
import pickle

def saveModel():

	dataset = "dataset/"
	directories = os.listdir(dataset)


	knownEncodings = []
	knownNames = []

	for directory in directories: 
		
		imagePaths = list(paths.list_images(dataset+directory+"/"))

		for (i, imagePath) in enumerate(imagePaths):
		    
		    print("[INFO] processing image {}/{}".format(i + 1,
		        len(imagePaths)))
		    name = imagePath.split(os.path.sep)[-2]
		    
		    image = cv2.imread(imagePath)
		    rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

		    boxes = face_recognition.face_locations(rgb,
		        model="cnn")
		    
		    encodings = face_recognition.face_encodings(rgb, boxes)
		    
		    for encoding in encodings:
		     
		        knownEncodings.append(encoding)
		        knownNames.append(name)

	print("[INFO] serializing encodings...")
	data = {"encodings": knownEncodings, "names": knownNames}

	f = open("encodings", "wb")
	f.write(pickle.dumps(data))
	f.close()