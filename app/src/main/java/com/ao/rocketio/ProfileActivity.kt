package com.ao.rocketio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.ao.rocketio.data.BEUser
import com.ao.rocketio.data.UserRepositoryInDB
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.ao.rocketio.enums.EventTypes

class ProfileActivity : AppCompatActivity() {

    val TAG = "Profile Activity"
    var mFile: File? = null
    private val PERMISSION_REQUEST_CODE = 1
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 101
    var userData: BEUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        UserRepositoryInDB.initialize(this)

        checkPermissions()

        setupDataObserver()
    }

    // get user data from database
    private fun setupDataObserver() {
        val mRep = UserRepositoryInDB.get()
        val getUserObserver = Observer<BEUser> { user ->
            try {
                etRank.setText(user.rank)
                etName.setText(user.name)
                etLatitude.setText(user.latitude.toString())
                etLongitude.setText(user.longitude.toString())
                userData = user
            } catch (e: Exception) {
                Intent(this@ProfileActivity , ErrorActivity::class.java ).also {
                    startActivity(it)
                }
            }
        }

        mRep.getById(1).observe(this, getUserObserver)
    }

    // handeler for action onTakePicture
    fun onTakePicture(view: View) {
        mFile = getOutputMediaFile("Camera")

        if (mFile == null) {
            Toast.makeText(this, "Could not create file...", Toast.LENGTH_LONG).show()
            return
        }

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val applicationId = "com.ao.rocketio"
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(
            this,
            "${applicationId}.provider",
            mFile!!))

        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
    }

    // handeling of image taken
    private fun getOutputMediaFile(folder: String): File? {
        val mediaStorageDirectory = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), folder)

        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdir()) {
                Log.d(TAG, "failed to create directory")
                return null
            }
        }

        val timeStamp: String = SimpleDateFormat("yyyyMMdd HHmmss").format(Date())
        val postfix = "jpg"
        val prefix = "IMG"
        return File(mediaStorageDirectory.path +
                    File.separator + prefix +
                    "_" + timeStamp + "." + postfix)

    }

    // result of camera handeling
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val mImage = findViewById<ImageView>(R.id.imgProfile)
        when(requestCode) {
            CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE ->
                if (resultCode == RESULT_OK)
                    showImage(mImage, mFile!!)
                else handleOther(resultCode)
        }
    }

    // if camera canceled or error
    private fun handleOther(resultCode: Int) {
        if (resultCode == RESULT_CANCELED)
            Toast.makeText(this, "Canceled...", Toast.LENGTH_LONG).show()
        else Toast.makeText(this, "Picture NOT taken - unknown error...", Toast.LENGTH_LONG).show()
    }

    // show the image
    private fun showImage(img: ImageView, f: File) {
        img.setImageURI(Uri.fromFile(f))
        img.setBackgroundColor(Color.RED)
    }

    // check permisions for writing and using camera
    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        val permission = mutableListOf<String>()
        if ( ! isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) permission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if ( ! isGranted(Manifest.permission.CAMERA)) permission.add(Manifest.permission.CAMERA)
        if (permission.size > 0)
            ActivityCompat.requestPermissions(this, permission.toTypedArray(), PERMISSION_REQUEST_CODE)
    }

    private fun isGranted(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    // save user
    fun onSaveUser(view: View) {
        //get user repository
        val mRep = UserRepositoryInDB.get()

        // try to update the data of user to database
        try {
            userData?.name = etName.text.toString()
            userData?.rank = etRank.text.toString()
            userData?.latitude = etLatitude.text.toString().toDouble()
            userData?.longitude = etLongitude.text.toString().toDouble()

            userData?.let { mRep.update(it) }

            // if update succesfull go to home activity
            Intent(this@ProfileActivity , HomeActivity::class.java ).also {
                startActivity(it)
            }
        } catch (e: Exception) {
            // if error occours redirect to error activity
            Intent(this@ProfileActivity , ErrorActivity::class.java ).also {
                startActivity(it)
            }
        }
    }
}
