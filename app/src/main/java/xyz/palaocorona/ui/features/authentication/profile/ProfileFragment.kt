package xyz.palaocorona.ui.features.authentication.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_profile.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseFragment
import xyz.palaocorona.data.authentication.model.User
import xyz.palaocorona.ui.features.authentication.AuthenticationViewModel
import xyz.palaocorona.ui.features.authentication.createprofile.CreateProfileFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProfileFragment : BaseFragment<AuthenticationViewModel>() {
    
    private lateinit var user: User
    
    override val layoutId: Int
        get() = R.layout.fragment_profile
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        btnUpdateProfile.onClick {
            val fragment = CreateProfileFragment()
            val bundle = Bundle()
            bundle.putParcelable(CreateProfileFragment.USER, user)
            bundle.putBoolean(CreateProfileFragment.UPDATE_PROFILE, true)
            fragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.authenticationFragmentContainer,
                    fragment
                )
                ?.commit()
        }
        
        btnLogout.onClick {
            viewModel.logout()
            activity?.finish()
        }
        
        viewModel.user.observe(viewLifecycleOwner, Observer {
            showUserInfo(it)
        })
        
        viewModel.getProfile()
    }
    
    private fun showUserInfo(user: User) {
        this.user = user
        etName.setText(user.name)
        etAge.setText(user.age)
        etGender.setText(user.gender)
        etPhoneNumber.setText(user.phoneNumber)
    }
}