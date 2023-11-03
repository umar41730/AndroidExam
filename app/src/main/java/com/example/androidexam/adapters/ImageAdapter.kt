package com.example.androidexam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.androidexam.model.ImageModel
import com.example.androidexam.R

class ImageAdapter(private val images: List<ImageModel>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageModel: ImageModel) {
            // Load the image using Glide into the ImageView in the item layout
            Glide.with(itemView)
                .load(imageModel.imageUrl)
                .apply(RequestOptions().centerCrop())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(itemView.findViewById(R.id.imageView))

        }
    }
}