package com.gumi.projectgumi.ui.account

import android.content.Intent
import coil.load
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.AccountAdapter
import com.gumi.projectgumi.base.BaseFragment
import com.gumi.projectgumi.databinding.FragmentAccountBinding
import com.gumi.projectgumi.ui.login.LoginActivity
import com.gumi.projectgumi.ui.order.OrdersFragment
import com.gumi.projectgumi.utils.Utils
import com.gumi.projectgumi.viewmodel.AccountViewModel
import com.gumi.projectgumi.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.gumi.gumiproject8.utils.hide
import com.gumi.gumiproject8.utils.show
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : BaseFragment<FragmentAccountBinding>() {

    override val layoutResource: Int
        get() = R.layout.fragment_account

    private val accountModel by viewModel<AccountViewModel>()
    private lateinit var accountAdapter: AccountAdapter
    private lateinit var googleSignInClient: GoogleSignInClient
    private val loginViewModel by viewModel<LoginViewModel>()

    override fun viewCreated() {

        accountAdapter = AccountAdapter { clickAccount(it) }
        binding.adapterAccount = accountAdapter

        if (Firebase.auth.currentUser == null) {
            binding.apply {
                buttonLogout.hide()
                buttonLogin.show()
                buttonLogin.setOnClickListener {
                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    (context as MainActivity).overridePendingTransition(
                        R.anim.slide_in_right,
                        R.anim.slide_out_left
                    )
                    (context as MainActivity).finish()
                }
            }
        } else {
            currentUser?.let {
                loginViewModel.checkUser(it.uid)
            }
        }

        loginViewModel.status.observe(this) {
            it?.let {
                binding.buttonLogout.hide()
                binding.buttonLogin.show()
                when (it) {
                    "phone" -> {
                        binding.buttonLogin.setOnClickListener {
                            (context as MainActivity).loginPhoneNumber()
                        }
                    }
                    "profile" -> {
                        binding.buttonLogin.setOnClickListener {
                            (context as MainActivity).loginProfile()
                        }
                    }
                    "login" -> {
                        checkHasUser()
                    }
                }
            }
        }

    }

    private fun checkHasUser() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        currentUser?.let {
            accountModel.getUserById(it.uid)
        }

        binding.apply {
            buttonLogin.hide()
            buttonLogout.show()
            buttonLogout.setOnClickListener {
                googleSignInClient.signOut()
                Firebase.auth.signOut()
                (context as MainActivity).goToFragment(MainActivity.SHOP)
            }
        }
        accountModel.dataAccount.observe(requireActivity()) {
            accountAdapter.submitList(it)
        }
        accountModel.dataUser.observe(requireActivity()) {
            it?.let {
                binding.apply {
                    imageAvt.load(currentUser?.photoUrl)
                    textName.text = it.userName
                    textPhone.text = it.phoneNumber
                }
            }
        }
    }

    private fun clickAccount(name: String) {
        when(name){
            "Profile" -> (context as MainActivity).loginProfile()
            "Orders" -> Utils.showDialogFragment(activity, OrdersFragment(), OrdersFragment.TAG)
            "About" -> (context as MainActivity).loginOnBording()
        }
    }
}