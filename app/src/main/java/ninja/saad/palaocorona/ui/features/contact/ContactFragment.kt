package ninja.saad.palaocorona.ui.features.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_contact.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.util.SpacingItemDecoration


class ContactFragment : BaseFragment<ContactViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_contact
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rvContact.layoutManager = LinearLayoutManager(context)
        rvContact.addItemDecoration(SpacingItemDecoration(requireContext(), R.dimen._5sdp))
        rvContact.adapter = ContactAdapter(object: ContactAdapter.ContactAdapterCallback{
            override fun onItemClick(number: String) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:$number")
                startActivity(intent)
            }
        })
        
        viewModel.contactList.observe(viewLifecycleOwner, Observer {
            (rvContact.adapter as ContactAdapter).setItems(it)
        })
        
        viewModel.getContactList(mutableListOf(
            resources.getStringArray(R.array.emergency_contact_name),
            resources.getStringArray(R.array.whole_day_contact_name),
            resources.getStringArray(R.array.other_contact_name)
        ), resources.getStringArray(R.array.contacts))
    }
}