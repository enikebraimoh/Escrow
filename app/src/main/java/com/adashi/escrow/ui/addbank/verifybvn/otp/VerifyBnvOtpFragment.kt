package com.adashi.escrow.ui.addbank.verifybvn.otp

import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentVerifyBnvOtpBinding
import com.adashi.escrow.models.user.NewBVNFeedBack
import com.adashi.escrow.models.verifybvn.BVN
import com.adashi.escrow.repository.SettingsRepository
import com.google.android.material.snackbar.Snackbar
import ng.adashi.core.BaseFragment
import com.adashi.escrow.network.NetworkDataSourceImpl
import ng.adashi.network.SessionManager
import ng.adashi.utils.App
import ng.adashi.utils.DataState


class VerifyBnvOtpFragment : BaseFragment<FragmentVerifyBnvOtpBinding>(R.layout.fragment_verify_bnv_otp) {

    override fun start() {
        super.start()

        val application = requireNotNull(this.activity).application
        val network = NetworkDataSourceImpl()
        val viewModelProviderFactory = VerifyOtpBnvFactory(application, SettingsRepository(network))

        var prefs: SharedPreferences = requireContext().getSharedPreferences(
            requireContext().getString(R.string.app_name),
            Context.MODE_PRIVATE
        )

        val viewModel = ViewModelProvider(requireActivity(), viewModelProviderFactory).get(
            VerifyOtpBvnViewModel::class.java)

        val args: VerifyBnvOtpFragmentArgs by navArgs()
        val bvn = args.bvn
        val phone = args.phoneNumber

        binding.info.text = resources.getString(R.string.an_otp,phone.substring(9))

        binding.Confirmotp.setOnClickListener {
            val bvn  = BVN(binding.otpView.text.toString(),bvn)
            viewModel.addBvn(bvn)
        }

        viewModel.banks.observe(this, { response ->
            when (response) {
                is DataState.Success<NewBVNFeedBack> -> {
                    displayProgressBar(false)

                     val editor = prefs.edit()
                     editor.putString(SessionManager.USER_BVN,response.data.data.user.bvn)
                     editor.apply()

                    Toast.makeText(requireContext(), "BVN Verified Successfully", Toast.LENGTH_SHORT).show()
                    showSnackBar("BVN Verified Successfully")
                    findNavController().popBackStack()

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
                        showSnackBar(response.error?.message.toString())
                    }
                }
                DataState.Loading -> {
                    displayProgressBar(true)
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
                binding.Confirmotp.visibility = View.INVISIBLE
            }
            false -> {
                binding.spinKit.visibility = View.INVISIBLE
                binding.Confirmotp.visibility = View.VISIBLE
            }
        }

    }

}