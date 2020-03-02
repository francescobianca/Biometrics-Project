from flask import Flask, jsonify
import evaluation

app = Flask(__name__)

'''
@app.route('/savemodel')
def callSaveModel():
	savemodel.saveModel()
	return jsonify({"success": True})

@app.route('/createdataset')
def callCreateDataset():
	ret = createdataset.createDataset()
	return jsonify({"success": ret})
'''

@app.route('/evaluation')
def callEvaluation():
	attendances = []
	attendances = evaluation.startEvaluation()
	return jsonify(attendances)


if __name__ == '__main__':
    app.run() 