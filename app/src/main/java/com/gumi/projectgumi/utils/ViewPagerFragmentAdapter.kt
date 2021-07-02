package com.gumi.miniproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.gumi.projectgumi.ui.account.AccountFragment
import com.gumi.projectgumi.ui.cart.CartFragment
import com.gumi.projectgumi.ui.explore.ExploreFragment
import com.gumi.projectgumi.ui.favorite.FavoriteFragment
import com.gumi.projectgumi.ui.shop.ShopFragment

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