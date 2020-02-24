import face_recognition
import pickle
import cv2
import numpy as np
import PySimpleGUI as sg
from keras import models

def startEvaluation():
    #Load the saved model
    model = models.load_model('faces_model.h5')
    video_capture = cv2.VideoCapture(0)

    print("[INFO] loading encodings...")
    data = pickle.loads(open("encodings", "rb").read())

    face_locations = []
    face_encodings = []
    face_names = []
    process_this_frame = True
    attendances = {}

    while True:

        ret, frame = video_capture.read()

        small_frame = cv2.resize(frame, (0, 0), fx=0.25, fy=0.25)

        rgb_small_frame = small_frame[:, :, ::-1]

        if process_this_frame:

            face_locations = face_recognition.face_locations(rgb_small_frame)
            face_encodings = face_recognition.face_encodings(rgb_small_frame, face_locations)

            face_names = []
            for face_encoding in face_encodings:

                #matches = face_recognition.compare_faces(data["encodings"], face_encoding, tolerance=0.6)

                name = "Unknown"
                pred = model.predict(face_encoding)
                print(pred)
                #face_distances = face_recognition.face_distance(data["encodings"], face_encoding)
                #best_match_index = np.argmin(face_distances)
                #confidence = face_distances[best_match_index]
                '''
                if matches[best_match_index]:
                    name = data["names"][best_match_index]
                    print(name)
                    r = attendances.get(name, None)
                    if r == None:
                        attendances[name] = confidence
                    elif attendances[name] > confidence: 
                        attendances[name] = confidence
                    print(attendances[name])

                face_names.append(name)
		'''
        '''      
        process_this_frame = not process_this_frame

        for (top, right, bottom, left), name in zip(face_locations, face_names):
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
	'''
    '''	
    video_capture.release()
    cv2.destroyAllWindows()
    return attendances
    '''
