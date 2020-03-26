package ninja.saad.palaocorona.ui.features.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_about_covid.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment

class AboutCovidFragment: BaseFragment<AboutCovidViewModel>()  {
    
    override val layoutId: Int
        get() = R.layout.fragment_about_covid
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_about_covid_source.movementMethod = LinkMovementMethod.getInstance()
        context?.let {
            if (getCurrentLocale().language == "bn")
                Glide.with(it)
                    .asGif()
                    .load("https://palaocorona.xyz/gif/Gif_Bangla.gif")
                    .into(ivAboutCovid)
            if (getCurrentLocale().language == "en")
                Glide.with(it)
                    .asGif()
                    .load("https://palaocorona.xyz/gif/Gif_English.gif")
                    .into(ivAboutCovid)
        }
    }
}