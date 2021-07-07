package com.gumi.projectgumi.ui.shop

import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager2.widget.ViewPager2
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.CateloryAdapter
import com.gumi.projectgumi.adapter.ProductItemAdapter
import com.gumi.projectgumi.adapter.SlideAdapter
import com.gumi.projectgumi.base.BaseFragment
import com.gumi.projectgumi.data.model.ImageSlideModel
import com.gumi.projectgumi.databinding.FragmentShopBinding
import com.gumi.projectgumi.ui.explore.ExploreDetailFragment
import com.gumi.projectgumi.ui.productDetail.DetailFragment
import com.gumi.projectgumi.ui.search.SearchProductFragment
import com.gumi.projectgumi.utils.Utils
import com.gumi.projectgumi.utils.Utils.TYPE_SHOP
import com.gumi.projectgumi.viewmodel.ShopViewModel
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.*
import com.google.android.material.tabs.TabLayoutMediator
import com.gumi.gumiproject8.utils.setVisible
import com.gumi.projectgumi.utils.BillingSubcribe
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment : BaseFragment<FragmentShopBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_shop

    private lateinit var slideAdapter: SlideAdapter
    private lateinit var exclusiveAdapter: ProductItemAdapter
    private lateinit var bestSellingAdapter: ProductItemAdapter
    private lateinit var cateloryAdapter: CateloryAdapter
    private lateinit var productAdapter: ProductItemAdapter
    private val shopViewModel by viewModel<ShopViewModel>()

    override fun viewCreated() {
        binding.apply {
            model = shopViewModel
            layoutBestSelling.textSeeAll.setOnClickListener {
                Utils.showFragmentById(
                    activity,
                    ShopSeeAllFragment.newInstance(ShopSeeAllFragment.TYPE_BESTSELLING)
                )
            }
            layoutExclusive.textSeeAll.setOnClickListener {
                Utils.showFragmentById(
                    activity,
                    ShopSeeAllFragment.newInstance(ShopSeeAllFragment.TYPE_EXCLUSIVE)
                )
            }
            layoutCatelory.textSeeAll.setOnClickListener {
                (context as MainActivity).goToFragment(MainActivity.Explore)
            }
            layoutHead.layoutSearch.layoutSearchStore.setOnClickListener {
                Utils.showDialogFragment(
                    activity,
                    SearchProductFragment(),
                    SearchProductFragment.TAG
                )
            }
        }

        init()

        checkLogin()

        initSubcribe()

        try {
            shopViewModel.loadImageSlideShow()
            shopViewModel.loadExclusive()
            shopViewModel.loadBestSelling()
            shopViewModel.loadCatelory()
        } catch (e: Exception) {

        }
    }

    private fun initSubcribe() {
        BillingSubcribe.getInstance(TAG, requireContext()).isSubscribe.observe(this) {
            it?.let {
                binding.myTemplate.setVisible(!it)
            }
        }
    }

    private fun checkLogin() {
        currentUser?.let {
            shopViewModel.getUserById(it.uid)
        }
    }

    private fun init() {
        slideAdapter = SlideAdapter { id, name ->
            clickSlideShow(id, name)
        }

        exclusiveAdapter = ProductItemAdapter { clickExclusive(it) }

        bestSellingAdapter = ProductItemAdapter { clickBestSelling(it) }

        cateloryAdapter = CateloryAdapter(TYPE_SHOP) { clickCatelory(it.cateloryId) }

        productAdapter = ProductItemAdapter { clickProduct(it) }

        initViewPager()

        initAdapter()

        initAdsNative()

        shopViewModel.imageSlideShow.observe(requireActivity()) {
            it?.let {
                slideAdapter.submitList(it)
                autoSlideShow(it)
            }
        }
        shopViewModel.dataExclusive.observe(requireActivity()) {
            it?.let {
                exclusiveAdapter.submitList(it)
            }
        }
        shopViewModel.dataBestSelling.observe(requireActivity()) {
            it?.let {
                bestSellingAdapter.submitList(it)
            }
        }
        shopViewModel.dataCategory.observe(requireActivity()) {
            it?.let {
                cateloryAdapter.submitList(it)
                shopViewModel.loadProductByCateloryId(it[0].cateloryId)
            }
        }
        shopViewModel.dataProduct.observe(requireActivity()) {
            it?.let {
                productAdapter.submitList(it)
            }
        }
        shopViewModel.dataUser.observe(requireActivity()) {
            it?.let {
                binding.apply {
                    layoutHead.textTitle.text = it.street
                }
            }
        }
    }

    private fun initAdsNative() {

        MobileAds.initialize(requireContext()) {}

        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd {
                // Show the ad.
                val style = NativeTemplateStyle.Builder()
                    .build()
                binding.myTemplate.apply {
                    setNativeAd(it)
                    setStyles(style)

                }
                if (isDestroyed) {
                    it.destroy()
                    return@forNativeAd
                }
            }
            .withAdListener(object : AdListener() {
                override fun onAdClicked() {
                    super.onAdClicked()
                    Toast.makeText(requireContext(), "Clicked Native ads", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

    }

    private var isDestroyed = false
    override fun onDestroy() {
        super.onDestroy()
        isDestroyed = true
    }

    private fun initAdapter() {
        binding.apply {
            adapterExclusive = exclusiveAdapter
            adapterBestSelling = bestSellingAdapter
            adapterCatelory = cateloryAdapter
            adapterProduct = productAdapter
        }
    }

    private fun initViewPager() {
        binding.apply {

            viewPager.adapter = slideAdapter

            TabLayoutMediator(tabLayout, viewPager) { _, _ ->

            }.attach()

        }
    }


    private fun clickProduct(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    private fun clickExclusive(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    private fun clickSlideShow(cateloryId: Int, name: String) {
        Utils.showFragmentById(
            activity,
            ExploreDetailFragment.newInstance(cateloryId, name)
        )
    }

    private fun clickBestSelling(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    private fun clickCatelory(cateloryId: Int) {
        shopViewModel.loadProductByCateloryId(cateloryId)
    }

    class SlideShowAuto(list: MutableList<ImageSlideModel>, view: ViewPager2) :
        LifecycleObserver {
        var index = 0

        val job = GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                if (index < list.size) {
                    view.setCurrentItem(index, true)
                    index++
                    delay(2000)
                } else {
                    index = 0
                }

            }
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        fun start() {
            job.start()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun stop() {
            job.cancel()
        }
    }

    private fun autoSlideShow(list: MutableList<ImageSlideModel>) {
        val slideShow = SlideShowAuto(list, binding.viewPager)
        lifecycle.addObserver(slideShow)
    }

    companion object {
        const val TAG = "LogShopFragment"
    }
}