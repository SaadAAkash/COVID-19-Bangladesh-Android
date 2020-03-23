package ninja.saad.palaocorona.ui.features.authentication.otpverification

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_otp_verification.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.authentication.AuthenticationViewModel
import ninja.saad.palaocorona.ui.features.authentication.createprofile.CreateProfileFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

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
        
        viewModel.userExists.observe(viewLifecycleOwner, Observer {
            if(!it) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.authenticationFragmentContainer,
                        CreateProfileFragment()
                    )
                    ?.commit()
            } else {
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            }
        })
        
        viewModel.otpInvalid.observeOn(viewLifecycleOwner, Observer {
            toast(R.string.invalid_otp)
        })
    }
}