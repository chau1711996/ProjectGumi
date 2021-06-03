package com.example.projectgumi.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gumiproject8.utils.hide
import com.example.projectgumi.R
import com.example.projectgumi.adapter.ProductItemAdapter
import com.example.projectgumi.databinding.FragmentExploreDetailBinding
import com.example.projectgumi.ui.productDetail.DetailFragment
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.TYPE_SHOP
import com.example.projectgumi.viewmodel.ExploreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CATELORY_ID = "CATELORY_ID"
private const val CATELORY_NAME = "CATELORY_NAME"

class ExploreDetailFragment : Fragment() {
    private var cateloryId: Int? = null
    private var cateloryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cateloryId = it.getInt(CATELORY_ID)
            cateloryName = it.getString(CATELORY_NAME)
        }
    }

    private lateinit var binding: FragmentExploreDetailBinding
    private val model by viewModel<ExploreViewModel>()
    private lateinit var exploreDetailAdapter: ProductItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreDetailBinding.bind(inflater.inflate(R.layout.fragment_explore_detail, container, false))
        binding.apply {
            lifecycleOwner = this@ExploreDetailFragment
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cateloryId?.let {
            model.loadProductByCateloryId(it)
        }

        exploreDetailAdapter = ProductItemAdapter(TYPE_SHOP) { clickProduct(it) }

        binding.apply {
            layoutHead.layoutSearch.layoutSearchStore.hide()
            cateloryName?.let {
                layoutHead.textCateloryName.text = it
            }
            adapterProduct = exploreDetailAdapter
            layoutHead.imageBack.setOnClickListener {
                activity?.apply {
                    supportFragmentManager.popBackStack()
                }
            }
        }

        model.dataProduct.observe(requireActivity()){
            it?.let {
                exploreDetailAdapter.submitList(it)
            }
        }
    }

    private fun clickProduct(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    companion object {
        @JvmStatic
        fun newInstance(cateloryId: Int, cateloryName: String) =
            ExploreDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(CATELORY_ID, cateloryId)
                    putString(CATELORY_NAME, cateloryName)
                }
            }
    }
}