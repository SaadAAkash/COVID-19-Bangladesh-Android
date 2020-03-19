package ninja.saad.palaocorona.ui.features.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseActivity
import ninja.saad.palaocorona.ui.features.dashboard.DashboardFragment

class MainActivity : BaseActivity<MainViewModel>() {
    
    override val layoutId: Int = R.layout.activity_main
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        supportFragmentManager.beginTransaction().replace(mainFragmentContainer.id,
            DashboardFragment()).addToBackStack(null).commit()
    }
}
