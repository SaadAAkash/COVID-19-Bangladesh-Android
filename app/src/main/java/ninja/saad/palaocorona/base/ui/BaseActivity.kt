package ninja.saad.palaocorona.base.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseActivity<ViewModel: BaseViewModel> : DaggerAppCompatActivity(), FragmentCommunicator {
    
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var viewModel: ViewModel
    abstract val layoutId: Int
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
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
    
    override fun startActivity(clz: Class<*>?, bundle: Bundle?) {
        val intent = Intent(this, clz)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }
    
    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]
        return type as Class<ViewModel>
    }
}