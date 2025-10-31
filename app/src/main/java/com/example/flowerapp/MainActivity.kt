package com.example.flowerapp

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var resultText: TextView
    private var selectedImage: Bitmap? = null

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val source = ImageDecoder.createSource(this.contentResolver, uri)
                selectedImage = ImageDecoder.decodeBitmap(source).copy(Bitmap.Config.ARGB_8888, true)
                imageView.setImageBitmap(selectedImage)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        resultText = findViewById(R.id.resultText)
        val selectButton: Button = findViewById(R.id.selectButton)
        val predictButton: Button = findViewById(R.id.predictButton)

        selectButton.setOnClickListener {
            val intent = android.content.Intent(android.content.Intent.ACTION_GET_CONTENT).apply {
                type = "image/*"
            }
            selectImageLauncher.launch(intent)
        }

        predictButton.setOnClickListener {
            selectedImage?.let {
                predict(it)
            } ?: Toast.makeText(this, "Select an image first", Toast.LENGTH_SHORT).show()
        }
    }

    private fun predict(bitmap: Bitmap) {
        try {
            val imageClassifier = ImageClassifier.createFromFile(this, "flower_model.tflite")
            val tensorImage = TensorImage.fromBitmap(bitmap)
            val results = imageClassifier.classify(tensorImage)

            if (results.isNotEmpty() && results[0].categories.isNotEmpty()) {
                val category = results[0].categories[0]
                val label = category.label
                resultText.text = "Predicted Flower: $label"
            } else {
                resultText.text = "Could not classify image"
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}
