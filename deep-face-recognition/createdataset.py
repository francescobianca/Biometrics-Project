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
from PIL import Image
from PIL import ImageEnhance

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
    cam = cv2.VideoCapture("/dev/v4l/by-id/usb-Microsoft_Microsoft®_LifeCam_HD-3000-video-index0")
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

            #salvataggio foto facce senza +30
            cv2.imwrite("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "true30.jpg", img[y:y+h,x:x+w])
            #salvataggio foto face con +30
            cv2.imwrite("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "true.jpg", img[y:y+h+30,x:x+w])

            im = tf.io.read_file("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "true.jpg")
            im = tf.image.decode_png(im, 3)

            im30 = tf.io.read_file("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "true30.jpg")
            im30 = tf.image.decode_png(im30, 3)
       
           
            #noise = add_salt_pepper_noise(img[y:y+h,x:x+w])
            #encN = tf.image.encode_jpeg(noise)
            #fnameN = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "noise50.jpg")
            #fwriteN = tf.io.write_file(fnameN, encN)

            # flip orizzontale della foto
            flip = tf.image.flip_left_right(im)
            encF = tf.image.encode_jpeg(flip)
            fnameF = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "flip.jpg")
            fwriteF = tf.io.write_file(fnameF, encF)
            flip = tf.image.flip_left_right(im30)
            encF = tf.image.encode_jpeg(flip)
            fnameF = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "flip.jpg")
            fwriteF = tf.io.write_file(fnameF, encF)

            # centrare la foto
            central = tf.image.central_crop(im, central_fraction=0.8)
            encCE = tf.image.encode_jpeg(central)
            fnameCE = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "central.jpg")
            fwriteCE = tf.io.write_file(fnameCE, encCE)
            central = tf.image.central_crop(im30, central_fraction=0.8)
            encCE = tf.image.encode_jpeg(central)
            fnameCE = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "central30.jpg")
            fwriteCE = tf.io.write_file(fnameCE, encCE)

            # aumentare contrasto foto di 0.9
            contrast = tf.image.adjust_contrast(im, contrast_factor=0.9)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast9.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)
            contrast = tf.image.adjust_contrast(im30, contrast_factor=0.9)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast309.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)

            # aumentare contrasto foto di 1.5
            contrast = tf.image.adjust_contrast(im, contrast_factor=1.5)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast15.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)
            contrast = tf.image.adjust_contrast(im30, contrast_factor=1.5)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast3015.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)

            # aumentare contrasto foto di 2
            contrast = tf.image.adjust_contrast(im, contrast_factor=2)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast2.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)
            contrast = tf.image.adjust_contrast(im30, contrast_factor=2)
            encC = tf.image.encode_jpeg(contrast)
            fnameC = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "contrast302.jpg")
            fwriteC = tf.io.write_file(fnameC, encC)

            # aumentare luminosità foto di 0.1
            brightness = tf.image.adjust_brightness(im, delta=0.1)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness1.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)
            brightness = tf.image.adjust_brightness(im30, delta=0.1)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness301.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)

            # aumentare luminosità foto di 0.2
            brightness = tf.image.adjust_brightness(im, delta=0.2)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness2.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)
            brightness = tf.image.adjust_brightness(im30, delta=0.2)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness302.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)

            # aumentare luminosità foto di 0.5
            brightness = tf.image.adjust_brightness(im, delta=0.3)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness3.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)
            brightness = tf.image.adjust_brightness(im30, delta=0.3)
            encB = tf.image.encode_jpeg(brightness)
            fnameB = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "brightness303.jpg")
            fwriteB = tf.io.write_file(fnameB, encB)

            # rendere bianconero la foto
            gray = tf.image.rgb_to_grayscale(im)
            encG = tf.image.encode_jpeg(gray)
            fnameG = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "gray.jpg")
            fwriteG = tf.io.write_file(fnameG, encG)
            gray = tf.image.rgb_to_grayscale(im30)
            encG = tf.image.encode_jpeg(gray)
            fnameG = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "gray30.jpg")
            fwriteG = tf.io.write_file(fnameG, encG)        

            #noise = add_salt_pepper_noise(img3)
            #encN = tf.image.encode_jpeg(noise)
            #fnameN = tf.constant("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(count) + "noise.jpg")
            #fwriteN = tf.io.write_file(fnameN, encN)

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