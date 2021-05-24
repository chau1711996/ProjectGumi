package com.example.projectgumi.ui.shop

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgumi.R
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.databinding.AdapterSlideBinding

class SlideAdapter(val action: (String) -> Unit): ListAdapter<ImageSlideModel, SlideAdapter.SlideViewHolder>(SlideCallback()) {
    inner class SlideViewHolder(val binding: AdapterSlideBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: ImageSlideModel){
            binding.apply {
                layoutSlide.setOnClickListener {
                    action(item.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_slide, parent, false)
        return SlideViewHolder(AdapterSlideBinding.bind(view))
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SlideCallback: DiffUtil.ItemCallback<ImageSlideModel>() {
        override fun areItemsTheSame(oldItem: ImageSlideModel, newItem: ImageSlideModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ImageSlideModel,
            newItem: ImageSlideModel
        ): Boolean {
            return oldItem == newItem
        }
    }


}