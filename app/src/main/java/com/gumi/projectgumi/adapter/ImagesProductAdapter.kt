package com.gumi.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gumi.projectgumi.R
import com.gumi.projectgumi.data.model.ProductImages
import com.gumi.projectgumi.databinding.AdapterImagesBinding

class ImagesProductAdapter: ListAdapter<ProductImages, ImagesProductAdapter.ProductImagesViewHolder>(
    SlideCallback()
) {
    inner class ProductImagesViewHolder(val binding: AdapterImagesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ProductImages){
            binding.apply {
                image.load(item.url){
                    placeholder(R.drawable.ic_launcher_foreground)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_images, parent, false)
        return ProductImagesViewHolder(AdapterImagesBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ProductImagesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SlideCallback: DiffUtil.ItemCallback<ProductImages>() {
        override fun areItemsTheSame(oldItem: ProductImages, newItem: ProductImages): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductImages,
            newItem: ProductImages
        ): Boolean {
            return oldItem == newItem
        }
    }


}