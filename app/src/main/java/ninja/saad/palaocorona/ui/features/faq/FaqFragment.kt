package ninja.saad.palaocorona.ui.features.faq

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_faq.*
import kotlinx.android.synthetic.main.item_faq_2.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class FaqFragment: BaseFragment<FaqViewModel>() {
    
    override val layoutId: Int
        get() = R.layout.fragment_faq
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        
        rvFaq.layoutManager = LinearLayoutManager(context)
        rvFaq.adapter = FaqAdapter()
        
        viewModel.faq.observe(viewLifecycleOwner, Observer {
            (rvFaq.adapter as FaqAdapter).setItems(it)
        })
        
        viewModel.getFaq()
    }
    
    private fun initViews() {
        btn_expand.onClick {
            //toggleSectionInfo(btn_expand);
        }
        btn_hide.onClick {
            //toggleSectionInfo(btn_hide);
        }
    }
    
    /*private fun toggleSectionInfo(view: View) {
        val show: Boolean = toggleArrow(view)
        if (show) {
            ViewAnimation.expand(layout_faq_answer, object : AnimListener() {
                fun onFinish() {
                    Tools.nestedScrollTo(nested_scroll_view, layout_faq_answer)
                }
            })
        } else {
            ViewAnimation.collapse(layout_faq_answer)
        }
    }*/
    
    fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }
}