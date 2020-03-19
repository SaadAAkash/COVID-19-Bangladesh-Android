package ninja.saad.palaocorona.ui.features.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_faq.view.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.data.faq.Faq

class FaqAdapter: RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    
    private var items = mutableListOf<Faq>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FaqViewHolder(view)
    }
    
    override fun getItemCount(): Int {
        return items.size
    }
    
    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.tvTitle.text = item.title
        holder.itemView.tvBody.text = item.body
    }
    
    fun setItems(items: MutableList<Faq>) {
        this.items = items
        notifyDataSetChanged()
    }
    
    
    class FaqViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }
}