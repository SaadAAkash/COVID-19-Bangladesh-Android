package ninja.saad.palaocorona.base.ui

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.franmontiel.localechanger.LocaleChanger
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerAppCompatActivity
import org.jetbrains.anko.toast
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.inject.Inject


abstract class BaseActivity<ViewModel: BaseViewModel> : DaggerAppCompatActivity(), FragmentCommunicator {
    
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    protected lateinit var viewModel: ViewModel
    abstract val layoutId: Int
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
        
        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeButtonEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.setHomeButtonEnabled(false)
            }
        }
        
        viewModel.toastMessage.observe(this, androidx.lifecycle.Observer {
            toast(it)
        })
        
    }
    
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    override fun startActivity(clz: Class<*>, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }
    
    override fun toggleLanguage() {
        val locale = getCurrentLocale()
        Logger.d(locale.language)
        if(locale.language == "bn") {
            LocaleChanger.setLocale(Locale("en"))
        } else {
            LocaleChanger.setLocale(Locale("bn"))
        }
        recreate()
    }
    
    override fun getCurrentLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            resources.configuration.locales.get(0)
        } else {
            resources.configuration.locale
        }
    }
    
    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<ViewModel>
    }
}