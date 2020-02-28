import face_recognition
import pickle
import cv2
import numpy as np
import PySimpleGUI as sg
import csv
from keras import models
from keras.preprocessing import image

faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")

class_label = {}
with open('hot-encoding.csv') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    for row in readCSV:
        class_label[row[0]] = row[1]

model = models.load_model('faces_model.h5')

def startEvaluation():
    video_capture = cv2.VideoCapture(-1)

    process_this_frame = True
    attendances = {}

    while True:

        ret, frame = video_capture.read()

        faces = faceCascade.detectMultiScale(
            frame,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(30, 30)
        )

        name = ""

        # Draw a rectangle around the faces
        for (x, y, w, h) in faces:

            if process_this_frame:
                cv2.rectangle(frame, (x, y), (x+w, y+h), (0, 255, 0), 2)
                small_frame = cv2.resize(frame[y:y+h,x:x+w], (120, 120), fx=0.25, fy=0.25)
                img = np.expand_dims(small_frame, axis = 0)
                model.compile(optimizer = 'adam', loss = 'binary_crossentropy', metrics = ['accuracy'])
                pred = model.predict_classes(img)
                pred_array = model.predict(img)
                if pred_array[0][pred[0]] > 0.60:
                    name = class_label[str(pred[0])]
                    r = attendances.get(class_label[str(pred[0])], None)
                    if r == None:
                       attendances[class_label[str(pred[0])]] = 1
                    elif attendances[class_label[str(pred[0])]] > 0:
                        attendances[class_label[str(pred[0])]] += 1
                    font = cv2.FONT_HERSHEY_DUPLEX
                    cv2.putText(frame, name, (h + 6, w - 6), font, 1.0, (255, 255, 255), 1)
                    print(pred_array)
                    print(name)

                
            process_this_frame = not process_this_frame

        cv2.imshow('Video', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    video_capture.release()
    cv2.destroyAllWindows()
    return attendances

attendancesReturn = startEvaluation()
print(attendancesReturn)