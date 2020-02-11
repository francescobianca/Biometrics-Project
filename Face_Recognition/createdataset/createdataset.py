import cv2
import os
cam = cv2.VideoCapture(-1)
cam.set(3, 640) # set video width
cam.set(4, 480) # set video height
face_id = input('\n Enter your studentMatricola ->')

messages = [["FRONT FACE","haarcascade_frontalface_default.xml"],["RIGHT SIDE FACE","haarcascade_profileface.xml"],["LEFT SIDE FACE","haarcascade_profileface.xml"]]

index = 0

for message in messages:
	input(message[0]+". Press Enter to continue.. ")
	face_detector = cv2.CascadeClassifier(message[1])
	print("taking photo..")
	count = 0
	while(True):
		ret, img = cam.read()
		if message[0] == "RIGHT SIDE FACE":
			flipped = cv2.flip(img, 1)
			faces = face_detector.detectMultiScale(flipped, 1.3, 5)
		else:
			faces = face_detector.detectMultiScale(img, 1.3, 5)
		for (x,y,w,h) in faces:
			cv2.rectangle(img, (x,y), (x+w,y+h), (255,0,0), 2)     
			count += 1
			# Save the captured image into the datasets folder
		
			if not os.path.isdir("../dataset/"+str(face_id)):
				os.mkdir("../dataset/"+str(face_id))
			cv2.imwrite("../dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(index) + ".jpg", img[y:y+h,x:x+w])
			
			cv2.imshow('image', img)

			index = index + 1

		k = cv2.waitKey(100) & 0xff # Press 'ESC' for exiting video
		if k == 27:
       			break
		elif count >= 15: # Take 30 face sample and stop video
                	break
cam.release()
cv2.destroyAllWindows()
print("\n Your dataset is complete! Thank you :)")
