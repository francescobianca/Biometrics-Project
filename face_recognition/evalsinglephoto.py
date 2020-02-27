import face_recognition
import pickle
import cv2
import numpy as np
import PySimpleGUI as sg
from keras import models
from keras.preprocessing import image
import csv
import os

class_label = {}
with open('hot-encoding.csv') as csvfile:
    readCSV = csv.reader(csvfile, delimiter=',')
    for row in readCSV:
        class_label[row[0]] = row[1]

model = models.load_model('faces_model.h5')

img = image.load_img('dataset/foto4.jpg', target_size = (120, 120)) 
img = image.img_to_array(img)
img = np.expand_dims(img, axis = 0)

model.compile(optimizer = 'adam', loss = 'binary_crossentropy', metrics = ['accuracy'])
pred = model.predict_classes(img)

print(class_label[str(pred[0])])