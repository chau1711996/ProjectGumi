package com.example.projectgumi.ui.favorite

import android.widget.Toast
import com.example.projectgumi.R
import com.example.projectgumi.adapter.FavoriteAdapter
import com.example.projectgumi.base.BaseFragment
import com.example.projectgumi.data.model.FavoriteModel
import com.example.projectgumi.databinding.FragmentFavoriteBinding
import com.example.projectgumi.ui.cart.CartFragment
import com.example.projectgumi.ui.productDetail.DetailFragment
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.viewmodel.CartViewModel
import com.example.projectgumi.viewmodel.FavoriteViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private lateinit var favoriteAdaper: FavoriteAdapter
    private val viewModel by viewModel<FavoriteViewModel>()
    private val cartViewModel by viewModel<CartViewModel>()
    private var listFavorite = mutableListOf<FavoriteModel>()

    override val layoutResource: Int
        get() = R.layout.fragment_favorite

    override fun viewCreated() {
        favoriteAdaper = FavoriteAdapter{ clickFavorite(it) }

        val dialog = Utils.showProgressBar(requireContext(), "Loading...")

        binding.apply {
            adapterProduct = favoriteAdaper
            btnFavorite.setOnClickListener {
                cartViewModel.insertCartByFavorite(listFavorite)
            }
            imageRefresh.setOnClickListener {
                loadData()
            }
        }

        loadData()

        viewModel.dataProduct.observe(requireActivity()){
            it?.let {
                listFavorite = it
                favoriteAdaper.submitList(listFavorite)
            }
        }

        cartViewModel.statusAddAllCart.observe(requireActivity()){
            it?.let {
                if(it){
                    dialog.dismiss()
                    Toast.makeText(requireContext(), "insert add all to cart success", Toast.LENGTH_SHORT)
                        .show()
//                    val cart = CartFragment()
//                    cart.loadData()
                }else
                    dialog.show()
            }
        }
    }

    fun loadData(){
        currentUser?.let {
            viewModel.loadData(it.uid)
        }
    }

    private fun clickFavorite(productId: Int) {
        Utils.showFragmentById(activity, DetailFragment.newInstance(productId))
    }

}