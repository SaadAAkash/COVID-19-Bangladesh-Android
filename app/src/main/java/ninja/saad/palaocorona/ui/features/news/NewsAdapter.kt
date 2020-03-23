package ninja.saad.palaocorona.ui.features.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.data.news.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    
    private var items = mutableListOf<News>()
    
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
        /*holder.itemView.tvTitle.text = item.title
        holder.itemView.tvBody.text = item.body*/
    }
    
    fun setItems(items: MutableList<News>) {
        this.items = items
        notifyDataSetChanged()
    }
    
    
    class NewsViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }

}