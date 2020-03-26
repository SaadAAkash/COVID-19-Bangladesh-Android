package xyz.palaocorona.base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import xyz.palaocorona.ui.dialogs.NoInternetConnectionDialog
import org.jetbrains.anko.support.v4.toast
import java.lang.reflect.ParameterizedType
import java.util.*
import javax.inject.Inject

abstract class BaseFragment<ViewModel: BaseViewModel>: DaggerFragment() {
    
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var communicator: FragmentCommunicator
    protected lateinit var viewModel: ViewModel
    protected abstract val layoutId: Int
    private var alertDialog: AlertDialog? = null
    var isActivityAsViewModelLifeCycleOwner = false
    
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is FragmentCommunicator) {
            communicator = context
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!isActivityAsViewModelLifeCycleOwner) {
            viewModel = ViewModelProvider(this, factory).get(getViewModelClass())
        } else {
            activity?.let {
                viewModel = ViewModelProvider(it, factory).get(getViewModelClass())
            }
        }
        
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loader.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it) {
                communicator.showLoader()
            } else {
                communicator.hideLoader()
            }
        })
        viewModel.toastMessage.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            toast(it)
        })
    }
    
    override fun onResume() {
        super.onResume()
        communicator.onFragmentResume()
    }
    
    fun toggleLanguage() {
        communicator.toggleLanguage()
    }
    
    fun startActivity(clz: Class<*>, bundle: Bundle? = null) {
        communicator.startActivity(clz, bundle)
    }
    
    fun getCurrentLocale(): Locale {
        return communicator.getCurrentLocale()
    }
    
    fun showNoInternetConnectionDialog(callback: NoInternetConnectionDialog.NoInternetDialogCallback?) {
        try {
            alertDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        alertDialog = NoInternetConnectionDialog(requireContext(), callback)
        alertDialog?.setCancelable(callback == null)
        alertDialog?.show()
    }
    
    fun showLoader() {
        communicator.showLoader()
    }
    
    fun hideLoader() {
        communicator.hideLoader()
    }
    
    private fun getViewModelClass(): Class<ViewModel> {
        val type =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0]   // index of 0 means first argument of Base class param
        return type as Class<ViewModel>
    }
    
}