package com.example.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.projectgumi.R
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.data.model.OrdersModel
import com.example.projectgumi.databinding.ItemFavoriteBinding
import com.example.projectgumi.databinding.ItemOrdersBinding

class OrdersAdapter:
    ListAdapter<OrdersModel, OrdersAdapter.OrdersHolder>(OrdersCallback()) {
    inner class OrdersHolder(val binding: ItemOrdersBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(p: OrdersModel){
            binding.apply {
                orders = p
                id.text = "Orders Code: ${p.id}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orders, parent, false)
        return OrdersHolder(ItemOrdersBinding.bind(view))
    }

    override fun onBindViewHolder(holder: OrdersHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class OrdersCallback : DiffUtil.ItemCallback<OrdersModel>() {
        override fun areItemsTheSame(oldItem: OrdersModel, newItem: OrdersModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: OrdersModel, newItem: OrdersModel): Boolean {
            return oldItem == newItem
        }
    }


}