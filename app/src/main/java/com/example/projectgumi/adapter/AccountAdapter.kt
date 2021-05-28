package com.example.projectgumi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectgumi.R
import com.example.projectgumi.data.model.AccountLayoutModel
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.databinding.AdapterSlideBinding
import com.example.projectgumi.databinding.ItemUserBinding

class AccountAdapter(val action: (String) -> Unit) :
    ListAdapter<AccountLayoutModel, AccountAdapter.AccountViewHolder>(
        AccountCallback()
    ) {
    inner class AccountViewHolder(val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AccountLayoutModel) {
            binding.apply {
                accountModel = item
                layoutItemUser.setOnClickListener {
                    action(item.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return AccountViewHolder(ItemUserBinding.bind(view))
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AccountCallback : DiffUtil.ItemCallback<AccountLayoutModel>() {
        override fun areItemsTheSame(
            oldItem: AccountLayoutModel,
            newItem: AccountLayoutModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: AccountLayoutModel,
            newItem: AccountLayoutModel
        ): Boolean {
            return oldItem == newItem
        }
    }


}