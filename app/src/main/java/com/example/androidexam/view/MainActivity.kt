package com.example.androidexam.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.androidexam.R
import com.example.androidexam.adapters.ImageAdapter
import com.example.androidexam.adapters.ItemsAdapter
import com.example.androidexam.model.ItemsModel
import com.example.androidexam.modelFactory.ImageViewModelFactory
import com.example.androidexam.modelFactory.ItemsViewModelFactory
import com.example.androidexam.repository.ImageRepository
import com.example.androidexam.repository.ItemsRepository
import com.example.androidexam.viewModel.ImageViewModel
import com.example.androidexam.viewModel.ItemsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ImageViewModel
    private lateinit var viewModelItem: ItemsViewModel
    private lateinit var viewPager: ViewPager2
    private lateinit var itemRv: RecyclerView
    private lateinit var searchEditTextView: EditText
    private lateinit var indicatorLayout: LinearLayout
    private lateinit var nestedScrollView: NestedScrollView
    private  var catId: String=""
    private var itemListRv: List<ItemsModel> = emptyList()
    private var originalTopMargin = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        itemRv = findViewById(R.id.item_rv)
        indicatorLayout = findViewById(R.id.indicatorLayout)
        nestedScrollView = findViewById(R.id.nested_scroll_view)
        searchEditTextView = findViewById(R.id.search_edit_text)

        val repository = ImageRepository(this)
        val repositoryItem = ItemsRepository(this)
        val viewModelFactory = ImageViewModelFactory(repository)
        val viewModelFactoryItem = ItemsViewModelFactory(repositoryItem)
        viewModel = ViewModelProvider(this, viewModelFactory)[ImageViewModel::class.java]
        viewModelItem = ViewModelProvider(this, viewModelFactoryItem)[ItemsViewModel::class.java]

        viewModel.imageUrls.observe(this) { imageList ->
            viewPager.adapter = ImageAdapter(imageList)
            // Initialize the indicator with the number of pages
            setUpIndicator(imageList.size)

        }
        val adapter = ItemsAdapter(emptyList())
        val layoutManager = LinearLayoutManager(this)
        itemRv.layoutManager = layoutManager
        itemRv.adapter = adapter
        viewModelItem.itemNames.observe(this) { itemsList ->
            Log.d("cjsfd","   $itemsList")
            itemListRv=itemsList
            val updatedItemList=filterItemsByCatId("0",itemListRv)
            adapter.updateData(updatedItemList)

        }
        // Listen to page change events in ViewPager2 to update the indicator
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                catId=position.toString()
                val updatedItemList=filterItemsByCatId(position.toString(),itemListRv)
                adapter.updateData(updatedItemList)
                updateIndicator(position)
            }
        })

        searchEditTextView.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val searchText = s.toString()
                val filteredList = filterItemsByName(searchText, itemListRv)
                // Update your RecyclerView adapter with the filtered list
                adapter.updateData(filteredList)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

    }

    fun filterItemsByName(searchText: String, itemsList: List<ItemsModel>): List<ItemsModel> {
        return itemsList.filter { it.itemName.contains(searchText, ignoreCase = true) }
    }

    fun filterItemsByCatId(catId: String, itemsList: List<ItemsModel>): List<ItemsModel> {
        return itemsList.filter { it.catId == catId }
    }
    private fun updateIndicator(position: Int) {
        val count = indicatorLayout.childCount
        for (i in 0 until count) {
            val imageView = indicatorLayout.getChildAt(i) as ImageView
            if (i == position) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.circle_selected
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.circle_unselected
                    )
                )
            }
        }
    }
    private fun setUpIndicator(count: Int) {
        val indicators = arrayOfNulls<ImageView>(count)
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(8, 0, 8, 0)

        for (i in indicators.indices) {
            indicators[i] = ImageView(this)
            indicators[i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.circle_unselected
                )
            )
            indicators[i]?.layoutParams = params
            indicatorLayout.addView(indicators[i])
        }
    }
}