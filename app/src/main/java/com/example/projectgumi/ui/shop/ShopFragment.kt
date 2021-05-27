package com.example.projectgumi.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CateloryAdapter
import com.example.projectgumi.adapter.ProductItemAdapter
import com.example.projectgumi.adapter.SlideAdapter
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.databinding.FragmentShopBinding
import com.example.projectgumi.utils.Utils.TYPE_SHOP
import com.example.projectgumi.viewmodel.ShopViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment : Fragment() {

    private lateinit var slideAdapter: SlideAdapter
    private lateinit var exclusiveAdapter: ProductItemAdapter
    private lateinit var bestSellingAdapter: ProductItemAdapter
    private lateinit var cateloryAdapter: CateloryAdapter
    private lateinit var productAdapter: ProductItemAdapter
    private lateinit var binding: FragmentShopBinding
    private val model by viewModel<ShopViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false)
        binding.model = model
        binding.lifecycleOwner = this

        init()



        model.loadImage()
        model.loadExclusive()
        model.loadBestSelling()
        model.loadCatelory()
        model.loadProductCatelory("0")

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun init() {
        slideAdapter = SlideAdapter { clickSlideShow(it) }

        exclusiveAdapter = ProductItemAdapter(TYPE_SHOP) { clickExclusive(it) }

        bestSellingAdapter = ProductItemAdapter(TYPE_SHOP) { clickBestSelling(it) }

        cateloryAdapter = CateloryAdapter (TYPE_SHOP){ clickCatelory(it) }

        productAdapter = ProductItemAdapter(TYPE_SHOP) { clickProduct(it) }

        initViewPager()


        initAdapter()

        model.imageSlideShow.observe(requireActivity()) {
            it?.let {
                slideAdapter.submitList(it)
                autoSlideShow(it)
            }
        }
        model.dataExclusive.observe(requireActivity()) {
            it?.let {
                exclusiveAdapter.submitList(it)
            }
        }
        model.dataBestSelling.observe(requireActivity()) {
            it?.let {
                bestSellingAdapter.submitList(it)
            }
        }
        model.dataCategory.observe(requireActivity()){
            it?.let{
                cateloryAdapter.submitList(it)
            }
        }
        model.dataProduct.observe(requireActivity()){
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

            TabLayoutMediator(tabLayout, viewPager) { tab, pos ->

            }.attach()

        }
    }

    private fun clickProduct(productId: String) {

    }

    private fun clickExclusive(productId: String) {

    }

    private fun clickSlideShow(productId: String) {

    }

    private fun clickBestSelling(productId: String) {

    }

    private fun clickCatelory(cateloryId: String) {
        model.loadProductCatelory(cateloryId)
    }

    private fun autoSlideShow(list: MutableList<ImageSlideModel>) {
        GlobalScope.launch(Dispatchers.Main) {
            var index = 0
            while (true) {
                if (index < list.size) {
                    binding.viewPager.setCurrentItem(index, true)
                    index++
                    delay(2000)
                } else {
                    index = 0
                }

            }
        }
    }

}