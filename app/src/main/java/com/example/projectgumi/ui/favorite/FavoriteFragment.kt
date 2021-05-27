package com.example.projectgumi.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectgumi.R
import com.example.projectgumi.adapter.ProductItemAdapter
import com.example.projectgumi.databinding.FragmentFavoriteBinding
import com.example.projectgumi.utils.Utils.TYPE_FAVORITE
import com.example.projectgumi.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var productAdapter: ProductItemAdapter
    private val viewModel by viewModel<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.bind(
            inflater.inflate(
                R.layout.fragment_favorite,
                container,
                false
            )
        )
        binding.apply {
            lifecycleOwner = this@FavoriteFragment
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productAdapter = ProductItemAdapter(TYPE_FAVORITE) { clickFavorite(it) }

        binding.apply {
            adapterProduct = productAdapter
        }

        viewModel.dataProduct.observe(requireActivity()){
            productAdapter.submitList(it)
        }

    }

    private fun clickFavorite(productId: String) {

    }

}