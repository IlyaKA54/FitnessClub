package com.example.fitnessclub.data.Model

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.result.ActivityResultLauncher
import java.io.InputStream

class ImageManager {
    fun imageToBase64(uri: Uri, contentResolver: ContentResolver) : String?{
        val inputStream = contentResolver.openInputStream(uri)

        val bytes = inputStream?.readBytes()
        return bytes?.let{
            Base64.encodeToString(it, Base64.DEFAULT)
        }?: ""
    }

    fun uriToBitmap(uri: Uri, contentResolver: ContentResolver): Bitmap? {
        return try {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun base64ToBitmap(str: String?):Bitmap?{
         return try {
             val base64Image = Base64.decode(str, Base64.DEFAULT)
             BitmapFactory.decodeByteArray(base64Image,0,base64Image.size)
        } catch (e: Exception) {
            null
        }
    }
}