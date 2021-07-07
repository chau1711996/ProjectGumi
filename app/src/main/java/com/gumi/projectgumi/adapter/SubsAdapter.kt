package com.gumi.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.billingclient.api.SkuDetails
import com.gumi.projectgumi.R
import com.gumi.projectgumi.databinding.SubsItemBinding

class SubsAdapter(val action: (SkuDetails) -> Unit) :
    ListAdapter<SkuDetails, SubsAdapter.ProductHolder>(ProductCallback()) {
    inner class ProductHolder(private val binding: SubsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(skuDetails: SkuDetails) {
            binding.txtDes.text = skuDetails.description
            binding.txtPrice.text = skuDetails.price
            binding.txtTitle.text = skuDetails.title
            binding.item.setOnClickListener { action(skuDetails) }
        }
    }

    class ProductCallback : DiffUtil.ItemCallback<SkuDetails>() {
        override fun areItemsTheSame(oldItem: SkuDetails, newItem: SkuDetails): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SkuDetails, newItem: SkuDetails): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subs_item, parent, false)
        return ProductHolder(SubsItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.bind(getItem(position))
    }

}