package com.example.projectgumi.ui.productDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.gumiproject8.utils.setVisible
import com.example.projectgumi.R
import com.example.projectgumi.adapter.ImagesProductAdapter
import com.example.projectgumi.databinding.FragmentDetailBinding
import com.example.projectgumi.viewmodel.DetailProductViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val PRODUCT_ID = "PRODUCT_ID"

class DetailFragment : Fragment() {
    private var productId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productId = it.getInt(PRODUCT_ID)
        }
    }
    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel by viewModel<DetailProductViewModel>()
    private lateinit var adapterImage: ImagesProductAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_detail, container, false)
        binding.lifecycleOwner = this
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterImage = ImagesProductAdapter()

        initFunction()

        productId?.let {
            detailViewModel.loadDataImages(it)
            detailViewModel.loadDataProduct(it)
        }

        detailViewModel.dataImages.observe(requireActivity()){
            adapterImage.submitList(it)
        }

        detailViewModel.statusInsert.observe(requireActivity()){
            if(it){
                Toast.makeText(context, "insert cart success", Toast.LENGTH_SHORT).show()
                activity?.apply {
                    supportFragmentManager.popBackStack()
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
            imageArrowShowProductDetail.setOnClickListener {
                textProductDescriptionDetail.setVisible(check)
                check = !check
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