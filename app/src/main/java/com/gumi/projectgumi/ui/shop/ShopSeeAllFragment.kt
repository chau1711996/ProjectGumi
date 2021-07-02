package com.gumi.projectgumi.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gumi.gumiproject8.utils.hide
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.ProductItemAdapter
import com.gumi.projectgumi.databinding.FragmentShopSeeAllBinding
import com.gumi.projectgumi.ui.productDetail.DetailFragment
import com.gumi.projectgumi.utils.Utils
import com.gumi.projectgumi.viewmodel.ShopSeeAllViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val TYPE_SEE_ALL = "TYPE_SEE_ALL"

class ShopSeeAllFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(TYPE_SEE_ALL)
        }
    }

    private lateinit var binding: FragmentShopSeeAllBinding
    private val shopSeeViewModel by viewModel<ShopSeeAllViewModel>()
    private lateinit var productItemAdapter: ProductItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopSeeAllBinding.bind(inflater.inflate(R.layout.fragment_shop_see_all, container, false))
        binding.apply {
            lifecycleOwner = this@ShopSeeAllFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        type?.let {
            shopSeeViewModel.loadDataFromType(it)
            binding.layoutHead.textCateloryName.text = it
        }

        productItemAdapter = ProductItemAdapter{clickShopSeeAll(it)}

        binding.apply {
            layoutHead.layoutSearch.layoutSearchStore.hide()
            layoutHead.imageRight.hide()
            adapterProduct = productItemAdapter
            layoutHead.imageBack.setOnClickListener {
                activity?.apply {
                    supportFragmentManager.popBackStack()
                }
            }
        }

        shopSeeViewModel.data.observe(requireActivity()){
            it?.let {
                productItemAdapter.submitList(it)
            }
        }
    }

    private fun clickShopSeeAll(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    companion object {
        const val TYPE_BESTSELLING = "Best Selling"
        const val TYPE_EXCLUSIVE = "Exclusive Offer"
        @JvmStatic
        fun newInstance(type: String) =
            ShopSeeAllFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE_SEE_ALL, type)
                }
            }
    }
}