package ninja.saad.palaocorona.ui.features.liveupdates

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_live_updates.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.util.toBanglaNumber
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LiveUpdatesFragment : BaseFragment<LiveUpdatesViewModel>()  {
    
    override val layoutId: Int
    get() = R.layout.fragment_live_updates
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWebView()
        setWebClient()
        handlePullToRefresh()
        handleOnKeyDown()
        loadUrl("https://service.prothomalo.com/commentary/index.php")
        
        tv_cases_source.movementMethod = LinkMovementMethod.getInstance()
        tv_live_update_source.movementMethod = LinkMovementMethod.getInstance()
    
        viewModel.liveUpdates.observe(viewLifecycleOwner, Observer {
            var formattedDate = ""
            formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val parsedDate = LocalDateTime.parse(it.lastUpdate, DateTimeFormatter.ISO_DATE_TIME)
                parsedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
            } else {
                val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                formatter.format(parser.parse(it.lastUpdate))
            }
            if (formattedDate.isNotEmpty()) tv_last_updated.text = "${resources.getString(R.string.last_updated)}\n$formattedDate"
            tv_confirmed.text = if(getCurrentLocale().language == "bn") it.confirmed.value.toString().toBanglaNumber() else it.confirmed.value.toString()
            tv_recovered.text = if(getCurrentLocale().language == "bn") it.recovered.value.toString().toBanglaNumber() else it.recovered.value.toString()
            tv_deaths.text = if(getCurrentLocale().language == "bn") it.deaths.value.toString().toBanglaNumber() else it.deaths.value.toString()
            var temp = it.recovered.value + it.deaths.value
            temp = it.confirmed.value - temp
            tv_active.text =  if(getCurrentLocale().language == "bn") temp.toString().toBanglaNumber() else temp.toString()
        })
    
        viewModel.getLiveUpdates()
    }
    
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        live_view.settings.javaScriptEnabled = true
        live_view.settings.loadWithOverviewMode = true
        live_view.settings.useWideViewPort = true
        live_view.settings.domStorageEnabled = true
        live_view.webViewClient = object : WebViewClient() {
            override
            fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                handler?.proceed()
            }
        }
    }
    private fun setWebClient() {
        live_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                /*progressBar.progress = newProgress
                if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                }*/
            }
        }
    }
    
    private fun handlePullToRefresh() {
    }
    
    private fun handleOnKeyDown() {
        live_view.canGoBack()
        live_view.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && live_view.canGoBack()
            ) {
                live_view.goBack()
                return@OnKeyListener true
            }
            false
        })
    }
    
    private fun loadUrl(pageUrl: String) {
        live_view.loadUrl(pageUrl)
    }
    
    private fun loadData(iframe : String) {
        live_view.loadData(iframe, "text/html", null);
    }
}