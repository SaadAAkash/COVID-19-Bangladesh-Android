package xyz.palaocorona.ui.features.news

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import xyz.palaocorona.R
import xyz.palaocorona.base.ui.BaseFragment

class NewsFragment: BaseFragment<NewsViewModel>(), NewsAdapter.NewsAdapterCallback {
    
    override val layoutId: Int
        get() = R.layout.fragment_news
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        rvNews.layoutManager = LinearLayoutManager(context)
        rvNews.adapter = NewsAdapter(this)
        
        viewModel.news.observe(viewLifecycleOwner, Observer {
            (rvNews.adapter as NewsAdapter).setItems(it)
        })
        
        viewModel.getNews(getCurrentLocale().language, 0)
    }
    
    override fun onNewsClick(url: String) {
        try {
            val webIntent = Intent(Intent.ACTION_VIEW)
            val Url = url
            webIntent.data = Uri.parse(Url)
            startActivity(webIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    override fun loadMore(size: Int) {
        viewModel.getNews(getCurrentLocale().language, size)
    }
}