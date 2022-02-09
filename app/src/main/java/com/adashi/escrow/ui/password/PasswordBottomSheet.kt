package ng.adashi.ui.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.adashi.escrow.R
import com.adashi.escrow.databinding.PasswordBottomSheetBinding
import ng.adashi.utils.RoundedBottomSheet

class PasswordBottomSheet(val click : (pin : String) -> Unit): RoundedBottomSheet() {
    lateinit var binding: PasswordBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.password_bottom_sheet, container, false
        )

        binding.continueBtn.setOnClickListener {
            var pin = binding.otpView.text.toString()
            if (validateField()){
               click(pin)
                dismiss()
            }
        }

        return binding.root
    }


    private fun validateField(): Boolean {
        return if (binding.otpView.text.toString() == "" || binding.otpView.text.toString() == null) {
            binding.otpViewField.isErrorEnabled = true
            binding.otpViewField.error = "please enter your password"
            false
        } else {
            binding.otpViewField.isErrorEnabled = false
            true
        }
    }




}