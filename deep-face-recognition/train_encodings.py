import time
start = time.time()

import pickle
import sys

from operator import itemgetter

import numpy as np
import pandas as pd

from sklearn.pipeline import Pipeline
from sklearn.discriminant_analysis import LinearDiscriminantAnalysis as LDA
from sklearn.preprocessing import LabelEncoder
from sklearn.svm import SVC

data = pickle.loads(open("face_encodings", "rb").read())

print("[INFO] start training face_encodings..")
X = data['encodings']
y = data['names']

clf = SVC(C=1, kernel='linear', probability=True)

clf.fit(X, y)

print("Saving classifier to local folder")
f = open("face_model", "wb")
f.write(pickle.dumps(clf))
f.close()