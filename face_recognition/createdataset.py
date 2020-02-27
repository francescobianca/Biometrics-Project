import cv2
import os
import PySimpleGUI as sg
import shutil
import time
import tensorflow as tf
import numpy as np
from skimage.util import random_noise
import matplotlib.image as mpimg
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Conv2D
from tensorflow.keras.layers import MaxPool2D
from tensorflow.keras.layers import Flatten
from tensorflow.keras.layers import Dense

faceCascade = cv2.CascadeClassifier("haarcascade_frontalface_default.xml")

def add_salt_pepper_noise(X_imgs):
    # Need to produce a copy as to not modify the original image
    X_imgs_copy = X_imgs.copy()
    salt_vs_pepper = 0.2
    amount = 0.004
    num_salt = np.ceil(amount * X_imgs_copy[0].size * salt_vs_pepper)
    num_pepper = np.ceil(amount * X_imgs_copy[0].size * (1.0 - salt_vs_pepper))
    
    for X_img in X_imgs_copy:
        # Add Salt noise
        coords = [np.random.randint(0, i - 1, int(num_salt)) for i in X_img.shape]
        X_img[coords[0], coords[1]] = 1

        # Add Pepper noise
        coords = [np.random.randint(0, i - 1, int(num_pepper)) for i in X_img.shape]
        X_img[coords[0], coords[1]] = 0
    return X_imgs_copy

def getDataset(face_id):
    cam = cv2.VideoCapture(-1)
    cam.set(3, 640) # set video width
    cam.set(4, 480) # set video height

    count = 0
    while count < 30:

        ret, img = cam.read()

        faces = faceCascade.detectMultiScale(
            img,
            scaleFactor=1.1,
            minNeighbors=5,
            minSize=(30, 30)
        )

        for (x, y, w, h) in faces:

            sg.OneLineProgressMeter('Acquisimento foto', count, 30, 'key')
            # Save the captured image into the datasets folder
            if not os.path.isdir("dataset/"+str(face_id)):
                os.mkdir("dataset/"+str(face_id))

            cv2.imwrite("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "true.jpg", img[y:y+h+50,x:x+w])

            noise = add_salt_pepper_noise(img[y:y+h+50,x:x+w])
            encN = tf.image.encode_jpeg(noise)
            fnameN = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "noise.jpg")
            fwriteN = tf.io.write_file(fnameN, encN)

            flip = tf.image.flip_left_right(img[y:y+h+50,x:x+w])
            encF = tf.image.encode_jpeg(flip)
            fnameF = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "flip.jpg")
            fwriteF = tf.io.write_file(fnameF, encF)

            central = tf.image.central_crop(img[y:y+h+50,x:x+w], central_fraction=0.8)
            encCE = tf.image.encode_jpeg(central)
            fnameCE = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "central.jpg")
            fwriteCE = tf.io.write_file(fnameCE, encCE)

            contrast = tf.image.adjust_contrast(img[y:y+h+50,x:x+w], contrast_factor=0.6)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)

            brightness = tf.image.adjust_brightness(img[y:y+h+50,x:x+w], delta=0.2)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)

            saturation = tf.image.adjust_saturation(img[y:y+h+50,x:x+w], 5)
            encS = tf.image.encode_jpeg(saturation)
            fnameS = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "saturation.jpg")
            fwriteS = tf.io.write_file(fnameS, encS)

            hue = tf.image.adjust_hue(img[y:y+h+50,x:x+w], delta=0.4)
            encH = tf.image.encode_jpeg(hue)
            fnameH = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "hue.jpg")
            fwriteH = tf.io.write_file(fnameH, encH)

            gray = tf.image.rgb_to_grayscale(img[y:y+h+50,x:x+w])
            encG = tf.image.encode_jpeg(gray)
            fnameG = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "gray.jpg")
            fwriteG = tf.io.write_file(fnameG, encG)

            cropup = tf.image.crop_to_bounding_box(img[y:y+h+50,x:x+w], 10, 10, 130, 130)
            encCRU = tf.image.encode_jpeg(cropup)
            fnameCRU = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "cropup.jpg")
            fwriteCRU = tf.io.write_file(fnameCRU, encCRU)

            cropdown = tf.image.crop_to_bounding_box(img[y:y+h+50,x:x+w], 40, 40, 120, 120)
            encCRD = tf.image.encode_jpeg(cropdown)
            fnameCRD = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "cropdown.jpg")
            fwriteCRD = tf.io.write_file(fnameCRD, encCRD)

            time.sleep(1)
            count += 1


    cam.release()
    cv2.destroyAllWindows()

    return True

layout = [[sg.Text('Benvenuto nella sezione di raccolta immagini del tuo volto.')],
           [sg.Text('Per iniziare, inserisci la tua matricola')],
                 [sg.InputText()],      
                 [sg.Button('Raccolta dati'), sg.Button('Annulla')]]      
window = sg.Window('Raccolta dati', layout)
event, values = window.read()
window.close()
face_id = values[0]
if event == 'Raccolta dati':
    ret = getDataset(face_id)
    if ret == True:
        layout = [[sg.Text('Complimenti matricola '+face_id+'! Operazione conclusa con successo.')],
                [sg.Button('Ok')]]
        window = sg.Window('Raccolta dati', layout)
        window.read()
        window.close()