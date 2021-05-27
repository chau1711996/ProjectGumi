package com.example.projectgumi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.miniproject.adapter.ViewPagerFragmentAdapter
import com.example.projectgumi.databinding.ActivityMainBinding
import com.example.projectgumi.ui.account.AccountFragment
import com.example.projectgumi.ui.cart.CartFragment
import com.example.projectgumi.ui.explore.ExploreFragment
import com.example.projectgumi.ui.favorite.FavoriteFragment
import com.example.projectgumi.ui.shop.ShopFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        val images = listOf(R.drawable.ic_shop, R.drawable.ic_explore, R.drawable.ic_cart, R.drawable.ic_favorite, R.drawable.ic_account)
        val titles = listOf(getString(R.string.shop), getString(R.string.explore),getString(R.string.cart), getString(R.string.favorite), getString(R.string.account))

        val fragments = mutableListOf(
            ShopFragment(),
            ExploreFragment(),
            CartFragment(),
            FavoriteFragment(),
            AccountFragment()
        )
        binding?.apply {
            //chặn user vuốt ngang viewpager
            viewPager.isUserInputEnabled = false

            viewPager.adapter = ViewPagerFragmentAdapter(supportFragmentManager, fragments, lifecycle)
            TabLayoutMediator(tabLayout, viewPager){ tab, pos ->
                tab.icon = ContextCompat.getDrawable(this@MainActivity, images[pos])
                tab.text = titles[pos]
            }.attach()
        }
    }
}