package com.gumi.projectgumi.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.ProductItemAdapter
import com.gumi.projectgumi.data.model.Product
import com.gumi.projectgumi.databinding.FragmentSearchProductBinding
import com.gumi.projectgumi.ui.productDetail.DetailFragment
import com.gumi.projectgumi.utils.Utils
import com.gumi.projectgumi.viewmodel.SearchStoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchProductFragment : DialogFragment() {

    private lateinit var binding: FragmentSearchProductBinding
    private val viewModel by viewModel<SearchStoreViewModel>()
    private lateinit var productAdapter: ProductItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
        binding = FragmentSearchProductBinding.bind(
            inflater.inflate(
                R.layout.fragment_search_product,
                container,
                false
            )
        )
        binding.lifecycleOwner = this
        binding.model = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productAdapter = ProductItemAdapter { clickProduct(it) }
        binding.apply {
            adapterProduct = productAdapter
            imageLeft.setOnClickListener {
                dismiss()
            }
        }

        viewModel.text.observe(requireActivity()) {
            it?.let {
                if (it.isNotEmpty())
                    viewModel.getProductByName(it)
                else{
                    val empty = mutableListOf<Product>()
                    productAdapter.submitList(empty)
                }
            }
        }

        viewModel.dataProduct.observe(requireActivity()) {
            it?.let {
                productAdapter.submitList(it)
            }
        }
    }

    private fun clickProduct(productId: Int) {
        dismiss()
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    companion object {
        const val TAG = "SearchProductFragment"
    }
}