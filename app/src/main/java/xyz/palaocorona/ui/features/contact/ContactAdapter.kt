package xyz.palaocorona.ui.features.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.android.synthetic.main.item_contact_head.view.*
import xyz.palaocorona.R
import xyz.palaocorona.data.contact.Contact
import xyz.palaocorona.util.toEnglishNumber
import org.jetbrains.anko.sdk27.coroutines.onClick

class ContactAdapter(val callback: ContactAdapterCallback): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    
    private var items = mutableListOf<Contact>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == 1) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact_head, parent, false)
            return ContactHeaderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
            return ContactViewHolder(view)
        }
    }
    
    override fun getItemCount(): Int {
        return items.size
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ContactViewHolder) {
            
            holder.itemView.tvName.text = items[position].title
            holder.itemView.tvContactInfo.text = items[position].subtitle
            holder.itemView.onClick {
                callback.onItemClick(items[position].subtitle.toEnglishNumber())
            }
            holder.itemView.btnCall.onClick {
                holder.itemView.performClick()
            }
            holder.itemView.tvName.onClick {
                holder.itemView.performClick()
            }
            holder.itemView.tvContactInfo.onClick {
                holder.itemView.performClick()
            }
            
        } else if(holder is ContactHeaderViewHolder) {
            holder.itemView.onClick {
            
            }
            holder.itemView.tvContactHead.text = items[position].title
            
        }
    }
    
    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }
    
    fun setItems(items: MutableList<Contact>) {
        this.items = items
        notifyDataSetChanged()
    }
    
    class ContactViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }
    
    class ContactHeaderViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }
    
    interface ContactAdapterCallback {
        fun onItemClick(number: String)
    }
}