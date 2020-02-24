import face_recognition
import pickle
import cv2
import numpy as np
import PySimpleGUI as sg
from keras import models
from keras.preprocessing import image
import csv

class_labels = {}
with open('hot-encoding.csv', mode='r') as infile:
    reader = csv.reader(infile)
    for rows in reader:
        class_labels[rows[0]] = rows[1]

def startEvaluation():
    cam = cv2.VideoCapture(0)
    #Load the saved model
    model = models.load_model('faces_model.h5')
    video_capture = cv2.VideoCapture(0)

    attendances = {}

    while True:
        ret, img = cam.read()
        cv2.imshow('Video', img)
        small_img = cv2.resize(img, (0, 0), fx=0.25, fy=0.25)
        rgb_small_img = small_img[:, :, ::-1]

        pred = model.predict_classes(img)
        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    video_capture.release()
    cv2.destroyAllWindows()
    print(attendances)
    return attendances

startEvaluation()

'''
pred = model.predict_classes(img.reshape(1, 150, 150, 3))
r = attendances.get(class_labels[str(pred[0])], None)
if r == None:
    attendances[class_labels[str(pred[0])]] = 1
else:
    attendances[class_labels[str(pred[0])]] += 1
'''