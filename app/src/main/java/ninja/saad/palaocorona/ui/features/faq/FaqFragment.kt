package ninja.saad.palaocorona.ui.features.faq

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_faq.*
import kotlinx.android.synthetic.main.item_faq_2.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.util.AnimationManager
import org.jetbrains.anko.sdk27.coroutines.onClick

class FaqFragment: BaseFragment<FaqViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_faq
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rvFaq.layoutManager = LinearLayoutManager(context)
        rvFaq.adapter = FaqAdapter()
        
        viewModel.faq.observe(viewLifecycleOwner, Observer {
            (rvFaq.adapter as FaqAdapter).setItems(it)
        })
        
        viewModel.getFaq()
    }
}