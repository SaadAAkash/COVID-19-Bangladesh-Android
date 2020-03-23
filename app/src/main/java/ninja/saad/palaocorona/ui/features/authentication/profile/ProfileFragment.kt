package ninja.saad.palaocorona.ui.features.authentication.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_profile.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.data.authentication.model.User
import ninja.saad.palaocorona.ui.features.authentication.AuthenticationViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick

class ProfileFragment : BaseFragment<AuthenticationViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_profile
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
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
        etName.setText(user.name)
        etAge.setText(user.age)
        etGender.setText(user.gender)
        etPhoneNumber.setText(user.phoneNumber)
    }
}