package ninja.saad.palaocorona.ui.features.authentication

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.fragment_create_profle.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class CreateProfileFragment: BaseFragment<AuthenticationViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_create_profle
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val genders = resources.getStringArray(R.array.genders)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, genders)
        etGender.setAdapter(arrayAdapter)
        
        btnCreateProfile.onClick {
            viewModel.saveProfile(etName.text.toString(), etAge.text.toString(),
                genders.indexOf(etGender.text.toString()), etPhoneNumber.text.toString())
        }
    }
    
}