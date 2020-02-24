import cv2
import numpy as np
from PIL import Image
from keras import models

#Load the saved model
model = models.load_model('faces_model.h5')
video = cv2.VideoCapture(0)

while True:
	_, frame = video.read()

	#Convert the captured frame into RGB
	im = Image.fromarray(frame, 'RGB')
	
	#Resizing into 150x150 because we trained the model with this image size.
	im = im.resize((150,150))
	img_array = np.array(im)

	img_array = np.expand_dims(img_array, axis=0)
	
	#Calling the predict method on model to predict 'me' on the image
	prediction = int(model.predict(img_array)[0][0])
	
	#if prediction is 0, which mean I am missing on the image, then show the frame in gray color.
	print(prediction)
	if prediction==0:
		frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
	
	cv2.imshow("Capturing", frame)
	key=cv2.waitKey(1)
	if key == ord('q'):
		break
video.release()
cv2.destroyAllWindows()
