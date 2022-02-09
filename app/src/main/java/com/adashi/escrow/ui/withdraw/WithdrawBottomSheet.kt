package ng.adashi.ui.withdraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.WithdrawDialogueSheetBinding
import com.adashi.escrow.models.addbank.Account
import com.adashi.escrow.models.addbank.GetAllBanksResponse
import com.adashi.escrow.models.listofbanks.Bank
import com.adashi.escrow.ui.addbank.BanksAdapter
import com.adashi.escrow.ui.auth.verifyemail.VerifyEmailViewModel
import com.adashi.escrow.ui.withdraw.WithdrawBanksAdapter
import com.adashi.escrow.ui.withdraw.WithdrawalViewModel
import com.adashi.escrow.ui.withdraw.models.WithdrawDetails
import com.adashi.escrow.ui.withdraw.models.WithdrawResponse
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import ng.adashi.ui.password.PasswordBottomSheet
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import ng.adashi.utils.RoundedBottomSheet

@AndroidEntryPoint
class WithdrawBottomSheet(val click : (message : String) -> Unit) : RoundedBottomSheet() {

    lateinit var binding: WithdrawDialogueSheetBinding
    lateinit var bankCode: String
    lateinit var accountNumber: String

    val viewModel: WithdrawalViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.withdraw_dialogue_sheet, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllBanks()

        viewModel.allBanks.observe(this,{ response ->

            when (response) {
                is DataState.Success<GetAllBanksResponse> -> {

                    val banks = response.data.data.accounts

                    val WithdrawBanksAdapter =
                        WithdrawBanksAdapter(requireActivity().applicationContext, banks)

                    binding.spinner.adapter = WithdrawBanksAdapter

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

        viewModel.withdrawResponse.observe(this,{ response ->

            when (response) {
                is DataState.Success<WithdrawResponse> -> {
                    val myResponse = response.data.message
                    showSnackBar(myResponse)
                    click(myResponse)
                    dismiss()

                }
                is DataState.Error -> {
                    //showSnackBar("Slow or no Internet Connection")
                    click("Slow or no Internet Connection")
                    dismiss()
                }
                is DataState.GenericError -> {

                    if (response.code!! == 403){
                        App.token = null
                        //showSnackBar("token expired, please login again")
                        click("token expired, please login again")
                        findNavController().popBackStack()
                        dismiss()
                    }
                    else{
                        val myResponse = response.error?.message.toString()
                        click(myResponse)
                        dismiss()
                    }
                }
                DataState.Loading -> {
                    showProgressBar()
                }
            }

        })

        binding.proceed.setOnClickListener {
            val passwordBS = PasswordBottomSheet{ pin ->
                val withdrawd = WithdrawDetails(
                    account_number = accountNumber ,
                    amount = binding.amount.text.toString().toInt(),
                    bank_code = bankCode ,
                    password = pin)

                viewModel.withdraw(withdrawd)
            }

            passwordBS.show(requireActivity().supportFragmentManager, "something")

        }

        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val clickedItem: Account = parent.getItemAtPosition(position) as Account
                val clickedAccountNumber: String = clickedItem.account_number
                val clickedBankCode: String = clickedItem.bank_code

                bankCode = clickedBankCode
                accountNumber = clickedAccountNumber
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showProgressBar(){
        binding.progressCircular.visibility = View.VISIBLE
        binding.title.visibility = View.INVISIBLE
        binding.amountField.visibility = View.INVISIBLE
        binding.selectBank.visibility = View.INVISIBLE
        binding.proceed.visibility = View.INVISIBLE
    }

}