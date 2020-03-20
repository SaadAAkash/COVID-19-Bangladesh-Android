package ninja.saad.palaocorona.ui.features.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_login.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.authentication.AuthenticationViewModel
import ninja.saad.palaocorona.ui.features.authentication.otpverification.OtpVerificationFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginFragment: BaseFragment<AuthenticationViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_login
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnLogin.onClick {
            validateUser(etPhoneNumber.text.toString())
        }
        
        viewModel.verificationId.observe(viewLifecycleOwner, Observer {
            val fragment =
                OtpVerificationFragment()
            val bundle = Bundle()
            bundle.putString(OtpVerificationFragment.VERIFICATION_ID, it)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.authenticationFragmentContainer, fragment)
                ?.commit()
        })
    }
    
    private fun validateUser(number : String) {
        when {
            number.length <= 10 ->
                Toast.makeText(context, "Invalid Phone Number. Please input a valid phone number", Toast.LENGTH_SHORT).show()
            number.length == 11 -> {
                number.let {
                    if ( ! (it.startsWith("01", true)) )
                        Toast.makeText(context,"Invalid Phone Number. Please input a valid phone number", Toast.LENGTH_SHORT).show()
                    else
                        viewModel.sendOtp(etPhoneNumber.text.toString())
                }
            }
        }
    }
}