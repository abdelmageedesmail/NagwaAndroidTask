package com.abdelmageed.nagwaandroidtask.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.util.Log
import androidx.core.app.ActivityCompat
import com.abdelmageed.nagwaandroidtask.data.responses.FilesResponsesItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.DataInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.reflect.Type
import java.net.MalformedURLException
import java.net.URL


class Utils {

    companion object {

        fun loadJSONFromAsset(context: Context): String? {
            lateinit var jsonString: String
            try {
                jsonString = context.assets.open("file_response.json")
                    .bufferedReader()
                    .use { it.readText() }
            } catch (ioException: IOException) {
                Log.e("ex", ioException.toString())
            }
            return jsonString
        }

        fun convertJsonStringToGson(context: Context): List<FilesResponsesItem>? {
            var users: List<FilesResponsesItem>? = null
            val loadJSONFromAsset = loadJSONFromAsset(context)
            if (!loadJSONFromAsset!!.isEmpty()) {
                val gson = Gson()
                val listUserType: Type =
                    object : TypeToken<List<FilesResponsesItem?>?>() {}.type
                users = gson.fromJson(loadJSONFromAsset, listUserType)
                return users!!
            } else {
                return users
            }
        }

        fun saveFileUsingMediaStore(
            url: String,
            fileName: String,
            extension: String
        ): Boolean {
            var isDownloaded: Boolean = false

            try {

                val total: Long = 1
                var itemLength = 0
                val u = URL(url)
                val input = u.openStream()
                val dis = DataInputStream(input)
                val buffer = ByteArray(1024)
                var length: Int
                val folder =
                    Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS.toString() +
                                File.separator + "NagwaTask"
                    )

                if (!folder.exists()) {
                    folder.mkdirs()
                }

                val file = File(folder.toString(), fileName + "." + extension)
                val fos = FileOutputStream(file)

                while (dis.read(buffer).also { length = it } > 0) {
                    fos.write(buffer, 0, length)
                }
                isDownloaded = true
            } catch (mue: MalformedURLException) {
                Log.e("SYNC getUpdate", "malformed url error", mue)
                isDownloaded = false
            } catch (ioe: IOException) {
                Log.e("SYNC getUpdate", "io error", ioe)
                isDownloaded = false
            } catch (se: SecurityException) {
                Log.e("SYNC getUpdate", "security error", se)
                isDownloaded = false
            }
            return isDownloaded
        }


        fun getAllFiles(): Array<File> {
            var files: Array<File> = emptyArray()
            val path =
                Environment.getExternalStorageDirectory()
                    .toString() + "/" + Environment.DIRECTORY_DOWNLOADS.toString() +
                        File.separator + "NagwaTask"

//             Environment.getExternalStorageDirectory().toString() + "/Pictures"
            Log.d("Files", "Path: $path")
            val directory = File(path)
            if (directory.listFiles() != null) {
                files = directory.listFiles()!!
            }
            return files
        }

        fun addPermissions(context: Activity?) {
            val PERMISSION_ALL = 1
            val PERMISSIONS = arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            if (!hasPermissions(context, *PERMISSIONS)) {
                ActivityCompat.requestPermissions(context!!, PERMISSIONS, PERMISSION_ALL)
            }
        }

        fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
            if (context != null && permissions != null) {
                for (permission in permissions) {
                    if (ActivityCompat.checkSelfPermission(
                            context,
                            permission!!
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        return false
                    }
                }
            }
            return true
        }
    }
}