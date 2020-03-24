package ninja.saad.palaocorona.ui.features.liveupdates

import android.annotation.SuppressLint
import android.net.http.SslError
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_live_updates.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment

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
            tv_confirmed.text = it.confirmed.value.toString()
            tv_recovered.text = it.recovered.value.toString()
            tv_deaths.text = it.deaths.value.toString()
            var temp = it.recovered.value + it.deaths.value
            temp = it.confirmed.value - temp
            tv_active.text =  temp.toString()
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