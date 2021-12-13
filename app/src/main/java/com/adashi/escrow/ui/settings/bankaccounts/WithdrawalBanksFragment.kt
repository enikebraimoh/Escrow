package com.adashi.escrow.ui.settings.bankaccounts

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentWithdrawalBanksBinding
import com.adashi.escrow.models.addbank.Account
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import com.adashi.escrow.models.shipmentpatch.PatchShipingStatus
import com.adashi.escrow.models.shipmentpatch.Transaction
import com.adashi.escrow.repository.SettingsRepository
import com.adashi.escrow.ui.addbank.AddBankDialogFragment
import com.adashi.escrow.ui.addbank.AddBankFactory
import com.adashi.escrow.ui.addbank.AddBankViewModel
import com.adashi.escrow.ui.addbank.BanksAdapter
import com.adashi.escrow.ui.addbank.verifybvn.VerifyBvnDialogFragment
import com.adashi.escrow.ui.auth.login.LoginFragmentDirections
import com.adashi.escrow.ui.dashboard.ShowTransactionDetailsDialogFragment
import com.adashi.escrow.ui.dashboard.TransactionsAdapter
import com.adashi.escrow.ui.settings.bankaccounts.mono.MonoAccountCode
import com.adashi.escrow.ui.settings.bankaccounts.mono.MonoAccountResponse
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import mono.connect.widget.ConnectWidget
import mono.connect.widget.ConnectedAccount
import mono.connect.widget.EventListener

class WithdrawalBanksFragment :
    BaseFragment<FragmentWithdrawalBanksBinding>(R.layout.fragment_withdrawal_banks),
    EventListener {

    var data = false
    lateinit var viewModel: WithdrawalBanksViewModel

    private lateinit var connectWidget: ConnectWidget

    private fun initAdapter(data: MutableList<Account>) {
        val adapter = GetAllBanksAdapter { d ->

        }

        binding.recyclerView.adapter = adapter
        adapter.submitList(data)
    }

    override fun start() {
        super.start()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory =
            WithdrawalBankFactory(application, SettingsRepository(network))

        viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            WithdrawalBanksViewModel::class.java
        )

        viewModel.navigateToLogin.observe(this,{
            if (!it){
                data = false
            }
        })

        viewModel.getAllBanks()

        viewModel.monoAcccountResponse.observe(this, { response ->
            when (response) {
                is DataState.Success<MonoAccountResponse> -> {
                  if (data){
                      Log.d("MONOTESTING", "success was called")
                      binding.progressBar2.visibility = View.INVISIBLE
                      binding.progressTexxt.visibility = View.INVISIBLE
                      viewModel.navigateToLoginDone()
                      viewModel.getAllBanks()

                    /*  val data = response.data.data
                      val aza = Account(
                          data.__v,
                          data._id,
                          data.accountName,
                          data.accountNumber,
                          data.bankName,
                          data.createdAt,
                          data.updatedAt
                      )
                      var newData = mutableListOf<Account>()
                      newData.add(aza)
                       initAdapter(newData)
                      Log.d("MONOTESTING", response.data.data.toString())
                      showSnackBar(data.toString())*/

                  }
                }
                is DataState.Error -> {
                    Log.d("MONOTESTING", "error was called")
                    showSnackBar("Slow or no Internet Connection")
                    binding.progressBar2.visibility = View.INVISIBLE
                    binding.progressTexxt.visibility = View.INVISIBLE
                }
                is DataState.GenericError -> {
                   if (data){
                       if (response.code!! == 403) {
                           App.token = null
                           binding.progressBar2.visibility = View.INVISIBLE
                           binding.progressTexxt.visibility = View.INVISIBLE
                           showSnackBar("token expired, please login again")
                           findNavController().popBackStack()
                       } else {
                           Log.d("MONOTESTING", "Generic Error was called")
                           Log.d("MONOTESTING", response.error?.message!!)
                           viewModel.navigateToLoginDone()
                           showSnackBar(response.error?.message)
                           binding.progressBar2.visibility = View.INVISIBLE
                           binding.progressTexxt.visibility = View.INVISIBLE
                       }
                   }
                }
                DataState.Loading -> {
                    binding.progressBar2.visibility = View.VISIBLE
                    binding.progressTexxt.visibility = View.VISIBLE
                }
            }

        })

        viewModel.allBanks.observe(this, { response ->
            when (response) {
                is DataState.Success<GetAllBanksResponse> -> {
                    binding.recyclerViewProgressBar.visibility = View.INVISIBLE
                    val data = response.data.data.accounts
                    initAdapter(data)


                }
                is DataState.Error -> {
                    //binding.recyclerViewProgressBar.visibility = View.INVISIBLE
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {

                    if (response.code!! == 403) {
                        App.token = null
                        binding.recyclerViewProgressBar.visibility = View.INVISIBLE
                        showSnackBar("token expired, please login again")
                        findNavController().popBackStack()
                    } else {
                        binding.recyclerViewProgressBar.visibility = View.INVISIBLE
                    }
                }
                DataState.Loading -> {
                    binding.recyclerViewProgressBar.visibility = View.VISIBLE
                }
            }

        })


        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        // MonoConfiguration
        setup()


    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun setup() {

        val key = getString(R.string.connect_public_key)
        connectWidget = ConnectWidget(requireContext(), key)
        connectWidget.setListener(this)

        binding.addBank.setOnClickListener {
            connectWidget.show()
        }
    }

    override fun onClose() {
        Toast.makeText(requireContext(), "widget closed", Toast.LENGTH_LONG).show()
    }

    override fun onSuccess(account: ConnectedAccount?) {
        Toast.makeText(requireContext(), "Account successfully connected", Toast.LENGTH_LONG).show()
        Log.d("MONOTESTING", account?.code.toString())
        viewModel.monoVerifyBank(MonoAccountCode(account?.code.toString()))
        data = true
        viewModel.navigateButtonClicked()
    }

}