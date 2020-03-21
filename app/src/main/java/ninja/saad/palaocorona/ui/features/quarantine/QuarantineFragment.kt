package ninja.saad.palaocorona.ui.features.quarantine

import android.os.Bundle
import android.view.View
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment

class QuarantineFragment: BaseFragment<QuarantineViewModel>()  {
    
    override val layoutId: Int
        get() = R.layout.fragment_slider_images
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}