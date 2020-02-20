import os
import cv2

def creafotodestre():
	while(True):
		nome = input('Inserisci nome cartella: ')
		path = "../dataset/"+nome+"/"
		isdir = os.path.isdir(path)	
		if not isdir:
			print("Questa cartella non esiste! RIPROVA. Forse ti sei scordato di usare lettere maiuscole/minuscole, etc.")
		else:
			break

	for file in os.listdir("fotodestra/"):
	    if file.endswith(".jpg"):
	        filename = os.path.join(file)
	        answer = input("La foto destra trovata ha nome "+filename+"! Se non è quella corretta, ricordati che nella cartella fotodestra deve esserci solo una foto, che è quella dell'attore scelto in questo momento! Per continuare scrivere y sennò n: ")
	        if answer == "y":
	        	break
	        else:
	        	return
	    else:
	    	print("Il file deve essere in formato .jpg")
    
	face_detector = cv2.CascadeClassifier("haarcascade_profileface.xml")
	img = cv2.imread("fotodestra/"+filename,1)
	flipped = cv2.flip(img, 1)
	faces = face_detector.detectMultiScale(flipped, 1.3, 5)

	count = 15
	while count < 30:
		#Save the captured image into the datasets folder
		cv2.imwrite(path + nome + '.' + str(count) + ".jpg", img)
		count += 1

creafotodestre()