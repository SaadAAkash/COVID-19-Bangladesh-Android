package xyz.palaocorona.ui.features.authentication.createprofile

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_create_profle.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseFragment
import xyz.palaocorona.data.authentication.model.User
import xyz.palaocorona.ui.dialogs.NoInternetConnectionDialog
import xyz.palaocorona.ui.features.authentication.AuthenticationViewModel
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class CreateProfileFragment: BaseFragment<AuthenticationViewModel>() {
    
    companion object {
        const val UPDATE_PROFILE = "update"
        const val USER = "user"
    }
    
    override val layoutId: Int
        get() = R.layout.fragment_create_profle
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val genders = resources.getStringArray(R.array.genders)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        etGender.setAdapter(arrayAdapter)
        
        if(arguments?.getBoolean(UPDATE_PROFILE) == true) {
            val user = arguments?.getParcelable<User>(USER)
            etName.setText(user?.name)
            etAge.setText(user?.age)
//            etGender.setText(user?.gender)
            etPhoneNumber.setText(user?.phoneNumber)
        }
        
        btnCreateProfile.onClick {
            viewModel.saveProfile(etName.text.toString(), etAge.text.toString(),
                etGender.text.toString(), etPhoneNumber.text.toString())
        }
    
        viewModel.noInternetConnection.observe(viewLifecycleOwner, Observer {
            showNoInternetConnectionDialog(object: NoInternetConnectionDialog.NoInternetDialogCallback {
                override fun retry() {
                    viewModel.saveProfile(etName.text.toString(), etAge.text.toString(),
                        etGender.text.toString(), etPhoneNumber.text.toString())
                }
            })
        })
        
        viewModel.profileSaved.observe(viewLifecycleOwner, Observer {
            if(it) {
                activity?.setResult(Activity.RESULT_OK)
                activity?.finish()
            } else {
            
            }
        })
        
        viewModel.nameInvalide.observeOn(viewLifecycleOwner, Observer {
            if(it) {
                toast(R.string.please_enter_your_name)
            }
        })
    
        viewModel.ageInvalide.observeOn(viewLifecycleOwner, Observer {
            if(it) {
                toast(R.string.please_enter_your_age)
            }
        })
        viewModel.genderInvalid.observeOn(viewLifecycleOwner, Observer {
            if(it) {
                toast(R.string.please_select_your_gender)
            }
        })
    
        viewModel.phoneNumberInvalid.observeOn(viewLifecycleOwner, Observer {
            if(it) {
                toast(R.string.phone_number_invalid)
            }
        })
    }
    
}