import face_recognition
import pickle
import cv2
import numpy as np
import PySimpleGUI as sg
import csv
from keras import models
from keras.preprocessing import image

class_label = {}
with open('hot-encoding.csv') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    for row in readCSV:
        class_label[row[0]] = row[1]

model = models.load_model('faces_model.h5')

def startEvaluation():
    video_capture = cv2.VideoCapture(-1)

    face_locations = []
    process_this_frame = True
    attendances = {}

    while True:

        ret, frame = video_capture.read()

        small_frame = cv2.resize(frame, (120, 120), fx=0.25, fy=0.25)

        rgb_small_frame = small_frame[:, :, ::-1]

        name = ""

        if process_this_frame:

            face_locations = face_recognition.face_locations(rgb_small_frame)

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
                
        process_this_frame = not process_this_frame
        print(name)
        for top, right, bottom, left in face_locations:
            top *= 4
            right *= 4
            bottom *= 4
            left *= 4

            cv2.rectangle(frame, (left, top), (right, bottom), (0, 0, 255), 2)

            cv2.rectangle(frame, (left, bottom - 35), (right, bottom), (0, 0, 255), cv2.FILLED)
            font = cv2.FONT_HERSHEY_DUPLEX
            cv2.putText(frame, name, (left + 6, bottom - 6), font, 1.0, (255, 255, 255), 1)

        cv2.imshow('Video', frame)

        if cv2.waitKey(1) & 0xFF == ord('q'):
            break

    video_capture.release()
    cv2.destroyAllWindows()
    return attendances

attendancesReturn = startEvaluation()
print(attendancesReturn)