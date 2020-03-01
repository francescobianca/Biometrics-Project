import pickle
import sys
from operator import itemgetter
import numpy as np
import pandas as pd
from sklearn.pipeline import Pipeline
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis as LDA
from sklearn.preprocessing import LabelEncoder
from sklearn.svm import SVC
import face_recognition
import cv2
import PySimpleGUI as sg
import csv
from keras import models
from keras.preprocessing import image

with open('face_model', 'rb') as pickle_file:
    clf = pickle.load(pickle_file)

faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")


def startEvaluation():
    video_capture = cv2.VideoCapture(-1)

    process_this_frame = True
    attendances = {}

    while True:

        ret, frame = video_capture.read()

        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
        boxes = face_recognition.face_locations(rgb)
                
        encodings = face_recognition.face_encodings(rgb, boxes)
                
        for encoding in encodings:
            predictions = clf.predict_proba([encoding]).ravel()
            maxPred = np.argmax(predictions)
            confidence = predictions[maxPred]
            print(clf.predict([encoding])[0])
            print(confidence)

            if confidence > 0.90:
                name = clf.predict([encoding])[0]
                r = attendances.get(name, None)
                if r == None:
                    attendances[name] = 1
                elif attendances[name] > 0:
                    attendances[name] += 1

                
        cv2.imshow('Video', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    video_capture.release()
    cv2.destroyAllWindows()
    return attendances

attendancesReturn = startEvaluation()
print(attendancesReturn)