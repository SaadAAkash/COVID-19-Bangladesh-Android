package xyz.palaocorona.ui.features.main

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_main.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseActivity
import xyz.palaocorona.base.ui.CustomTypefaceSpan
import xyz.palaocorona.ui.features.authentication.AuthenticationActivity
import xyz.palaocorona.ui.features.dashboard.DashboardFragment

class MainActivity : BaseActivity<MainViewModel>() {
    
    override val layoutId: Int = R.layout.activity_main
    private var doubleBackToExitPressedOnce: Boolean = false
    private var isLoggedIn = false
    private var alertDialog: AlertDialog? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val spannable = SpannableString(getString(R.string.app_name_top_bar))
        val engFont = Typeface.create(ResourcesCompat.getFont(applicationContext, R.font.mina), Typeface.NORMAL)
        spannable.setSpan(CustomTypefaceSpan("", engFont), 0,
            spannable.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
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
            if(this.isLoggedIn && this.isLoggedIn != it) {
                supportFragmentManager.beginTransaction().replace(
                    mainFragmentContainer.id,
                    DashboardFragment()
                ).commit()
            }
            this.isLoggedIn = it
            invalidateOptionsMenu()
        })
        
        viewModel.isUpdateAvailable.observe(this, Observer {
            if(it) {
                showUpdateDialog()
            }
        })
        
        viewModel.checkForUpdate()
    
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(object : OnCompleteListener<InstanceIdResult?> {
                override fun onComplete(task: Task<InstanceIdResult?>) {
                    if (!task.isSuccessful) {
                        Logger.e("getInstanceId failed - " + task.exception)
                        return
                    }
                    val token: String? = task.result?.token
                    Logger.d(token)
                
                   
                }
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
        } else if(item.itemId == R.id.profile) {
            val bundle = Bundle()
            bundle.putString(AuthenticationActivity.NAVIGATE, AuthenticationActivity.NAV_PROFILE)
            startActivity(AuthenticationActivity::class.java, bundle)
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
    
    private fun showUpdateDialog() {
        try {
            alertDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        alertDialog = MaterialAlertDialogBuilder(this)
            .setTitle(R.string.update_available)
            .setMessage(R.string.updae_available_message)
            .setPositiveButton(R.string.update) { _, _ ->
            
            }.setNegativeButton(R.string.exit) { _, _ ->
            
            }
            .show()
    }
}
