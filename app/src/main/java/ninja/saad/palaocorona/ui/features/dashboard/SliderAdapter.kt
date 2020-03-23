package ninja.saad.palaocorona.ui.features.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_slider.view.*
import ninja.saad.palaocorona.R

class SliderAdapter(var source: String) : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    
    val itemsDashboard = mutableListOf<Any>(R.drawable.ic_myth_slider_5, R.drawable.ic_myth_slider_2, R.drawable.ic_myth_slider_3, R.drawable.ic_myth_slider_4, R.drawable.ic_myth_slider_1)
    val itemsQuarantine = mutableListOf<Int>(R.drawable.ic_quarantine_1_en, R.drawable.ic_quarantine_2_en, R.drawable.ic_quarantine_3_en, R.drawable.ic_quarantine_4_en, R.drawable.ic_quarantine_5_en, R.drawable.ic_quarantine_6_en)
    val itemsDos = mutableListOf<Int>(R.drawable.ic_myth_slider_1, R.drawable.ic_myth_slider_2, R.drawable.ic_myth_slider_3, R.drawable.ic_myth_slider_4, R.drawable.ic_myth_slider_5)
    
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }
    
    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        when (source) {
            "dashboard" -> {
                Glide.with(viewHolder.sliderImage).load(itemsDashboard[position]).into(viewHolder.sliderImage)
            }
            "quarantine" -> {
                Glide.with(viewHolder.sliderImage).load(itemsQuarantine[position]).into(viewHolder.sliderImage)
            }
            "dos" -> {
                Glide.with(viewHolder.sliderImage).load(itemsDos[position]).into(viewHolder.sliderImage)
            }
        }
        
        
    }
    
    override fun getCount(): Int {
        when (source) {
            "dashboard" -> {
                return itemsDashboard.size
            }
            "quarantine" -> {
               return itemsQuarantine.size
            }
            "dos" -> {
               return itemsDos.size
            }
        }
        return itemsDashboard.size
    }
    
    fun addSliderToDashboard(items: MutableList<String>) {
        this.itemsDashboard.addAll(items)
        notifyDataSetChanged()
    }
    
    class SliderViewHolder(view: View): SliderViewAdapter.ViewHolder(view) {
        val sliderImage = view.ivSlider
    }
}