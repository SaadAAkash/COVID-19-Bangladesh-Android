package ninja.saad.palaocorona.ui.features.testyourself

import android.os.Bundle
import android.view.View
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment

class TestYourselfFragment: BaseFragment<TestYourselfViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_test_yourself
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.getQuestionnaire()
    }
}