 # CODE OF PART 1 - "Load the Dataset"
import os
import cv2
import csv
import numpy as np

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
                img_resize = cv2.resize(img, (150, 150)) 
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
X_train = X_train.reshape(-1,150, 150, 3)   
X_validateReshape = X_validate.reshape(-1,150, 150, 3)
X_test = X_test.reshape(-1,150, 150, 3)

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

model = models.Sequential()
model.add(layers.Conv2D(32, (3,3), activation='relu', input_shape=(150,150,3)))
model.add(layers.MaxPooling2D((2,2)))
model.add(layers.Conv2D(64, (3,3), activation='relu'))
model.add(layers.MaxPooling2D((2,2)))
model.add(layers.Conv2D(128, (3,3), activation='relu'))
model.add(layers.MaxPooling2D((2,2)))
model.add(layers.Conv2D(128, (3,3), activation='relu'))
model.add(layers.MaxPooling2D((2,2)))
model.add(layers.Flatten())
model.add(layers.Dense(512, activation='relu'))
model.add(layers.Dense(num_dir, activation='softmax'))

print(model.summary())

from keras import optimizers

model.compile(loss='categorical_crossentropy', optimizer=optimizers.Adam(lr=2e-4),metrics=['accuracy'])

# Questa configurazione va meglio utilizzando anche l'optimizers.Adam
history = model.fit(X_train, encoded_y_train,
                        epochs=3, 
                        batch_size = 32,
                        validation_data=(X_validateReshape, encoded_y_validateCategorical), 
                        verbose=1)

model.save('faces_model.h5')

from keras.models import load_model 
from sklearn.metrics import classification_report

#model = load_model('lego_model.h5')

predictions = model.predict(X_test)
predicted_classes = np.argmax(predictions,axis=1)

print("Detailed classification report:")
print()
print("The model is trained on the full development set.")
print("The scores are computed on the full evaluation set.")
print()
print(classification_report(encoded_y_test, predicted_classes))
print()





