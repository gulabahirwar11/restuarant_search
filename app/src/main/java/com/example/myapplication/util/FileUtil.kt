package com.example.myapplication.util

import android.content.Context
import java.io.IOException
import java.io.InputStream

object FileUtil {
    /**
     * Read asset folder json file and covert into String
     * @param context : Context
     * @param fileName : Json File Name
     * @return String
     */
    fun getJsonFromAssets(context: Context, fileName: String): String? {
        return try {
            val inputStream: InputStream = context.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}