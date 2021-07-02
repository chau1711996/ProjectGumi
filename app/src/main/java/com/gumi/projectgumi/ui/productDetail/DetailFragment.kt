package com.gumi.projectgumi.ui.productDetail

import android.os.Bundle
import android.widget.Toast
import coil.load
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.ImagesProductAdapter
import com.gumi.projectgumi.base.BaseFragment
import com.gumi.projectgumi.databinding.FragmentDetailBinding
import com.gumi.projectgumi.utils.Utils
import com.gumi.projectgumi.viewmodel.DetailProductViewModel
import com.gumi.projectgumi.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.gumi.gumiproject8.utils.setVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val PRODUCT_ID = "PRODUCT_ID"

class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private var productId: Int? = null

    override val layoutResource: Int
        get() = R.layout.fragment_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt(PRODUCT_ID)
        }
    }

    private val loginViewModel by viewModel<LoginViewModel>()
    private val detailViewModel by viewModel<DetailProductViewModel>()
    private lateinit var adapterImage: ImagesProductAdapter

    override fun viewCreated() {
        adapterImage = ImagesProductAdapter()

        initFunction()

        productId?.let {
            detailViewModel.loadDataImages(it)
            detailViewModel.loadDataProduct(it)
            detailViewModel.checkFavorite(it, currentUser?.uid ?: "")
        }

        detailViewModel.statusCheckFavorite.observe(requireActivity()){
            it?.let {
                if(it.equals(Utils.SUCCESS)){
                    binding.imageFavorite.load(R.drawable.ic_favorite_true)
                }else{
                    binding.imageFavorite.load(R.drawable.ic_favorite)
                }
            }
        }

        detailViewModel.dataImages.observe(requireActivity()) {
            adapterImage.submitList(it)
        }

        detailViewModel.statusInsert.observe(requireActivity()) {
            if (it) {
                Toast.makeText(context, "insert cart success", Toast.LENGTH_SHORT).show()
                activity?.apply {
                    supportFragmentManager.popBackStack()
                }
            }
        }

        detailViewModel.statusFavorite.observe(requireActivity()) {
            it?.let {
                when (it) {
                    Utils.INSERT -> {
                        binding.imageFavorite.load(R.drawable.ic_favorite_true)
                    }
                    Utils.DELETE -> {
                        binding.imageFavorite.load(R.drawable.ic_favorite)
                    }
                    else -> Toast.makeText(requireContext(), "failed favorite", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }

    private fun initFunction() {
        var check = false
        binding.apply {
            model = detailViewModel
            viewPager.adapter = adapterImage
            TabLayoutMediator(tabLayout, viewPager) { _, _ ->

            }.attach()
            imageLeft.setOnClickListener {
                activity?.apply {
                    supportFragmentManager.popBackStack()
                }
            }
            imageRight.setOnClickListener {
                activity?.apply {
                    supportFragmentManager.popBackStack()
                }
                (context as MainActivity).goToFragment(MainActivity.Cart)
            }
            imageArrowShowProductDetail.setOnClickListener {
                textProductDescriptionDetail.setVisible(check)
                check = !check
            }
            imageFavorite.setOnClickListener {
                if (currentUser == null) {
                    (context as MainActivity).loginActivity()
                }
                currentUser?.let {
                    loginViewModel.checkUser(it.uid)
                }
            }
        }
        loginViewModel.status.observe(requireActivity()) {
            it?.let {
                when (it) {
                    "phone" -> {
                        (context as MainActivity).loginPhoneNumber()
                    }
                    "profile" -> {
                        (context as MainActivity).loginProfile()
                    }
                    "login" -> {
                        currentUser?.let {
                            detailViewModel.insertFavorite(it.uid)
                        }
                    }
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(productId: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(PRODUCT_ID, productId)
                }
            }
    }


}