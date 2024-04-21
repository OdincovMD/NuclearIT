# NuclearIT
# Разделение данных на обучающий и валидационный наборы.

train_dir и val_dir - переменные, содержащие пути к каталогам для обучающего и валидационного наборов соответственно.
each_val_img_ind - переменная, определяющая частоту выборки изображений для валидационного набора. В данном случае каждое пятое изображение будет добавлено в валидационный набор.
class_names - список, содержащий имена классов, которые вы хотите разделить на обучающий и валидационный наборы.
С помощью циклов for создаются каталоги для каждого класса в обучающем и валидационном наборах.
Внутренний цикл for проходит по изображениям в каждом классе и копирует их в соответствующие каталоги для обучения и валидации с учетом заданной частоты выборки.

# Реализуется аугментация изображений для обучающего набора данных.

Создаются два списка папок с изображениями: image_folders_train, содержащий пути к папкам обучающего набора, и image_folders_competition, содержащий пути к папкам соревновательного набора данных.
Затем определяются два списка аугментаций: train_transforms_data_all для обучающего набора и train_transforms_dataset_source для соревновательного набора(содержит специальную обработку для изображений соревнования(уменьшение яркости и тд.).
Для каждого изображения в папке обучающего набора выполняется следующее:
Проверяется, является ли изображение частью данных или оригинальным изображением. Если это оригинальное изображение и его индекс делится на augment_number без остатка, применяются аугментации из train_transforms_data_all.
Если изображение является частью данных (файл начинается с 'data_'), применяются аугментации из train_transforms_dataset_source.
Аугментированные изображения сохраняются в той же папке, что и исходные изображения, с добавлением индекса аугментации в их имена.



# Далее происходит обучение модели YOLOv8l-cls с использованием предварительно обученных весов, загруженных из файла "yolov8l-cls.pt". Обучение происходит на данных, расположенных в папке "/kaggle/working/", с продолжительностью обучения в 20 эпох и размером изображения 128x128 пикселей.

DATA_DIR='/kaggle/working/': Устанавливается путь к корневой папке данных.
model = YOLO("yolov8l-cls.pt"): Создается экземпляр модели YOLO с загрузкой предварительно обученных весов из файла "yolov8l-cls.pt".
results = model.train(data=f'{DATA_DIR}', epochs=20, imgsz=128): Запускается процесс обучения модели. Обучение происходит на данных, расположенных в папке, указанной в переменной DATA_DIR, в течение 20 эпох, с размером изображения 128x128 пикселей.


# Далее запуск задачи классификации с использованием модели YOLOv8l-cls на валидационных данных, с последующем анализом полученной модели.

!yolo task=classify mode=val: Запускается задача классификации с использованием модели YOLOv8l-cls в режиме валидации.
model=/kaggle/working/runs/classify/train/weights/best.pt: Указывается путь к модели, которая будет использоваться для классификации. В данном случае, это модель, обученная на тренировочных данных и сохраненная в папке /kaggle/working/runs/classify/train/weights/ под именем best.pt.
data='{DATA_DIR}': Указывается путь к данным, которые будут использоваться для валидации. Переменная {DATA_DIR}, вероятно, должна быть заменена на актуальный путь к данным.

# Предикт
Происходит получение предикатка на тестовых данных.
необзодимо указать путь к тестовым данным, а также в 
model = YOLO(f'/kaggle/working/runs/classify/train/weights/best.pt')
указать путь к весам, лежащим на google диске. 

# Происходит детекция человека.

В строке 
output_folder = '/kaggle/working/new'
необходимо указать путь куда, будут сохранены анотированные изображенния 
