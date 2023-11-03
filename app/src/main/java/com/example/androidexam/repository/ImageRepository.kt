package com.example.androidexam.repository

import android.content.Context
import com.example.androidexam.model.ImageModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageRepository(private val context: Context) {

    suspend fun getImageUrls(): List<ImageModel> = withContext(Dispatchers.IO) {
        val json: String = context.assets.open("images.json").bufferedReader().use {
            it.readText()
        }

        val gson = Gson()
        val imageList: List<ImageModel> = gson.fromJson(json, Array<ImageModel>::class.java).toList()
        imageList
    }
}