package com.adashi.escrow.ui.settings.bankaccounts

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentWithdrawalBanksBinding
import com.adashi.escrow.models.addbank.Account
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.repository.SettingsRepository
import com.adashi.escrow.ui.addbank.AddBankDialogFragment
import com.adashi.escrow.ui.addbank.verifybvn.VerifyBvnDialogFragment
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import com.adashi.escrow.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.utils.App
import ng.adashi.utils.DataState

class WithdrawalBanksFragment : BaseFragment<FragmentWithdrawalBanksBinding>(R.layout.fragment_withdrawal_banks){



    private fun initAdapter(data: List<Account>) {
        val adapter = GetAllBanksAdapter { d ->
            //Toast.makeText(requireContext(), d.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)
    }


    override fun start() {
        super.start()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = WithdrawalBankFactory(application, SettingsRepository(network))

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            WithdrawalBanksViewModel::class.java)

        viewModel.getAllBanks()

        viewModel.allBanks.observe(this,{ response ->

            when (response) {
                is DataState.Success<GetAllBanksResponse> -> {

                    val data = response.data.data.accounts
                    initAdapter(data)

                }
                is DataState.Error -> {

                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {

                    if (response.code!! == 403){
                        App.token = null

                        showSnackBar("token expired, please login again")
                        findNavController().popBackStack()
                    }
                    else{

                    }
                }
                DataState.Loading -> {

                }
            }

        })

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )


            binding.addBank.setOnClickListener {

                val bvn = prefs.getString(SessionManager.USER_BVN, "")
                //Toast.makeText(requireContext(), bvn, Toast.LENGTH_SHORT).show()

                if (bvn?.length!! < 2 || bvn.isNullOrBlank()){
                    var fr = VerifyBvnDialogFragment{ click ,textbnv , phone ->
                        when(click){
                            0 ->{
                                //showSnackBar("$textbnv  $phone")
                                findNavController().navigate(WithdrawalBanksFragmentDirections.actionWithdrawalBanksFragmentToVerifyBnvOtpFragment(textbnv,phone))
                            }
                        }
                    }
                    fr.show(requireActivity().supportFragmentManager,"added fragm")

                }else{

                var fr = AddBankDialogFragment{ id, message ->
                    when(id){
                        1 ->{
                            viewModel.getAllBanks()
                            showSnackBar(message)
                        }
                    }
                    viewModel.getAllBanks()
                }
                fr.show(requireActivity().supportFragmentManager,"added fragm")

                    viewModel.getAllBanks()
            }
        }


    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}