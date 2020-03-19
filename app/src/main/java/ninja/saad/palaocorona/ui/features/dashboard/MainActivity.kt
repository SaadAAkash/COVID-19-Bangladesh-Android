package ninja.saad.palaocorona.ui.features.dashboard

import android.os.Bundle
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseActivity

class MainActivity : BaseActivity<MainViewModel>() {
    
    override val layoutId: Int = R.layout.activity_main
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
}
