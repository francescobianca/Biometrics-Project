import face_recognition
import cv2
import numpy as np
import os
from imutils import paths
import pickle


# This is a demo of running face recognition on live video from your webcam. It's a little more complicated than the
# other example, but it includes some basic performance tweaks to make things run a lot faster:
#   1. Process each video frame at 1/4 resolution (though still display it at full resolution)
#   2. Only detect faces in every other frame of video.

# PLEASE NOTE: This example requires OpenCV (the `cv2` library) to be installed only to read from your webcam.
# OpenCV is *not* required to use the face_recognition library. It's only required if you want to run this
# specific demo. If you have trouble installing it, try any of the other demos that don't require it instead.

# Get a reference to webcam #0 (the default one)
video_capture = cv2.VideoCapture(0)

dataset = "dataset/"
directories = os.listdir(dataset)


knownEncodings = []
knownNames = []

for directory in directories: 
	# Load a sample picture and learn how to recognize it.
	imagePaths = list(paths.list_images(dataset+directory+"/"))
	# initialize the list of known encodings and known names
	
	# loop over the image paths
	for (i, imagePath) in enumerate(imagePaths):
	    # extract the person name from the image path
	    print("[INFO] processing image {}/{}".format(i + 1,
	        len(imagePaths)))
	    name = imagePath.split(os.path.sep)[-2]
	    # load the input image and convert it from BGR (OpenCV ordering)
	    # to dlib ordering (RGB)
	    image = cv2.imread(imagePath)
	    rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

	    # detect the (x, y)-coordinates of the bounding boxes
	    # corresponding to each face in the input image
	    boxes = face_recognition.face_locations(rgb,
	        model="cnn")
	    # compute the facial embedding for the face
	    encodings = face_recognition.face_encodings(rgb, boxes)
	    # loop over the encodings
	    for encoding in encodings:
	        # add each encoding + name to our set of known names and
	        # encodings
	        knownEncodings.append(encoding)
	        knownNames.append(name)

# dump the facial encodings + names to disk
print("[INFO] serializing encodings...")
data = {"encodings": knownEncodings, "names": knownNames}

f = open("encodings", "wb")
f.write(pickle.dumps(data))
f.close()