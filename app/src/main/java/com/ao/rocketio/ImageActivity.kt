package com.ao.rocketio

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ao.rocketio.api.RetrofitInstance
import com.ao.rocketio.data.Image
import com.ao.rocketio.databinding.ActivityImageBinding
import com.ao.rocketio.enums.MediaEnum
import kotlinx.android.synthetic.main.activity_image.*
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.Executors


const val TAGIMAGE = "ImageActivity"
class ImageActivity : AppCompatActivity() {

    private var imageDataFromApi: Image? = null
    private lateinit var binding: ActivityImageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = getString(R.string.app_name)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.apod.getData()
            } catch (e: IOException){
                Log.e(TAGIMAGE, "IOException")
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAGIMAGE, "IOException")
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                imageDataFromApi = response.body()
                Log.e(TAGIMAGE, "Loaded")
                loaderImage.visibility = View.INVISIBLE
                llImageContainer.visibility = View.VISIBLE
                tvTitle.text = imageDataFromApi?.title
                tvExplanation.text = imageDataFromApi?.explanation
                if (imageDataFromApi?.media_type.equals(MediaEnum.Video.toString().lowercase())){
                    setupVideoView()
                }else {
                    setupImageView()
                }

            }else {
                Log.e(TAGIMAGE, "Response not succesfull")
            }
        }


    }

    private fun setupImageView() {
        val imageView = ImageView(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val executor = Executors.newSingleThreadExecutor()

        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null

        executor.execute {
            val imageUrl = imageDataFromApi?.url.toString()

            try {
                val `in` = java.net.URL(imageUrl).openStream()
                image = BitmapFactory.decodeStream(`in`)

                handler.post {
                    imageView.setImageBitmap(image)
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        imageView.layoutParams = layoutParams
        binding.flMediaContainer.addView(imageView)
    }

    private fun setupVideoView() {
        Log.e(TAGIMAGE, "Video start")
        val videoView = VideoView(this)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        videoView.layoutParams = layoutParams

        videoView.setVideoURI(Uri.parse(imageDataFromApi?.url.toString()))

        binding.flMediaContainer.addView(videoView)
        layoutParams.setMargins(10, 10, 10, 10)
        videoView.requestFocus()
        videoView.start()
    }
}