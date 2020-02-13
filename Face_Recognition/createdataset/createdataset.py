import cv2
import os
import PySimpleGUI as sg
import shutil
import capturefaces

layout = [[sg.Text('Benvenuto nella sezione di raccolta immagini del tuo volto.')],
           [sg.Text('Per iniziare, inserisci la tua matricola')],
                 [sg.InputText()],      
                 [sg.Button('Raccolta dati'), sg.Cancel()]]      
window = sg.Window('Raccolta dati', layout)
event, values = window.read()
window.close()
face_id = values[0]
if event == 'Raccolta dati':
	capturefaces.getDataset(face_id)
	layout = [[sg.Text('Complimenti matricola '+face_id+'! Operazione conclusa con successo.')],
        	[sg.Button('Ok')]]
	window = sg.Window('Raccolta dati', layout)
	window.read()  	


