package com.gumi.projectgumi.ui.explore

import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.CateloryAdapter
import com.gumi.projectgumi.base.BaseFragment
import com.gumi.projectgumi.data.model.Catelory
import com.gumi.projectgumi.databinding.FragmentExploreBinding
import com.gumi.projectgumi.utils.Utils.TYPE_EXPLORE
import com.gumi.projectgumi.utils.Utils.showFragmentById
import com.gumi.projectgumi.viewmodel.ExploreViewModel
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