package com.example.androidexam.modelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidexam.repository.ItemsRepository
import com.example.androidexam.viewModel.ItemsViewModel

class ItemsViewModelFactory(private val repository: ItemsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsViewModel::class.java)) {
            return ItemsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}