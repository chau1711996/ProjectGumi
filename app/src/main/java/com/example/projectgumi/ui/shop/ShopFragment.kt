package com.example.projectgumi.ui.shop

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager2.widget.ViewPager2
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CateloryAdapter
import com.example.projectgumi.adapter.ProductItemAdapter
import com.example.projectgumi.adapter.SlideAdapter
import com.example.projectgumi.base.BaseFragment
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.databinding.FragmentShopBinding
import com.example.projectgumi.ui.explore.ExploreDetailFragment
import com.example.projectgumi.ui.productDetail.DetailFragment
import com.example.projectgumi.ui.search.SearchProductFragment
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.TYPE_SHOP
import com.example.projectgumi.viewmodel.ShopViewModel
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.tabs.TabLayoutMediator
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
    lateinit var adLoader: AdLoader

    override fun viewCreated() {
        binding.apply {
            model = shopViewModel
            layoutBestSelling.textSeeAll.setOnClickListener {
                Utils.showFragmentById(activity, ShopSeeAllFragment.newInstance(ShopSeeAllFragment.TYPE_BESTSELLING))
            }
            layoutExclusive.textSeeAll.setOnClickListener {
                Utils.showFragmentById(activity, ShopSeeAllFragment.newInstance(ShopSeeAllFragment.TYPE_EXCLUSIVE))
            }
            layoutCatelory.textSeeAll.setOnClickListener {
                (context as MainActivity).goToFragment(MainActivity.Explore)
            }
            layoutHead.layoutSearch.layoutSearchStore.setOnClickListener {
                Utils.showDialogFragment(activity, SearchProductFragment(), SearchProductFragment.TAG)
            }
        }

        init()

        checkLogin()

        shopViewModel.loadImageSlideShow()
        shopViewModel.loadExclusive()
        shopViewModel.loadBestSelling()
        shopViewModel.loadCatelory()
    }

    private fun checkLogin(){
        currentUser?.let {
            shopViewModel.getUserById(it.uid)
        }
    }

    private fun init() {
        slideAdapter = SlideAdapter { id, name ->
            clickSlideShow(id, name)
        }

        exclusiveAdapter = ProductItemAdapter{ clickExclusive(it) }

        bestSellingAdapter = ProductItemAdapter{ clickBestSelling(it) }

        cateloryAdapter = CateloryAdapter(TYPE_SHOP) { clickCatelory(it.cateloryId) }

        productAdapter = ProductItemAdapter{ clickProduct(it) }

        initViewPager()

        initAdapter()

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
        shopViewModel.dataUser.observe(requireActivity()){
            it?.let {
                binding.apply {
                    layoutHead.textTitle.text = it.street
                }
            }
        }
    }

    private fun initAdsNative(){

        MobileAds.initialize(requireContext()){}

        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd {
                // Show the ad.
                if (adLoader.isLoading) {
                    // The AdLoader is still loading ads.
                    // Expect more adLoaded or onAdFailedToLoad callbacks.
                } else {
                    // The AdLoader has finished loading ads.
                    val style = NativeTemplateStyle.Builder()
                        .build()
                    binding.myTemplate.apply {
                        setNativeAd(it)
                        setStyles(style)
                    }
                }
                if (isDestroyed) {
                    it.destroy()
                    return@forNativeAd
                }
            }.build()

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

    class SlideShowAuto(list: MutableList<ImageSlideModel>, view: ViewPager2) : LifecycleObserver {
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



}