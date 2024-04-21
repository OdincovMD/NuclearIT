import io

import torchvision
from PIL import Image
from ultralytics import YOLO

model = YOLO(f'static/best.pt')
submissions = dict()
import torch
import os
from super_gradients.training import models
model2 = models.get("yolo_nas_pose_l", pretrained_weights="coco_pose")
device = 'cuda:0' if torch.cuda.is_available() else 'cpu'
confidence = 0.6
model2.to(device)
output_folder = "static/output"
def predict(filename,file):
    file = Image.open(file)
    result = model(file)
    probs = result[0].probs.data.tolist()
    name = result[0].names
    output =model2.predict(file, conf=confidence)
    output_path = os.path.join(output_folder, f"{filename}")
    output.save(output_path)
    return list(name.values()), probs
    # return max(zip(list(name.values()), probs), key=lambda x: x[1])[0]


# Создание папки для сохранения предсказанных изображений, если её ещё нет
# os.makedirs(output_folder, exist_ok=True)
# Проход по каждому файлу в папке с изображениями для предсказания


    # Предсказание модели

    # Сохранение предсказанного изображения в папку output_folder
    # output_path = os.path.join(output_folder, f"1_prediction.jpg")
    # output.save(output_path)
    #
    # print(f"Сохранено предсказанное изображение: {output_path}")
