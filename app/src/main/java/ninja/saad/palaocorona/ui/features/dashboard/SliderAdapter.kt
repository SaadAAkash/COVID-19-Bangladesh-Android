package ninja.saad.palaocorona.ui.features.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_slider.view.*
import ninja.saad.palaocorona.R

class SliderAdapter : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    
    val items = mutableListOf<Int>(R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground)
    
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }
    
    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        Glide.with(viewHolder.sliderImage).load(items[position]).into(viewHolder.sliderImage)
    }
    
    override fun getCount(): Int {
        return items.size
    }
    
    class SliderViewHolder(view: View): SliderViewAdapter.ViewHolder(view) {
        val sliderImage = view.ivSlider
    }
}