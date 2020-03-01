from imutils import paths
import face_recognition
import argparse
import pickle
import cv2
import os

imagePath = "dataset/"

knownEncodings = []
knownNames = []

for _,dirs,_ in os.walk(imagePath):
    X=[];
    y=[];
    for dir in dirs:
        for _,_,files in os.walk(imagePath+dir+"/"):
            count = 0
            for file in files:
                print("[INFO] processing image {}/{}".format(count + 1,len(files)))
                name = dir
                image = cv2.imread(imagePath+dir+"/"+file)
                rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
                boxes = face_recognition.face_locations(rgb)
                
                encodings = face_recognition.face_encodings(rgb, boxes)
                
                for encoding in encodings:
                    knownEncodings.append(encoding)
                    knownNames.append(name)
                count += 1

print("Stiamo generando il tuo file di encodings..")
data = {"encodings": knownEncodings, "names": knownNames}
f = open("face_encodings", "wb")
f.write(pickle.dumps(data))
f.close()