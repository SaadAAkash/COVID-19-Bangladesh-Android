package ninja.saad.palaocorona.base.ui

import android.content.Context
import dagger.android.support.DaggerFragment

open class BaseFragment: DaggerFragment() {
    
    private lateinit var communicator: FragmentCommunicator
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentCommunicator) {
            communicator = context
        }
    }
    
}