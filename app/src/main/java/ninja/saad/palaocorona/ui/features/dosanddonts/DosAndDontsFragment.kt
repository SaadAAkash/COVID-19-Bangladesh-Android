package ninja.saad.palaocorona.ui.features.dosanddonts

import android.os.Bundle
import android.view.View
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.fragment_slider_images.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.base.ui.BaseViewModel
import ninja.saad.palaocorona.ui.features.dashboard.SliderAdapter

class DosAndDontsFragment: BaseFragment<BaseViewModel>()  {
    
    override val layoutId: Int
        get() = R.layout.fragment_slider_images
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image_slider.sliderAdapter = SliderAdapter("dos", getCurrentLocale().language)
        //drop or swap
        image_slider.setIndicatorAnimation(IndicatorAnimations.DROP) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        image_slider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        image_slider.scrollTimeInSec = 7 //set scroll delay in seconds :
        image_slider.startAutoCycle()
        image_slider.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
        
    }
}