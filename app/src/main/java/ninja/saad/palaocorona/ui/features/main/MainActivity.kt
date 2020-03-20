package ninja.saad.palaocorona.ui.features.main

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.franmontiel.localechanger.LocaleChanger
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_main.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseActivity
import ninja.saad.palaocorona.ui.features.dashboard.DashboardFragment

class MainActivity : BaseActivity<MainViewModel>() {
    
    override val layoutId: Int = R.layout.activity_main
    private var fragments: MutableList<Fragment> = mutableListOf()
    
    override fun attachBaseContext(base: Context?) {
        val nBase = LocaleChanger.configureBaseContext(base)
        super.attachBaseContext(nBase)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    
        /*val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_main)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            view -> finish()
        }
        toolbar.title = HtmlCompat.fromHtml(getString(R.string.app_name_top_bar), HtmlCompat.FROM_HTML_MODE_LEGACY)*/
        
        supportFragmentManager.addOnBackStackChangedListener {
            fragments = supportFragmentManager.fragments
        }
        
        if(savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                mainFragmentContainer.id,
                DashboardFragment()
            ).commit()
        }
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
}
