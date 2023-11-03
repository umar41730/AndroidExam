package com.example.androidexam.modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidexam.repository.ImageRepository
import com.example.androidexam.viewModel.ImageViewModel

class ImageViewModelFactory(private val repository: ImageRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)) {
            return ImageViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}