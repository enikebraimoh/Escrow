package com.adashi.escrow.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adashi.escrow.R
import com.adashi.escrow.databinding.FragmentVerifyEmailBinding
import ng.adashi.core.BaseFragment
import android.content.Intent





class VerifyEmailFragment : BaseFragment<FragmentVerifyEmailBinding>(R.layout.fragment_verify_email) {

    override fun start() {
        super.start()

        binding.emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_APP_EMAIL)
            requireActivity().startActivity(intent)
        }

    }

}