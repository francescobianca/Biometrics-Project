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

layout = [[sg.Text('Le tue immagini sono state salvate correttamente')],
[sg.Text('Se volessi rifare le foto premi "Ripeti Operazione", altrimenti "Completa Operazione"')],      
[sg.Button('Ripeti Operazione'),sg.Button('Completa operazione')]]      
window = sg.Window('Raccolta dati', layout)
event, values = window.read()
window.close()
if event == 'Ripeti Operazione':
    capturefaces.getDataset(face_id)
if event == 'Completa Operazione':
  	


