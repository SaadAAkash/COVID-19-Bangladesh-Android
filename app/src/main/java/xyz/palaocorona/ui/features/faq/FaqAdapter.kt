package xyz.palaocorona.ui.features.faq
/*

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_faq_2.*
import kotlinx.android.synthetic.main.item_faq_2.view.*
import xyz.palaocorona.R
import xyz.palaocorona.data.faq.Faq
import xyz.palaocorona.util.AnimationManager
import org.jetbrains.anko.sdk27.coroutines.onClick

class FaqAdapter: RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    
    private var items = mutableListOf<Faq>()
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq_2, parent, false)
        return FaqViewHolder(view)
    }
    
    override fun getItemCount(): Int {
        return items.size
    }
    
    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val item = items[position]
        holder.itemView.faqTitle.text = item.title
        holder.itemView.faqBody.text = item.body
        initViews(holder)
    }
    
    fun setItems(items: MutableList<Faq>) {
        this.items = items
        notifyDataSetChanged()
    }
    
    class FaqViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }
    
    private fun initViews(holder: FaqViewHolder) {
        holder.itemView.btn_expand.onClick {
            toggleSectionInfo(holder.itemView.btn_expand, holder);
        }
        */
/*holder.itemView.btn_hide.onClick {
            toggleSectionInfo(holder.itemView.btn_hide, holder);
        }*//*

    }
    
    private fun toggleSectionInfo(view: View, holder: FaqViewHolder) {
        val show: Boolean = toggleArrow(view)
        if (show) {
            AnimationManager().expand(holder.itemView.layout_faq_answer, object : AnimationManager.AnimListener {
                override fun onFinish() {
                    //Tools.nestedScrollTo(nested_scroll_view, layout_faq_answer)
                }
            })
        } else {
            AnimationManager().collapse(holder.itemView.layout_faq_answer)
        }
    }
    
    fun toggleArrow(view: View): Boolean {
        return if (view.rotation == 0f) {
            view.animate().setDuration(200).rotation(180f)
            true
        } else {
            view.animate().setDuration(200).rotation(0f)
            false
        }
    }
}*/
