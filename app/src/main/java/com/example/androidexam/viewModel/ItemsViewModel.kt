package com.example.androidexam.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidexam.model.ItemsModel
import com.example.androidexam.repository.ItemsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ItemsViewModel(private val itemsRepository: ItemsRepository) : ViewModel() {

    private val _imageUrls = MutableLiveData<List<ItemsModel>>()
    val itemNames: LiveData<List<ItemsModel>>
        get() = _imageUrls

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        fetchImageUrls()
    }

    private fun fetchImageUrls() {
        coroutineScope.launch {
            val images = itemsRepository.getItems()
            _imageUrls.value = images
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}