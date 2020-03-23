package ninja.saad.palaocorona.ui.features.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_dashboard.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.about.AboutCovidFragment
import ninja.saad.palaocorona.ui.features.authentication.AuthenticationActivity
import ninja.saad.palaocorona.ui.features.faq.FaqFragment
import ninja.saad.palaocorona.ui.features.news.NewsFragment
import ninja.saad.palaocorona.ui.features.quarantine.QuarantineFragment
import ninja.saad.palaocorona.ui.features.quarantine.QuarantineViewModel
import ninja.saad.palaocorona.ui.features.testyourself.TestYourselfFragment
import org.jetbrains.anko.sdk27.coroutines.onClick

class DashboardFragment: BaseFragment<DashboardViewModel>() {
    
    private val LOGIN_REQUEST_CODE = 2592
    
    override val layoutId: Int
        get() = R.layout.fragment_dashboard
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        gujobSlider.sliderAdapter = SliderAdapter("dashboard", getCurrentLocale().language)
        //drop or swap
        gujobSlider.setIndicatorAnimation(IndicatorAnimations.DROP); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        gujobSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        gujobSlider.scrollTimeInSec = 8 //set scroll delay in seconds :
        gujobSlider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        gujobSlider.setSliderAnimationDuration(600)
        gujobSlider.setIndicatorAnimationDuration(600)
        gujobSlider.startAutoCycle()
        gujobSlider.isAutoCycle = true
        setClickListener()
        
        viewModel.sliderImages.observe(viewLifecycleOwner, Observer {
            (gujobSlider.sliderAdapter as SliderAdapter).addSliderToDashboard(it)
        })
        
        viewModel.getSliderImages()
    }
    
    private fun setClickListener() {
        
        btnAboutCovid.onClick {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, AboutCovidFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        btnVirusTest.onClick {
            if(viewModel.isUserLoggedIn()) {
                startTest()
            } else {
                val intent = Intent(context, AuthenticationActivity::class.java)
                startActivityForResult(intent, LOGIN_REQUEST_CODE)
            }
        }
        btnRecentNews.onClick {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, NewsFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        btnDosNDonts.onClick {
        
        }
        btnQuarantine.onClick {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, QuarantineFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        btnFAQ.onClick {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.mainFragmentContainer, FaqFragment())
                ?.addToBackStack(null)
                ?.commit()
        }
        btnLiveUpdates.onClick {
        
        }
        btnEmergency.onClick {
            
        }
        
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            startTest()
        }
    }
    
    private fun startTest() {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.mainFragmentContainer, TestYourselfFragment())
            ?.addToBackStack(null)
            ?.commit()
    }
}