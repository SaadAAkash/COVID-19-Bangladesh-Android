package xyz.palaocorona.ui.features.authentication.otpverification

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_otp_verification.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseFragment
import xyz.palaocorona.ui.dialogs.NoInternetConnectionDialog
import xyz.palaocorona.ui.features.authentication.AuthenticationViewModel
import xyz.palaocorona.ui.features.authentication.createprofile.CreateProfileFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class OtpVerificationFragment: BaseFragment<AuthenticationViewModel>() {
    companion object {
        const val VERIFICATION_ID = "id"
    }
    
    override val layoutId: Int
        get() = R.layout.fragment_otp_verification
    
    override fun onCreate(savedInstanceState: Bundle?) {
        isActivityAsViewModelLifeCycleOwner = true
        super.onCreate(savedInstanceState)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnVerify.onClick {
            viewModel.verifyOtp(arguments?.getString(VERIFICATION_ID), etOtp.text.toString())
        }
    
        viewModel.noInternetConnection.observe(viewLifecycleOwner, Observer {
            showNoInternetConnectionDialog(object: NoInternetConnectionDialog.NoInternetDialogCallback {
                override fun retry() {
                    viewModel.verifyOtp(arguments?.getString(VERIFICATION_ID), etOtp.text.toString())
                }
            })
        })
        
        viewModel.otpInvalid.observeOn(viewLifecycleOwner, Observer {
            toast(R.string.invalid_otp)
        })
    }
}