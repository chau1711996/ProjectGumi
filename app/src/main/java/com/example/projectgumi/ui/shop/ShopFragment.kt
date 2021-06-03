package com.example.projectgumi.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager2.widget.ViewPager2
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CateloryAdapter
import com.example.projectgumi.adapter.ProductItemAdapter
import com.example.projectgumi.adapter.SlideAdapter
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.databinding.FragmentShopBinding
import com.example.projectgumi.ui.productDetail.DetailFragment
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.TYPE_SHOP
import com.example.projectgumi.viewmodel.ShopViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment : Fragment() {

    private lateinit var slideAdapter: SlideAdapter
    private lateinit var exclusiveAdapter: ProductItemAdapter
    private lateinit var bestSellingAdapter: ProductItemAdapter
    private lateinit var cateloryAdapter: CateloryAdapter
    private lateinit var productAdapter: ProductItemAdapter
    private lateinit var binding: FragmentShopBinding
    private val shopViewModel by viewModel<ShopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false)
        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            model = shopViewModel
            layoutBestSelling.textSeeAll.setOnClickListener {
                Utils.showFragmentById(activity, ShopSeeAllFragment.newInstance(ShopSeeAllFragment.TYPE_BESTSELLING))
            }
            layoutExclusive.textSeeAll.setOnClickListener {
                Utils.showFragmentById(activity, ShopSeeAllFragment.newInstance(ShopSeeAllFragment.TYPE_EXCLUSIVE))
            }
        }

        init()

        shopViewModel.loadImageSlideShow()
        shopViewModel.loadExclusive()
        shopViewModel.loadBestSelling()
        shopViewModel.loadCatelory()
    }

    private fun init() {
        slideAdapter = SlideAdapter { clickSlideShow(it) }

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

    //catelory
    private fun clickProduct(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    private fun clickExclusive(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    private fun clickSlideShow(productId: Int) {

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
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        fun start() {
            job.start()
        }
        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        fun stop() {
            job.cancel()
        }
    }

    private fun autoSlideShow(list: MutableList<ImageSlideModel>) {
        val slideShow = SlideShowAuto(list, binding.viewPager)
        lifecycle.addObserver(slideShow)
    }

}