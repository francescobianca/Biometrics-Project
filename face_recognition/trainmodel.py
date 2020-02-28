 # CODE OF PART 1 - "Load the Dataset"
import os
import cv2
import csv
import keras
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Dense, Flatten, Dropout
from keras.optimizers import Adam
from keras.callbacks import TensorBoard

import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split

from sklearn.metrics import confusion_matrix
from sklearn.metrics import classification_report
from sklearn.metrics import roc_curve, auc
from sklearn.metrics import accuracy_score
from keras.utils import np_utils
import itertools

def loadPictures(path):
  for _,dirs,_ in os.walk(path):
    X=[];
    y=[];
    for dir in dirs:
        for _,_,files in os.walk(path+dir+"/"):
            for file in files:
                if file.find('.jpg')<0:
                       continue     
                #print("Loading:"+path+dir+"/"+file)
                
                img = cv2.imread(path+dir+"/"+file)
                img_resize = cv2.resize(img, (200, 200)) 
                X.append(img_resize)
                y.append(dir)
    return X,y
                
train_folder = 'dataset/train/'
X_train,y_train=loadPictures(train_folder)

validation_and_test_folder = 'dataset/validationandtest/'
X_validationandtest,y_validationandtest = loadPictures(validation_and_test_folder)

from sklearn.model_selection import train_test_split

X_test,X_validate,y_test,y_validate = train_test_split(X_validationandtest,y_validationandtest,test_size=0.2,random_state=42)

X_train = np.asarray(X_train)
X_validate = np.asarray(X_validate)
X_test = np.asarray(X_test)

num_dir = len(np.unique(y_train)) # un check veloce per vedere quante sono le classi

#Reshape for CNN
X_train = X_train.reshape(-1, 200, 200, 3)   
X_validateReshape = X_validate.reshape(-1, 200, 200, 3)
X_test = X_test.reshape(-1, 200, 200, 3)

# Dataset preprocessing
from sklearn.preprocessing import LabelEncoder

encoder = LabelEncoder()

encoder.fit(y_train)
encoded_y_train = encoder.transform(y_train)

labels = encoder.classes_
with open('hot-encoding.csv', 'w', newline='') as file:
    for index, label in zip(range(len(labels)), labels):
        writer = csv.writer(file)
        writer.writerow([index, label])

encoder.fit(y_validate)
encoded_y_validate = encoder.transform(y_validate)

encoder.fit(y_test)
encoded_y_test = encoder.transform(y_test)

from keras.utils.np_utils import to_categorical

encoded_y_train = to_categorical(encoded_y_train, num_classes = num_dir)
encoded_y_validateCategorical = to_categorical(encoded_y_validate, num_classes = num_dir)
encoded_y_testCategorical = to_categorical(encoded_y_test, num_classes = num_dir)

print(encoded_y_train.shape)

# Building model
from keras import layers
from keras import models

cnn_model= Sequential([
    Conv2D(filters=36, kernel_size=7, activation='relu', input_shape= (200, 200, 3)),
    MaxPooling2D(pool_size=2),
    Conv2D(filters=54, kernel_size=5, activation='relu', input_shape= (200, 200, 3)),
    MaxPooling2D(pool_size=2),
    Flatten(),
    Dense(2024, activation='relu'),
     Dropout(0.5),
    Dense(1024, activation='relu'),
    Dropout(0.5),
    Dense(512, activation='relu'),
    Dropout(0.5),
    #num_dir is the number of outputs
    Dense(num_dir, activation='softmax')  
])

from keras import optimizers

cnn_model.compile(loss='categorical_crossentropy', optimizer=optimizers.Adam(lr=2e-4),metrics=['accuracy'])

cnn_model.summary()

history=cnn_model.fit(
    np.array(X_train), np.array(encoded_y_train), batch_size=512,
    epochs=30, verbose=2,
    validation_data=(np.array(X_validateReshape),np.array(encoded_y_validateCategorical)),
)

cnn_model.save('faces_model.h5')

from keras.models import load_model 
from sklearn.metrics import classification_report

predictions = cnn_model.predict(X_test)
predicted_classes = np.argmax(predictions,axis=1)

print("Detailed classification report:")
print()
print("The model is trained on the full development set.")
print("The scores are computed on the full evaluation set.")
print()
print(classification_report(encoded_y_test, predicted_classes))
print()
