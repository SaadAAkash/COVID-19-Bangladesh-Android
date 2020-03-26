package xyz.palaocorona.ui.features.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_news.view.*
import xyz.palaocorona.R
import xyz.palaocorona.data.news.model.News
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val callback: NewsAdapterCallback) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    
    private var items = mutableListOf<News>()
    private var isMoreItemsRequested = false
    private var isEndOfNews = false
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }
    
    override fun getItemCount(): Int {
        return items.size
    }
    
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]
        val url = URL(item.source)
        val baseUrl: String = url.host
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSXXX", Locale.ENGLISH)
        val dateFormatter = SimpleDateFormat("HH:mm, dd MMM, yyyy", Locale.ENGLISH)
        val dateString = dateFormatter.format(formatter.parse(item.time)!!)
        holder.itemView.tvTitle.text = item.title
        holder.itemView.tvSubtitle.text = item.subtitle
        holder.itemView.tvSource.text = baseUrl
        holder.itemView.tvDate.text = dateString
        Glide.with(holder.itemView.context).load(item.imageSrc).placeholder(R.drawable.ic_thumbnail).into(holder.itemView.ivNews)
        holder.itemView.onClick {
            callback.onNewsClick(item.source)
        }
        if((position == items.size - 1) && !isMoreItemsRequested && !isEndOfNews) {
            callback.loadMore(items.size)
            isMoreItemsRequested = true
        }
    }
    
    fun setItems(items: MutableList<News>) {
        if(items.size == 0) isEndOfNews = true
        if(!isMoreItemsRequested) {
            this.items = items
            notifyDataSetChanged()
        } else {
            this.items.addAll(items)
            notifyItemInserted(this.items.size - items.size)
            isMoreItemsRequested = false
        }
    }
    
    class NewsViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }
    
    interface NewsAdapterCallback {
        fun onNewsClick(url: String)
        fun loadMore(size: Int)
    }

}