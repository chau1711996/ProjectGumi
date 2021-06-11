package com.example.projectgumi.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gumiproject8.utils.hide
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CateloryAdapter
import com.example.projectgumi.base.BaseFragment
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.databinding.FragmentExploreBinding
import com.example.projectgumi.utils.Utils.TYPE_EXPLORE
import com.example.projectgumi.utils.Utils.showFragmentById
import com.example.projectgumi.viewmodel.ExploreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : BaseFragment<FragmentExploreBinding>() {

    private val model by viewModel<ExploreViewModel>()
    private lateinit var exploreAdapter: CateloryAdapter

    override val layoutResource: Int
        get() = R.layout.fragment_explore

    override fun viewCreated() {
        model.loadCatelory()
        exploreAdapter = CateloryAdapter(TYPE_EXPLORE){clickCatelory(it)}

        binding.apply {
            viewModel = model
            layoutHead.imageBack.hide()
            layoutHead.imageRight.hide()
            layoutHead.layoutSearch.layoutSearchStore.hide()
            layoutHead.textCateloryName.text = "Explore"
            adapterExplore = exploreAdapter
        }

        model.dataCategory.observe(requireActivity()){
            it?.let {
                exploreAdapter.submitList(it)
            }
        }
        model.text.observe(requireActivity()){
            it?.let{
                model.getCateloryByName(it)
            }
        }
    }

    private fun clickCatelory(catelory: Catelory) {
        showFragmentById(activity, ExploreDetailFragment.newInstance(catelory.cateloryId, catelory.name))
    }

}