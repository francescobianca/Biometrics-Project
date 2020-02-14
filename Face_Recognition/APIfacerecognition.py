from flask import Flask, jsonify
import savemodel
import createdataset
import evaluation

app = Flask(__name__)


@app.route('/savemodel')
def callSaveModel():
	savemodel.saveModel()
	return jsonify({"success": True})

@app.route('/createdataset')
def callCreateDataset():
	ret = createdataset.createDataset()
	return jsonify({"success": ret})

@app.route('/evaluation')
def callEvaluation():
	attendances = []
	attendances = evaluation.startEvaluation()
	if not attendances:
		return jsonify({"success": False})
	else:
		return jsonify({"success": True},attendances)


if __name__ == '__main__':
    app.run() 