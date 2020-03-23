package ninja.saad.palaocorona.ui.features.liveupdates

import android.os.Bundle
import android.view.View
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.about.AboutCovidViewModel

class LiveUpdatesFragment : BaseFragment<AboutCovidViewModel>()  {
    
    override val layoutId: Int
    get() = R.layout.fragment_live_updates
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}