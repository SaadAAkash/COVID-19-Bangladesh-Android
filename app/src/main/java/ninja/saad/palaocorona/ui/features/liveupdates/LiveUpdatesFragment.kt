package ninja.saad.palaocorona.ui.features.liveupdates

import android.os.Bundle
import android.view.View
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.ui.features.about.AboutCovidViewModel
import android.annotation.SuppressLint
import android.net.http.SslError
import android.view.KeyEvent
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import kotlinx.android.synthetic.main.fragment_live_updates.*

class LiveUpdatesFragment : BaseFragment<AboutCovidViewModel>()  {
    
    override val layoutId: Int
    get() = R.layout.fragment_live_updates
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        initWebView()
        setWebClient()
        handlePullToRefresh()
        loadUrl("https://service.prothomalo.com/commentary/index.php")
    }
    
    private fun handlePullToRefresh() {
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
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progressBar.progress = newProgress
                if (newProgress < 100 && progressBar.visibility == ProgressBar.GONE) {
                    progressBar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == 100) {
                    progressBar.visibility = ProgressBar.GONE
                }
            }
        }
    }
    
    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && live_view.canGoBack()) {
            live_view.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, exit the activity)
        return super.onKeyDown(keyCode, event)
    }*/
    
    private fun loadUrl(pageUrl: String) {
        live_view.loadUrl(pageUrl)
    }
    
    
}