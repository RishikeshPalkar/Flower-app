



# 🌸 Flower Classification using CNN (TensorFlow Lite)

A lightweight **Convolutional Neural Network (CNN)** model that classifies flower images into 5 categories — built with **TensorFlow**, trained in **Google Colab**, and converted to **TensorFlow Lite** for Android deployment.

---

## 🚀 Overview

* **Dataset:** [TF Flowers](https://www.tensorflow.org/datasets/catalog/tf_flowers) (3670 images)
* **Classes:** Daisy, Dandelion, Rose, Sunflower, Tulip
* **Frameworks:** TensorFlow, Keras, TensorFlow Lite
* **Goal:** On-device flower recognition for Android apps

---

## ⚙️ Steps

1. **Load Dataset**

   ```python
   dataset, info = tfds.load('tf_flowers', as_supervised=True, with_info=True)
   ```
2. **Preprocess & Train**

   ```python
   model.fit(train_ds, epochs=10, validation_data=test_ds)
   ```
3. **Convert to TFLite**

   ```python
   converter = tf.lite.TFLiteConverter.from_saved_model("flower_cnn_model")
   tflite_model = converter.convert()
   ```
4. **Download Model**

   ```python
   from google.colab import files
   files.download("flower_model.tflite")
   ```

---

## 📱 Android Integration

* Place `flower_model.tflite` in `app/src/main/assets/`
* Load it with TensorFlow Lite Interpreter in Kotlin/Java
* Use it for real-time flower classification in your app 🌼

---

## 📊 Results

| Metric     | Value       |
| ---------- | ----------- |
| Accuracy   | ~85–90%     |
| Model Size | ~2 MB       |
| Input      | 128×128 RGB |

---

## 👨‍💻 Author

**Rishikesh Palkar**
Android Developer | AI Enthusiast | Computer Science Student @ YCCE


