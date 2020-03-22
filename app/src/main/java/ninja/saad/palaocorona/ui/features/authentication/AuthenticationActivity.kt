package ninja.saad.palaocorona.ui.features.authentication

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.franmontiel.localechanger.LocaleChanger
import kotlinx.android.synthetic.main.activity_authentication.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseActivity
import ninja.saad.palaocorona.base.ui.CustomTypefaceSpan
import ninja.saad.palaocorona.ui.features.authentication.login.LoginFragment

class AuthenticationActivity : BaseActivity<AuthenticationViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.activity_authentication
    
    override fun attachBaseContext(base: Context?) {
        val nBase = LocaleChanger.configureBaseContext(base)
        super.attachBaseContext(nBase)
    }
    
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
        
        supportFragmentManager.beginTransaction()
            .replace(authenticationFragmentContainer.id, LoginFragment())
            .commit()
        
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