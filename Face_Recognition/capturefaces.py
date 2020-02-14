import cv2
import os
import PySimpleGUI as sg
import shutil

def getDataset(face_id):
	cam = cv2.VideoCapture(0)
	cam.set(3, 640) # set video width
	cam.set(4, 480) # set video height
	messages = [["FRONT FACE","haarcascade_frontalface_default.xml","frontale"],["RIGHT SIDE FACE","haarcascade_profileface.xml","destro"],["LEFT SIDE FACE","haarcascade_profileface.xml","sinistro"]]
	index = 0
	if os.path.isdir("dataset/"+str(face_id)):
		layout = [[sg.Text('Registrazione delle foto già avvenuta')],
        			[sg.Text('Premere "Ripeti" per acquisire nuovamente le foto (comporta perdita foto precedenti), oppure "Annulla" per terminare')],
        			[sg.Button('Ripeti'), sg.Button('Annulla')]]      
		window = sg.Window('Raccolta dati', layout)
		event, values = window.read()
		window.close()
		if event == 'Ripeti':
			shutil.rmtree("dataset/"+str(face_id))
		elif event == 'Annulla':
			return False
	for message in messages:
		layout = [[sg.Text('Mostra il tuo volto lato '+message[2])],
	           [sg.Text('Premi submit quando sei pronto!')],
	           [sg.Submit()]]      
		window = sg.Window('Raccolta dati', layout)
		window.read()
		window.close()
		face_detector = cv2.CascadeClassifier(message[1])
		count = 1
		while(True):
			ret, img = cam.read()
			if message[0] == "RIGHT SIDE FACE":
				flipped = cv2.flip(img, 1)
				faces = face_detector.detectMultiScale(flipped, 1.3, 5)
			else:
				faces = face_detector.detectMultiScale(img, 1.3, 5)
			
			sg.OneLineProgressMeter('Foto lato '+message[2]+' acquisite.', count, 15, 'key')     
			for (x,y,w,h) in faces:
				cv2.rectangle(img, (x,y), (x+w,y+h), (255,0,0), 2)
				count += 1
				# Save the captured image into the datasets folder
			
				if not os.path.isdir("dataset/"+str(face_id)):
					os.mkdir("dataset/"+str(face_id))
				cv2.imwrite("dataset/"+ str(face_id) + "/" + str(face_id) + '.' + str(index) + ".jpg", img[y:y+h,x:x+w])
				
				cv2.imshow('image', img)

				index = index + 1

			k = cv2.waitKey(100) & 0xff # Press 'ESC' for exiting video
			if k == 27:
				break
			elif count > 15: # Take 30 face sample and stop video
				break
	cam.release()
	cv2.destroyAllWindows()

	layout = [[sg.Text('Le tue immagini sono state salvate correttamente')],
			[sg.Text('Se volessi rifare le foto premi "Ripeti Operazione", altrimenti "Completa Operazione"')],      
			[sg.Button('Ripeti Operazione'),sg.Button('Completa operazione')]]      
	window = sg.Window('Raccolta dati', layout)
	event, values = window.read()
	window.close()
	if event == 'Ripeti Operazione':
		getDataset(face_id)
	elif event == 'Completa operazione':
		return True