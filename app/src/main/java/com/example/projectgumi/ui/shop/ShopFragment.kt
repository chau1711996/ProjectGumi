package com.example.projectgumi.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.projectgumi.R
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.databinding.FragmentShopBinding
import com.example.projectgumi.viewmodel.ShopViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopFragment : Fragment() {

    private lateinit var binding: FragmentShopBinding
    private val model by viewModel<ShopViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val images = listOf<ImageSlideModel>(
            ImageSlideModel("0","https://i.pinimg.com/originals/9d/60/22/9d6022f153768025ad37f51d89d29ece.jpg"),
            ImageSlideModel("1","https://wall.vn/wp-content/uploads/2020/03/hinh-nen-dep-may-tinh-1.jpg"),
            ImageSlideModel("2","https://maytinhvui.com/wp-content/uploads/2020/11/hinh-nen-may-tinh-4k-game-min.jpg"),
            ImageSlideModel("3","https://pdp.edu.vn/wp-content/uploads/2021/01/hinh-nen-4k-tuyet-dep-cho-may-tinh.jpg"),
            ImageSlideModel("4","https://luongsport.com/wp-content/uploads/2020/10/1039991.jpg")
        )

        val slideAdapter = SlideAdapter(){

        }

        slideAdapter.submitList(images)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop, container, false)
        binding.model = model
        binding.lifecycleOwner = this
        binding.apply {

            viewPager.adapter = slideAdapter

            TabLayoutMediator(tabLayout, viewPager){tab, pos ->

            }.attach()
        }

        // Inflate the layout for this fragment
        return binding.root
    }
}