package ninja.saad.palaocorona.ui.features.faq

import android.os.Bundle
import android.view.View
import android.widget.ExpandableListAdapter
import android.widget.ExpandableListView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_faq.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.util.AnimationManager
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.find
import java.util.ArrayList
import java.util.HashMap

class FaqFragment: BaseFragment<FaqViewModel>() {
    
    private lateinit var expandableListView: ExpandableListView
    private lateinit var expandableListAdapter: ExpandableListAdapter
    private lateinit var expandableListTitle: List<String>
    private lateinit var expandableListDetail: HashMap<String, List<String>>
    
    override val layoutId: Int
        get() = R.layout.fragment_faq
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        expandableListView = find(R.id.expandableListView)
        expandableListDetail = setData()
        expandableListTitle = ArrayList(expandableListDetail.keys)
        expandableListAdapter = FAQExpandableListAdapter(context, expandableListTitle, expandableListDetail)
        expandableListView.setAdapter(expandableListAdapter)
        
        /*rvFaq.layoutManager = LinearLayoutManager(context)
        rvFaq.adapter = FaqAdapter()
        viewModel.faq.observe(viewLifecycleOwner, Observer {
            (rvFaq.adapter as FaqAdapter).setItems(it)
        })
        viewModel.getFaq()*/
    }
    
    private fun setData() : HashMap<String, List<String>> {
        val expandableListDetail = HashMap<String, List<String>>()
        expandableListDetail[resources.getString(R.string.faq_q_1)] = arrayListOf(resources.getString(R.string.faq_a_1))
        expandableListDetail[resources.getString(R.string.faq_q_2)] = arrayListOf(resources.getString(R.string.faq_a_2))
        expandableListDetail[resources.getString(R.string.faq_q_3)] = arrayListOf(resources.getString(R.string.faq_a_3))
        expandableListDetail[resources.getString(R.string.faq_q_4)] = arrayListOf(resources.getString(R.string.faq_a_4))
        expandableListDetail[resources.getString(R.string.faq_q_5)] = arrayListOf(resources.getString(R.string.faq_a_5))
        expandableListDetail[resources.getString(R.string.faq_q_6)] = arrayListOf(resources.getString(R.string.faq_a_6))
        expandableListDetail[resources.getString(R.string.faq_q_7)] = arrayListOf(resources.getString(R.string.faq_a_7))
        return expandableListDetail
    }
}