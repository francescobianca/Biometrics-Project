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

def startEvaluation():
    with open('face_model', 'rb') as pickle_file:
        clf = pickle.load(pickle_file)

    faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")


    #video_capture = cv2.VideoCapture(-1)
    video_capture = cv2.VideoCapture("/dev/v4l/by-id/usb-Microsoft_Microsoft®_LifeCam_HD-3000-video-index0")
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

            if confidence > 0.85:
                name = clf.predict([encoding])[0]
                r = attendances.get(name, None)
                if r == None:
                    attendances[name] = [1, confidence, confidence]
                elif attendances[name][0] > 0:
                    attendances[name][0] += 1
                    attendances[name][1] += confidence
                    if attendances[name][2] < confidence:
                        attendances[name][2] = confidence
                
        cv2.imshow('Video', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    video_capture.release()
    cv2.destroyAllWindows()

    return attendances