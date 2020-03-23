package ninja.saad.palaocorona.ui.features.main

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseActivity
import ninja.saad.palaocorona.base.ui.CustomTypefaceSpan
import ninja.saad.palaocorona.ui.features.dashboard.DashboardFragment

class MainActivity : BaseActivity<MainViewModel>() {
    
    override val layoutId: Int = R.layout.activity_main
    private var doubleBackToExitPressedOnce: Boolean = false
    private var isLoggedIn = false
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val spannable = SpannableString(getString(R.string.app_name_top_bar))
        val engFont = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.mina), Typeface.NORMAL)
        spannable.setSpan(CustomTypefaceSpan("", engFont), 0,
            spannable.length ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(applicationContext, R.color.colorAccent)),
            spannable.length -1, spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        title = spannable
        
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                mainFragmentContainer.id,
                DashboardFragment()
            ).commit()
        }
        
        viewModel.isLoggedIn.observe(this, Observer {
            this.isLoggedIn = it
            invalidateOptionsMenu()
        })
    }
    
    override fun onFragmentResume() {
        super.onFragmentResume()
        viewModel.getIsLoggedIn()
    }
    
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isLoggedIn) {
            menuInflater.inflate(R.menu.main_menu_logged_in, menu)
        } else {
            menuInflater.inflate(R.menu.main_menu, menu)
        }
        return true
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.language) {
            toggleLanguage()
        }
        return super.onOptionsItemSelected(item)
    }
    
    override fun onBackPressed() {
        
        if(supportFragmentManager.backStackEntryCount == 0) {
            val toast = Toast.makeText(this, getString(R.string.double_press_to_exit), Toast.LENGTH_LONG)
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                toast.cancel()
            } else {
                toast.show()
            }
            val handler = Handler()
            handler.postDelayed({ toast.cancel() }, 790)
            this.doubleBackToExitPressedOnce = true
            
            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            super.onBackPressed()
        }
        
    }
}
