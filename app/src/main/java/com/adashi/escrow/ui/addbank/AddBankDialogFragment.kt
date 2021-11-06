package com.adashi.escrow.ui.addbank

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import com.adashi.escrow.databinding.AddBankBottomSheetDialogueBinding
import com.adashi.escrow.databinding.SucessBottomSheetDialogueBinding
import com.adashi.escrow.models.createtransaction.NewTransactionBodyResponse
import com.adashi.escrow.models.listofbanks.ListOfBanks
import com.adashi.escrow.models.listofbanks.ListOfBanksItem
import ng.adashi.utils.RoundedBottomSheet
import com.adashi.escrow.MainActivity
import com.adashi.escrow.ShowSucessDialogFragment
import com.adashi.escrow.models.accountname.AccountNameResponse
import com.adashi.escrow.repository.SettingsRepository
import com.adashi.escrow.ui.createtransaction.CreateTransactionFactory
import com.adashi.escrow.ui.createtransaction.CreateTransactionViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ng.adashi.network.NetworkDataSourceImpl
import ng.adashi.repository.HomeRepository
import ng.adashi.utils.App
import ng.adashi.utils.DataState
import android.text.Editable

import android.text.TextWatcher

import android.widget.EditText
import com.adashi.escrow.models.addbank.AddBankDetails
import com.adashi.escrow.models.addbank.AddBankResponse
import kotlin.properties.Delegates


class AddBankDialogFragment(val click: (id: Int) -> Unit) : RoundedBottomSheet() {
    lateinit var binding: AddBankBottomSheetDialogueBinding
    lateinit var bankname: String
    var data = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.add_bank_bottom_sheet_dialogue, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = AddBankFactory(application, SettingsRepository(network))

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            AddBankViewModel::class.java)

        binding.data = viewModel
        binding.lifecycleOwner = this

        binding.progressBar.visibility = View.INVISIBLE
        binding.accountName.visibility = View.VISIBLE

        viewModel.getNigerianBanks()



        viewModel.navigateToLogin.observe(this,{
            if (!it){
                data = false
            }

        })

        viewModel.banks.observe(this, { response ->
            when (response) {
                is DataState.Success<MutableList<ListOfBanksItem>> -> {

                    val banks = response.data

                    val BanksArrayAdapter =
                        BanksAdapter(requireActivity().applicationContext, banks)

                    binding.spinner.adapter = BanksArrayAdapter

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
                        showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {

                }
            }
        })
        viewModel.name.observe(this, { response ->
            when (response) {
                is DataState.Success<AccountNameResponse> -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    binding.accountName.visibility = View.VISIBLE
                    binding.accountName.text = response.data.data.accountName

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
                        showSnackBar(response.code.toString())
                    }
                }
                DataState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.accountName.visibility = View.INVISIBLE
                }
            }
        })
        viewModel.res.observe(this, { response ->
            when (response) {
                is DataState.Success<AddBankResponse> -> {
                    if(data){
                        showSnackBar("Bank account Added")
                        binding.addbank.visibility = View.VISIBLE
                        click(1)
                        Toast.makeText(requireContext(), "Bank account Added", Toast.LENGTH_SHORT).show()
                        viewModel.navigateToLoginDone()
                        data = false
                        dismiss()
                    }
                }
                is DataState.Error -> {
                    showSnackBar("Slow or no Internet Connection")
                    dismiss()
                }
                is DataState.GenericError -> {
                   if (data){
                       if (response.code!! == 403){
                           App.token = null
                           binding.addbank.visibility = View.VISIBLE
                           showSnackBar("token expired, please login again")
                           findNavController().popBackStack()
                       }
                       else{
                           binding.addbank.visibility = View.VISIBLE
                           Toast.makeText(requireContext(), response.code.toString(), Toast.LENGTH_SHORT).show()
                           Toast.makeText(requireContext(), response.error?.message.toString(), Toast.LENGTH_SHORT).show()
                           showSnackBar(response.error?.message.toString())
                           dismiss()
                       }
                   }
                }
                DataState.Loading -> {
                    binding.addbank.visibility = View.INVISIBLE
                }
            }
        })

        val delay: Long = 1000 // 1 seconds after user stops typing

        var last_text_edit: Long = 0
        val handler = Handler()

        val input_finish_checker = Runnable {
            if (System.currentTimeMillis() > last_text_edit + delay - 500) {

               viewModel.getAccountName(binding.bankNumber.text.toString())

            }
        }

       // val editText = findViewById(R.id.editTextStopId) as EditText
        binding.bankNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                handler.removeCallbacks(input_finish_checker)
            }

            override fun afterTextChanged(s: Editable) {
                //avoid triggering event when text is empty
                if (s.length > 0) {
                    last_text_edit = System.currentTimeMillis()
                    handler.postDelayed(input_finish_checker, delay)
                } else {
                }
            }
        }
        )



        binding.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val clickedItem: ListOfBanksItem = parent.getItemAtPosition(position) as ListOfBanksItem
                val clickedCountryName: String = clickedItem.name

                bankname = clickedCountryName
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        binding.addbank.setOnClickListener {
            val bank = AddBankDetails(binding.accountName.text.toString(),binding.bankNumber.text.toString(),bankname)
            //Toast.makeText(requireContext(), bank.toString(), Toast.LENGTH_SHORT).show()
            viewModel.addBank(bank)
            data = true
            viewModel.navigateButtonClicked()
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}


