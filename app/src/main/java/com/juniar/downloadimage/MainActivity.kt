package com.juniar.downloadimage

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    val url = "data:image/png;base64,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"

    companion object {
        val WRITE_EXTERNAL_STORAGE_CODE=100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Glide.with(this@MainActivity)
                .load(url)
                .into(iv_example)

        btn_download.setOnClickListener {
            if (sdkVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
                if (checkRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    GlideDownload.with(this@MainActivity)
                            .asBitmap()
                            .load(url)
                            .into(object : SimpleTarget<Bitmap>(100, 100) {
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    saveImage(resource)
                                }
                            })
                } else {
                    makeRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_CODE)
                }
            } else {
                makeRequest(Manifest.permission.WRITE_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    GlideDownload.with(this@MainActivity)
                            .asBitmap()
                            .load(url)
                            .into(object : SimpleTarget<Bitmap>(100, 100) {
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    saveImage(resource)
                                }
                            })
                }
            }
        }
    }

    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String? = null

        val imageFileName = "JPEG_" + "FILE_NAME" + ".jpg"
        val storageDir = File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/IRFAN_HOMO")
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, e.localizedMessage, Toast.LENGTH_LONG).show()
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath)
            Toast.makeText(this@MainActivity, "IMAGE SAVED", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this@MainActivity, "gagal", Toast.LENGTH_LONG).show()
        }
        return savedImagePath
    }

    private fun galleryAddPic(imagePath: String?) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
        sendBroadcast(mediaScanIntent)
    }
}
