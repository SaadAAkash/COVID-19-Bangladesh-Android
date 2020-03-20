package ninja.saad.palaocorona.ui.features.authentication.otpverification

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_otp_verification.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.authentication.AuthenticationViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick

class OtpVerificationFragment: BaseFragment<AuthenticationViewModel>() {
    companion object {
        const val VERIFICATION_ID = "id"
    }
    
    override val layoutId: Int
        get() = R.layout.fragment_otp_verification
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnVerify.onClick {
            viewModel.verifyOtp(arguments?.getString(VERIFICATION_ID), etOtp.text.toString())
        }
    }
}