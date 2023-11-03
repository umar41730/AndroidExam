package com.example.androidexam.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidexam.model.ImageModel
import com.example.androidexam.repository.ImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageViewModel(private val imageRepository: ImageRepository) : ViewModel() {

    private val _imageUrls = MutableLiveData<List<ImageModel>>()
    val imageUrls: LiveData<List<ImageModel>>
        get() = _imageUrls

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        fetchImageUrls()
    }

    private fun fetchImageUrls() {
        coroutineScope.launch {
            val images = imageRepository.getImageUrls()
            _imageUrls.value = images
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}