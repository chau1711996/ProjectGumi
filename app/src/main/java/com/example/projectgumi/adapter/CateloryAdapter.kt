package com.example.projectgumi.adapter

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
import com.example.projectgumi.databinding.ItemExploreBinding
import com.example.projectgumi.utils.Utils.TYPE_EXPLORE
import com.example.projectgumi.utils.Utils.TYPE_SHOP

class CateloryAdapter(val type: Int, val action: (Catelory) -> Unit) :
    ListAdapter<Catelory, RecyclerView.ViewHolder>(
        CateloryCallback()
    ) {
    inner class CateloryHolder(val binding: ItemCateloryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cate: Catelory) {
            binding.apply {
                catelory = cate
                layoutItemCatelory.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        randomBackground()
                    )
                )
                layoutItemCatelory.setOnClickListener {
                    action(cate)
                }
            }
        }
    }

    inner class ExploreHolder(val binding: ItemExploreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cate: Catelory) {
            binding.apply {
                val color = ContextCompat.getColor(
                    binding.root.context,
                    randomBackground()
                )
                catelory = cate
                layoutItemCatelory.setCardBackgroundColor(color)
                layoutItemCatelory.setOnClickListener {
                    action(cate)
                }
            }
        }
    }

    protected fun randomBackground(): Int {
        val result: Int
        when ((0..3).random()) {
            0 -> result = R.color.catelory1
            1 -> result = R.color.catelory2
            2 -> result = R.color.catelory3
            else -> result = R.color.catelory4
        }
        return result
    }

    override fun getItemViewType(position: Int): Int {
        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_SHOP -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_catelory, parent, false)
                CateloryHolder(ItemCateloryBinding.bind(view))
            }
            else -> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_explore, parent, false)
                ExploreHolder(ItemExploreBinding.bind(view))
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_SHOP -> {
                val result = holder as CateloryHolder
                result.bind(getItem(position))
            }
            TYPE_EXPLORE -> {
                val result = holder as ExploreHolder
                result.bind(getItem(position))
            }
        }
    }

    class CateloryCallback : DiffUtil.ItemCallback<Catelory>() {
        override fun areItemsTheSame(oldItem: Catelory, newItem: Catelory): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Catelory, newItem: Catelory): Boolean {
            return oldItem == newItem
        }
    }

}