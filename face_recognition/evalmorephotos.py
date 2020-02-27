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

conta = {}

def findclass(path):
	model = models.load_model('faces_model.h5')
	for _,_,files in os.walk(path):
		for file in files:
			img = image.load_img(path+file, target_size = (120, 120)) 
			img = image.img_to_array(img)
			img = np.expand_dims(img, axis = 0)
			model.compile(optimizer = 'adam', loss = 'binary_crossentropy', metrics = ['accuracy'])
			pred = model.predict_classes(img)
			r = conta.get(class_label[str(pred[0])], None)
			if r == None:
				conta[class_label[str(pred[0])]] = 1
			elif conta[class_label[str(pred[0])]] > 0: 
				conta[class_label[str(pred[0])]] += 1  
                
train_folder = 'dataset/fotoprova/'
findclass(train_folder)
print(conta)
