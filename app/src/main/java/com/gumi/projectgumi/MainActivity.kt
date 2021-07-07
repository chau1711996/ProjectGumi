package com.gumi.projectgumi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.gumi.miniproject.adapter.ViewPagerFragmentAdapter
import com.gumi.projectgumi.data.model.Product
import com.gumi.projectgumi.databinding.ActivityMainBinding
import com.gumi.projectgumi.ui.account.ProfileActivity
import com.gumi.projectgumi.ui.login.LoginActivity
import com.gumi.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.gumi.projectgumi.ui.splash.OnBordingActivity
import com.gumi.projectgumi.viewmodel.CartViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.material.tabs.TabLayoutMediator
import com.gumi.gumiproject8.utils.setVisible
import com.gumi.projectgumi.ui.account.SubsActivity
import com.gumi.projectgumi.utils.BillingSubcribe
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var myAdapter: ViewPagerFragmentAdapter? = null
    private val cartViewModel by viewModel<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        init()
        initBannerAds()
        initSubcribe()
    }

    private fun initSubcribe() {
        BillingSubcribe.getInstance(TAG, this).isSubscribe.observe(this){
            it?.let {
                binding?.adView?.setVisible(!it)
            }
        }
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

    private fun initBannerAds(){
        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()
        binding?.adView?.apply {
            loadAd(adRequest)
            adListener = object : AdListener(){
                override fun onAdClicked() {
                    super.onAdClicked()
                    Toast.makeText(this@MainActivity, "Clicked banner", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun goToFragment(position: Int) {
        binding?.apply {
            viewPager.currentItem = position
        }
    }

    fun loginProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    fun loginSubs() {
        val intent = Intent(this, SubsActivity::class.java)
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
        const val TAG = "LogMainActivity"
    }

}