package com.sixbynine.civ3guide.android

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

suspend fun Context.readTextAsset(filename: String) = withContext(Dispatchers.IO) {
    var reader: BufferedReader? = null
    try {
        reader = BufferedReader(InputStreamReader(assets.open(filename), "UTF-8"))
        val result = StringBuilder()
        while (true) {
            result.appendLine(reader.readLine() ?: break)
        }
        result.toString()
    } catch (e: IOException) {
        Log.e("TextAssets", "Error reading $filename", e)
        null
    } finally {
        reader?.close()
    }
}