package com.example.emosense.view.predict

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.emosense.R
import com.example.emosense.data.response.PredictResponse
import com.example.emosense.databinding.ActivityPredictBinding
import com.example.emosense.utils.getImageUri
import com.example.emosense.utils.reduceFileImage
import com.example.emosense.utils.uriToFile
import com.example.emosense.view.ViewModelFactory
import com.example.emosense.view.main.MainViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class PredictActivity : AppCompatActivity() {
    private val viewModel by viewModels<PredictViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityPredictBinding

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }

        setupView()

        binding.backButton.setOnClickListener {
            finish()
        }

        viewModel.getSession().observe(this) { user ->
            binding.uploadButton.setOnClickListener { uploadImage(user.id) }
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri!!)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.noPhotoLayout.visibility = View.GONE
            binding.previewImageView.setImageURI(it)
            binding.previewImageView.visibility = View.VISIBLE

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun uploadImage(id: Int) {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val requestBody = id.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    viewModel.predict(multipartBody, requestBody)
                    viewModel.predictResponse.observe(this@PredictActivity) { response ->
                        Log.d("Image File", "Response received: $response")
                        if (response.status == "success") {
                            Log.d("Image File", "Success response received")
                            val intent = Intent(this@PredictActivity, ResultActivity::class.java)
                            intent.putExtra(ResultActivity.EXTRA_IMAGE, uri.toString())
                            intent.putExtra(ResultActivity.EXTRA_RESPONSE, Gson().toJson(response))
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        } else {
                            Log.d("Image File", "Received response, but status is not 'success'")
                        }

                    }
                    Log.d("Image File", "masuk sini?")

                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, PredictResponse::class.java)
                    showToast(errorResponse.message)
                }
            }
        } ?: showToast(getString(R.string.empty_image_warning))
    }

}