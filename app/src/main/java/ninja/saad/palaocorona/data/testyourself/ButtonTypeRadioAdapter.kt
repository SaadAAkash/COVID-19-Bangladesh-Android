package ninja.saad.palaocorona.data.testyourself

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_button_option.view.*
import ninja.saad.palaocorona.R
import org.jetbrains.anko.dimen
import org.jetbrains.anko.sdk27.coroutines.onClick
import kotlin.math.max

class ButtonTypeRadioAdapter(private val callback: ButtonTypeRadioAdapterCallback): RecyclerView.Adapter<ButtonTypeRadioAdapter.ButtonTypeRadioViewHolder>() {
    
    private var texts = mutableListOf<String>()
    private var images = mutableListOf<String>()
    private var currentSelectedItem = -1
    private lateinit var parent: ViewGroup
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonTypeRadioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button_option, parent, false)
        this.parent = parent
        return ButtonTypeRadioViewHolder(view)
    }
    
    override fun getItemCount(): Int {
        return max(texts.size, images.size)
    }
    
    override fun onBindViewHolder(holder: ButtonTypeRadioViewHolder, position: Int) {
        
        holder.itemView.tvOption.visibility = View.VISIBLE
        holder.itemView.ivOption.visibility = View.GONE
        holder.itemView.tvOption.backgroundTintList =
            ContextCompat.getColorStateList(holder.itemView.context, R.color.colorPrimary)
        if(currentSelectedItem == position) {
            holder.itemView.tvOption.backgroundTintList =
                ContextCompat.getColorStateList(holder.itemView.context, R.color.colorAccent)
        }
        
        if(position < texts.size && images.size == 0) {
            holder.itemView.tvOption.text = texts[position]
        } else if( position < texts.size && images.size == texts.size) {
            holder.itemView.tvOption.text = texts[position]
            Glide.with(holder.itemView.context)
                .load(images[position])
                .addListener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        e?.printStackTrace()
                        return false
                    }
    
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.tvOption.icon = resource
                        return false
                    }
    
                }).preload()
        } else if(position < images.size && images.size > 0) {
            holder.itemView.tvOption.visibility = View.GONE
            holder.itemView.ivOption.visibility = View.VISIBLE
            Glide.with(holder.itemView.context)
                .load(images[position])
                .into(holder.itemView.ivOption)
            (holder.itemView as MaterialCardView).radius = holder.itemView.context.dimen(R.dimen._10sdp).toFloat()
            (holder.itemView as MaterialCardView).layoutParams.height = holder.itemView.context.dimen(R.dimen._36sdp)
            (holder.itemView as MaterialCardView).layoutParams.width = holder.itemView.context.dimen(R.dimen._36sdp)
            if(position == currentSelectedItem) {
                (holder.itemView as MaterialCardView).strokeWidth =
                    holder.itemView.context.dimen(R.dimen._1sdp)
            } else {
                (holder.itemView as MaterialCardView).strokeWidth = 0
            }
        }
        
        holder.itemView.tvOption.onClick {
            performClick(position, holder)
        }
        holder.itemView.ivOption.onClick {
            performClick(position, holder)
        }
    
        holder.itemView.onClick {
            performClick(position, holder)
        }
    }
    
    fun setItems(texts: MutableList<String> = mutableListOf(),
                 images: MutableList<String> = mutableListOf()) {
        this.texts = texts
        this.images = images
        notifyDataSetChanged()
    }
    
    private fun performClick(position: Int, holder: ButtonTypeRadioViewHolder) {
        
        currentSelectedItem = position
        notifyDataSetChanged()
        callback.onItemClick(position)
    }
    
    class ButtonTypeRadioViewHolder(val view: View): RecyclerView.ViewHolder(view), LayoutContainer {
        override val containerView: View?
            get() = view
    }
    
    interface ButtonTypeRadioAdapterCallback{
        fun onItemClick(position: Int)
    }
}