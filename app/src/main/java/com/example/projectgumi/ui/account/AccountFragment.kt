package com.example.projectgumi.ui.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.projectgumi.R
import com.example.projectgumi.adapter.AccountAdapter
import com.example.projectgumi.databinding.FragmentAccountBinding
import com.example.projectgumi.viewmodel.AccountViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private val accountModel by viewModel<AccountViewModel>()
    private lateinit var accountAdapter: AccountAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.bind(inflater.inflate(R.layout.fragment_account, container, false))
        binding.apply {
            lifecycleOwner = this@AccountFragment
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        accountAdapter = AccountAdapter { clickAccount(it) }
        binding.apply {
            adapterAccount = accountAdapter
        }
        accountModel.dataAccount.observe(requireActivity()){
            accountAdapter.submitList(it)
        }
    }

    private fun clickAccount(name: String) {

    }
}