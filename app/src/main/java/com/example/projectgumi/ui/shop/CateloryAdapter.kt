package com.example.projectgumi.ui.shop

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectgumi.R
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.databinding.ItemCateloryBinding
import com.example.projectgumi.databinding.ItemProductBinding
import java.util.*

class CateloryAdapter(val action:(String) -> Unit): ListAdapter<Catelory, CateloryAdapter.CateloryHolder>(CateloryCallback()) {
    inner class CateloryHolder(val binding: ItemCateloryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(catelory: Catelory){
            binding.apply {
                textCateloryName.text = catelory.cateloryName
                imageCatelory.load(catelory.cateloryImage)
                layoutItemCatelory.setOnClickListener {
                    action(catelory.cateloryId)
                }
            }
        }
    }

    class CateloryCallback: DiffUtil.ItemCallback<Catelory>() {
        override fun areItemsTheSame(oldItem: Catelory, newItem: Catelory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Catelory, newItem: Catelory): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CateloryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_catelory, parent, false)
        return CateloryHolder(ItemCateloryBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CateloryHolder, position: Int) {
        holder.bind(getItem(position))
    }
}