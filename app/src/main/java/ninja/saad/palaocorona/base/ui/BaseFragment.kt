package ninja.saad.palaocorona.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

abstract class BaseFragment<ViewModel: BaseViewModel>: DaggerFragment() {
    
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var communicator: FragmentCommunicator
    protected lateinit var viewModel: ViewModel
    protected abstract val layoutId: Int
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentCommunicator) {
            communicator = context
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
        
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }
    
    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        return type as Class<ViewModel>
    }
    
}