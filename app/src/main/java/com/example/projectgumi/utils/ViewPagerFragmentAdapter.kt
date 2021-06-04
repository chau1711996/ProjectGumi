package com.example.miniproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.projectgumi.ui.account.AccountFragment
import com.example.projectgumi.ui.cart.CartFragment
import com.example.projectgumi.ui.explore.ExploreFragment
import com.example.projectgumi.ui.favorite.FavoriteFragment
import com.example.projectgumi.ui.shop.ShopFragment

class ViewPagerFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment{
        when(position){
            0 -> return ShopFragment()
            1 -> return ExploreFragment()
            2 -> return CartFragment()
            3 -> return FavoriteFragment()
            4 -> return AccountFragment()
        }
        return ShopFragment()
    }
}