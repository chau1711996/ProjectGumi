package com.gumi.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gumi.projectgumi.R
import com.gumi.projectgumi.data.model.CartModel
import com.gumi.projectgumi.databinding.ItemCartBinding
import com.gumi.projectgumi.ui.cart.CartFragment

class CartAdapter(val action:(Int,String,Int)->Unit) :
    ListAdapter<CartModel, CartAdapter.CartHolder>(CartCallback()) {
    inner class CartHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cart: CartModel, position: Int) {
            binding.apply {
                cartModel = cart
                imageDelete.setOnClickListener {
                    action(cart.cartId, CartFragment.DELETE, position)
                }
                imageGiam.setOnClickListener {
                    action(cart.cartId, CartFragment.REDUCTION, position)
                }
                imageTang.setOnClickListener {
                    action(cart.cartId, CartFragment.INCREASE, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartHolder(ItemCartBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class CartCallback : DiffUtil.ItemCallback<CartModel>() {
        override fun areItemsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CartModel, newItem: CartModel): Boolean {
            return oldItem == newItem
        }
    }


}