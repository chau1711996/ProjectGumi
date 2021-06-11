package com.example.projectgumi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.miniproject.adapter.ViewPagerFragmentAdapter
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.databinding.ActivityMainBinding
import com.example.projectgumi.ui.account.ProfileActivity
import com.example.projectgumi.ui.login.LoginActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.example.projectgumi.ui.splash.OnBordingActivity
import com.example.projectgumi.viewmodel.CartViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var myAdapter: ViewPagerFragmentAdapter? = null
    private val cartViewModel by viewModel<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
    }

    private fun init() {
        val images = listOf(R.drawable.ic_shop, R.drawable.ic_explore, R.drawable.ic_cart, R.drawable.ic_favorite, R.drawable.ic_account)
        val titles = listOf(getString(R.string.shop), getString(R.string.explore),getString(R.string.cart), getString(R.string.favorite), getString(R.string.account))

        binding?.apply {
            viewPager.isUserInputEnabled = false
            myAdapter = ViewPagerFragmentAdapter(supportFragmentManager, lifecycle)
            viewPager.adapter = myAdapter
            TabLayoutMediator(tabLayout, viewPager){ tab, pos ->
                tab.icon = ContextCompat.getDrawable(this@MainActivity, images[pos])
                tab.text = titles[pos]
            }.attach()
        }
    }

    fun goToFragment(position: Int) {
        binding?.apply {
            viewPager.setCurrentItem(position)
        }
    }

    fun loginProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    fun loginPhoneNumber() {
        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    fun loginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    fun loginOnBording() {
        val intent = Intent(this, OnBordingActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    fun insertCart(product: Product){
        cartViewModel.insertCart(product)
        Toast.makeText(this, "insert ${product.name} to cart success", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val SHOP = 0
        const val Explore = 1
        const val Cart = 2
        const val Favorite = 3
        const val Account = 4
    }
}