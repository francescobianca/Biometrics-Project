import cv2
import os
import PySimpleGUI as sg
import shutil
import capturefaces

def createDataset():
	layout = [[sg.Text('Benvenuto nella sezione di raccolta immagini del tuo volto.')],
	           [sg.Text('Per iniziare, inserisci la tua matricola')],
	                 [sg.InputText()],      
	                 [sg.Button('Raccolta dati'), sg.Button('Annulla')]]      
	window = sg.Window('Raccolta dati', layout)
	event, values = window.read()
	window.close()
	face_id = values[0]
	if event == 'Raccolta dati':
		ret = capturefaces.getDataset(face_id)
		if ret == True:
			layout = [[sg.Text('Complimenti matricola '+face_id+'! Operazione conclusa con successo.')],
		        	[sg.Button('Ok')]]
			window = sg.Window('Raccolta dati', layout)
			window.read()
			window.close()
		return ret
	else:
		return False


