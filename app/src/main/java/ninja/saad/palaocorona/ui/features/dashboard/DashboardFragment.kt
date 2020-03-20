package ninja.saad.palaocorona.ui.features.dashboard

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_dashboard.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.authentication.AuthenticationActivity
import ninja.saad.palaocorona.ui.features.faq.FaqFragment
import ninja.saad.palaocorona.ui.features.news.NewsFragment
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.act

class DashboardFragment: BaseFragment<DashboardViewModel>() {


    override val layoutId: Int
        get() = R.layout.fragment_dashboard
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        gujobSlider.sliderAdapter = SliderAdapter()
        gujobSlider.startAutoCycle()
        setClickListener()
    }
    
    private fun setClickListener() {
        
        btnTest.onClick {
            startActivity(AuthenticationActivity::class.java, null)
        }
        
        btnNews.onClick {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, NewsFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        btnDos.onClick {
        
        }
        btnQuarantine.onClick {
        
        }
        btnFaq.onClick {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, FaqFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        btnMessage.onClick {
        
        }
    }
}