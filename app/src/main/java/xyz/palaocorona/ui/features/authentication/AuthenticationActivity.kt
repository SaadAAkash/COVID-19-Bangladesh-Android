package xyz.palaocorona.ui.features.authentication

import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_authentication.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseActivity
import xyz.palaocorona.base.ui.CustomTypefaceSpan
import xyz.palaocorona.ui.features.authentication.createprofile.CreateProfileFragment
import xyz.palaocorona.ui.features.authentication.login.LoginFragment
import xyz.palaocorona.ui.features.authentication.profile.ProfileFragment

class AuthenticationActivity : BaseActivity<AuthenticationViewModel>() {
    
    companion object {
        const val NAVIGATE = "navigate"
        const val NAV_PROFILE = "profile"
    }
    
    override val layoutId: Int
        get() = R.layout.activity_authentication
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        val spannable = SpannableString(getString(R.string.app_name_top_bar))
        val engFont = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.mina), Typeface.NORMAL)
        spannable.setSpan(
            CustomTypefaceSpan("", engFont), 0,
            spannable.length ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorAccent)),
            spannable.length -1, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        title = spannable
        
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        
        if(intent.extras?.getString(NAVIGATE) == NAV_PROFILE) {
            supportFragmentManager.beginTransaction()
                .replace(authenticationFragmentContainer.id, ProfileFragment())
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(authenticationFragmentContainer.id, LoginFragment())
                .commit()
        }
    
        viewModel.userExists.observe(this, Observer {
            if(!it) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.authenticationFragmentContainer,
                        CreateProfileFragment()
                    )
                    .commit()
            } else {
                setResult(Activity.RESULT_OK)
                finish()
            }
        })
        
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.language) {
            toggleLanguage()
        }
        return super.onOptionsItemSelected(item)
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