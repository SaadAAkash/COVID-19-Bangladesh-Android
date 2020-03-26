package xyz.palaocorona.ui.features.authentication.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseFragment
import xyz.palaocorona.ui.dialogs.NoInternetConnectionDialog
import xyz.palaocorona.ui.features.authentication.AuthenticationViewModel
import xyz.palaocorona.ui.features.authentication.otpverification.OtpVerificationFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class LoginFragment: BaseFragment<AuthenticationViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_login
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        isActivityAsViewModelLifeCycleOwner = true
        super.onCreate(savedInstanceState)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnLogin.onClick {
            viewModel.sendOtp(etPhoneNumber.text.toString())
        }
    
        viewModel.noInternetConnection.observe(viewLifecycleOwner, Observer {
            showNoInternetConnectionDialog(object: NoInternetConnectionDialog.NoInternetDialogCallback {
                override fun retry() {
                    viewModel.sendOtp(etPhoneNumber.text.toString())
                }
            })
        })
        
        viewModel.verificationId.observeOn(viewLifecycleOwner, Observer {
            val fragment =
                OtpVerificationFragment()
            val bundle = Bundle()
            bundle.putString(OtpVerificationFragment.VERIFICATION_ID, it)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.authenticationFragmentContainer, fragment)
                ?.addToBackStack(null)
                ?.commit()
        })
        
        viewModel.phoneNumberInvalid.observeOn(viewLifecycleOwner, Observer {
            toast(R.string.phone_number_invalid)
        })
    }
}