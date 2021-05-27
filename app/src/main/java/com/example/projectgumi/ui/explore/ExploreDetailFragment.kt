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
import com.example.projectgumi.utils.Utils.TYPE_SHOP
import com.example.projectgumi.viewmodel.ExploreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CATELORY_ID = "CATELORY_ID"

class ExploreDetailFragment : Fragment() {
    private var cateloryId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            cateloryId = it.getString(CATELORY_ID)
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

        cateloryId?.let {
            model.loadCateloryById(it)
            model.loadProductById(it)
        }

        exploreDetailAdapter = ProductItemAdapter(TYPE_SHOP) { clickProduct(it) }

        binding.apply {
            layoutHead.layoutSearch.layoutSearchStore.hide()
            adapterProduct = exploreDetailAdapter
            layoutHead.imageBack.setOnClickListener {
                activity?.apply {
                    supportFragmentManager.popBackStack()
                }
            }
        }

        model.resultCatelory.observe(requireActivity()){
            it?.let {
                binding.layoutHead.textCateloryName.text = it.cateloryName
            }
        }

        model.resultProduct.observe(requireActivity()){
            it?.let {
                exploreDetailAdapter.submitList(it)
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun clickProduct(productId: String) {

    }

    companion object {
        @JvmStatic
        fun newInstance(cateloryId: String) =
            ExploreDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(CATELORY_ID, cateloryId)
                }
            }
    }
}