package ninja.saad.palaocorona.ui.features.contact

import androidx.lifecycle.MutableLiveData
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.data.contact.Contact
import javax.inject.Inject

class ContactViewModel @Inject constructor(): BaseViewModel() {
    
    var contactList = MutableLiveData<MutableList<Contact>>()
    
    fun getContactList(
        contactNames: MutableList<Array<String>>,
        contacts: Array<String>
    ) {
        val contactList = mutableListOf<Contact>()
        var contactIndex = 0
        contactNames.forEach {
            for (i in it.indices) {
                if(i == 0) {
                    contactList.add(Contact(1, it[i]))
                } else {
                    contactList.add(Contact(2, it[i], contacts[contactIndex]))
                    contactIndex++
                }
            }
        }
        this.contactList.value = contactList
    }
}