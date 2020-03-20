package ninja.saad.palaocorona.ui.features.about

import android.os.Bundle
import android.view.View
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment

class AboutCovidFragment: BaseFragment<AboutCovidViewModel>()  {
    
    override val layoutId: Int
        get() = R.layout.fragment_about_covid
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}