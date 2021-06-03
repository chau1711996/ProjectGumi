package com.example.projectgumi.ui.explore

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gumiproject8.utils.hide
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CateloryAdapter
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.databinding.FragmentExploreBinding
import com.example.projectgumi.utils.Utils.TYPE_EXPLORE
import com.example.projectgumi.utils.Utils.showFragmentById
import com.example.projectgumi.viewmodel.ExploreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExploreFragment : Fragment() {

    private lateinit var binding: FragmentExploreBinding
    private val model by viewModel<ExploreViewModel>()
    private lateinit var exploreAdapter: CateloryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.bind(inflater.inflate(R.layout.fragment_explore, container, false))
        binding.apply {
            lifecycleOwner = this@ExploreFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.loadCatelory()
        exploreAdapter = CateloryAdapter(TYPE_EXPLORE){clickCatelory(it)}

        binding.apply {
            layoutHead.imageBack.hide()
            layoutHead.imageRight.hide()
            layoutHead.textCateloryName.text = "Find Product"
            adapterExplore = exploreAdapter
        }

        model.dataCategory.observe(requireActivity()){
            it?.let {
                exploreAdapter.submitList(it)
            }
        }
    }

    private fun clickCatelory(catelory: Catelory) {
        showFragmentById(activity, ExploreDetailFragment.newInstance(catelory.cateloryId, catelory.name))
    }

}