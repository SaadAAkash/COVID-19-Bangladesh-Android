package ninja.saad.palaocorona.ui.features.authentication

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_authentication.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseActivity
import ninja.saad.palaocorona.ui.features.authentication.login.LoginFragment

class AuthenticationActivity : BaseActivity<AuthenticationViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.activity_authentication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        
        supportFragmentManager.beginTransaction()
            .replace(authenticationFragmentContainer.id, LoginFragment())
            .commit()
        
    }
    
    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            setResult(Activity.RESULT_CANCELED)
            super.onBackPressed()
        }
        
    }
}