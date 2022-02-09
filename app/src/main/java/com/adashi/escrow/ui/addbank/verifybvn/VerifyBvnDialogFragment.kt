package com.adashi.escrow.ui.addbank.verifybvn

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.adashi.escrow.R
import ng.adashi.utils.RoundedBottomSheet
import com.adashi.escrow.databinding.VerifyBvnBottomSheetDialogueBinding
import com.adashi.escrow.models.verifybvn.BvnResponse
import com.adashi.escrow.repository.SettingsRepository
import com.google.android.material.snackbar.Snackbar
import com.adashi.escrow.network.NetworkDataSourceImpl
import ng.adashi.utils.App
import ng.adashi.utils.DataState


class VerifyBvnDialogFragment(val click: (id: Int,bvn: String,phone: String) -> Unit) : RoundedBottomSheet() {
    lateinit var binding: VerifyBvnBottomSheetDialogueBinding
    var data = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.verify_bvn_bottom_sheet_dialogue, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = VerifyBnvFactory(application, SettingsRepository(network))

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            VerifyBvnViewModel::class.java)

        binding.data = viewModel
        binding.lifecycleOwner = this


        viewModel.navigateToLogin.observe(this,{
            if (it){
                viewModel.navigateToLoginDone()
                data = false
            }

        })


        binding.verifybtn.setOnClickListener {
            displayProgressBar(true)
           // val bvn = BVN(binding.bvn.text.toString())
            viewModel.navigateButtonClicked()
            data = true
            viewModel.VerifyBvn(binding.bvn.text.toString())
        }

        viewModel.banks.observe(this, { response ->
            when (response) {
                is DataState.Success<BvnResponse> -> {
                    if (data){
                        displayProgressBar(false)

                         /*val editor = prefs.edit()
                         editor.putString(SessionManager.USER_BVN,response.data.data.bvn)
                         editor.apply()*/

                        click(0,binding.bvn.text.toString(),response.data.data.phone_number)
                        dismiss()
                    }

                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    showSnackBar("Slow or no Internet Connection")
                }
                is DataState.GenericError -> {

                    if (response.code!! == 403){
                        App.token = null
                        displayProgressBar(false)
                        showSnackBar("token expired, please login again")
                        findNavController().popBackStack()
                    }
                    else{
                        Toast.makeText(requireContext(),response.code.toString() , Toast.LENGTH_SHORT).show()
                        showSnackBar(response.code.toString())
                        dismiss()
                    }
                }
                DataState.Loading -> {

                }
            }
        })

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(requireActivity(), binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    private fun displayProgressBar(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                binding.spinKit.visibility = View.VISIBLE
                binding.verifybtn.visibility = View.INVISIBLE
            }
            false -> {
                binding.spinKit.visibility = View.INVISIBLE
                binding.verifybtn.visibility = View.VISIBLE
            }
        }

    }



}


