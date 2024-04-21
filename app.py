# from firebase_admin import storage

import pyrebase
from flask import Flask, render_template, session, request, redirect, flash
from werkzeug.utils import secure_filename

from model import *

app = Flask(__name__)
config = {

    "apiKey": "AIzaSyAtbO4GaMUa5TqAozkeDksWkWQHjuXV6e4",

    "authDomain": "wayapp-a6d5d.firebaseapp.com",

    "databaseURL": "https://wayapp-a6d5d-default-rtdb.europe-west1.firebasedatabase.app",

    "projectId": "wayapp-a6d5d",

    "storageBucket": "wayapp-a6d5d.appspot.com",

    "messagingSenderId": "1066558457563",

    "appId": "1:1066558457563:web:1bb062025ae32ef9706efe",

    "measurementId": "G-JER5R0Z8S4",
    'serviceAccount': 'static/wayapp-a6d5d-firebase-adminsdk-v0ycz-17465c1c96.json'

}

firebase = pyrebase.initialize_app(config)
auth = firebase.auth()
app.config['SECRET_KEY'] = 'secret!'

storage = firebase.storage()

bucket = storage.bucket


# bucket = storage.bucket()
def upload_file(filename, file):
    # file = file.read()
    classes, probs = predict(filename, file)
    arr = {}
    for i in range(len(classes)):
        arr[classes[i]] = probs[i]
    maximum = max(arr, key=arr.get)
    if maximum == 'smoking':
        dir = 'smoke/'
    else:
        dir = 'nosmoke/'
    # # storage.child("smoke/" + filename + ".jpg").put(img)
    bucket.blob(dir + filename).upload_from_filename('static/output/' + filename)


@app.route('/', methods=['GET', 'POST'])
def index():  # put application's code here
    if ('user' in session):
        return render_template('index.html')
    if request.method == 'POST':
        email = request.form['email']
        password = request.form['password']
        try:
            user = auth.sign_in_with_email_and_password(email, password)
            session['user'] = email
            return redirect('/')
        except:
            return 'invalid login'

    return render_template('login.html')


@app.route('/logout')
def logout():
    session.pop('user', None)
    return redirect('/')


@app.route('/file')
def file():
    message = request.args.get('message')
    return render_template('file.html', message=message)


def allowed_file(filename):
    ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg'}
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS


@app.route('/send', methods=['POST'])
def send():
    if request.method == 'POST':
        # check if the post request has the file part
        if 'file' not in request.files:
            flash('No file part')
            return {'status': 'error', 'message': 'No file part'}
        file = request.files['file']
        # if user does not select file, browser also
        # submit a empty part without filename
        if file.filename == '':
            flash('No selected file')
            return {'status': 'error', 'message': 'No selected file'}
        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            # return os.path.join(app.config['UPLOAD_FOLDER'], filename)
            # return app.config['UPLOAD_FOLDER']
            # file.save(os.path.join(app.config['UPLOAD_FOLDER'], filename))
            upload_file(filename, file)
            return {'status': 'success', 'message': 'File uploaded'}


@app.route('/view', methods=['GET'])
def view():
    if ('user' in session):
        list1 = storage.list_files()
        new_list = []
        for file in list1:
            new_list.append(file.name)
        smoke_images = []
        no_smoke_images = []
        smoke = []
        no_smoke = []
        list_smoke = new_list[new_list.index('smoke/') + 1:]
        list_nosmoke = list(set(new_list) - set(list_smoke))
        list_nosmoke.remove('smoke/')
        list_nosmoke.remove('nosmoke/')
        for file in list_smoke:
            smoke_images.append(file)
        for file in list_nosmoke:
            no_smoke_images.append(file)
        for image in smoke_images:
            img = bucket.get_blob(image)
            smoke.append(img.generate_signed_url(expiration=1735718400))

        for image in no_smoke_images:
            img = bucket.get_blob(image)
            no_smoke.append(img.generate_signed_url(expiration=1735718400))
        final = {
            'smoke': smoke,
            'no_smoke': no_smoke
        }
        return render_template('view.html', images=final)
    return redirect('/')


if __name__ == '__main__':
    app.run(debug=True)  # add debug mode
    # app.run(debug=False, port=5000, host='0.0.0.0')
