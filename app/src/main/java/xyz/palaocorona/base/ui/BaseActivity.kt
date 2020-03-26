package xyz.palaocorona.base.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger
import dagger.android.support.DaggerAppCompatActivity
import xyz.palaocorona.base.BaseApplication
import org.jetbrains.anko.toast
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.inject.Inject


abstract class BaseActivity<ViewModel: BaseViewModel> : DaggerAppCompatActivity(), FragmentCommunicator {
    
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    protected lateinit var viewModel: ViewModel
    abstract val layoutId: Int
    private var loader: Loader? = null
    
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(BaseApplication.localeManager.setLocale(base))
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setContentView(layoutId)
        viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
    
        if(supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(false)
        }
        
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
            BaseApplication.localeManager.setNewLocale(this, "en")
        } else {
            BaseApplication.localeManager.setNewLocale(this, "bn")
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
    
    override fun onFragmentResume() {
    
    }
    
    override fun showLoader() {
        try {
            loader?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        loader = Loader()
        loader?.setCancelable(false)
        loader?.show(supportFragmentManager, "loader")
    }
    
    override fun hideLoader() {
        try {
            loader?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<ViewModel>
    }
}