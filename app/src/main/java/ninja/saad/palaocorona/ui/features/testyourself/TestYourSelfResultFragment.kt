package ninja.saad.palaocorona.ui.features.testyourself

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_test_yourself_result.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class TestYourSelfResultFragment : BaseFragment<TestYourselfViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_test_yourself_result
    
    override fun onCreate(savedInstanceState: Bundle?) {
        isActivityAsViewModelLifeCycleOwner = true
        super.onCreate(savedInstanceState)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animResult.imageAssetsFolder = "assets/"
        
        btnBack.onClick {
            activity?.onBackPressed()
        }
        
        viewModel.result.observe(viewLifecycleOwner, Observer {
            if(it == 1) {
                animResult.setAnimation("wash_hands.json")
                tvResult.text = getString(R.string.test_positive)
            } else if(it == 2) {
                animResult.setAnimation("doctor.json")
                tvResult.text = getString(R.string.test_medium)
            } else {
                animResult.setAnimation("emergency.json")
                tvResult.text = getString(R.string.test_negative)
            }
        })
        
        viewModel.getResult()
    }
}