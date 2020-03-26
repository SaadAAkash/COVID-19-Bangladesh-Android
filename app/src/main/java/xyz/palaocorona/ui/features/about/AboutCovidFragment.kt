package xyz.palaocorona.ui.features.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_about_covid.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseFragment
import xyz.palaocorona.base.ui.BaseViewModel

class AboutCovidFragment: BaseFragment<BaseViewModel>()  {
    
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