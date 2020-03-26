package ninja.saad.palaocorona.ui.features.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_slider.view.*
import ninja.saad.palaocorona.R

class SliderAdapter(var source: String, val language: String = "en") : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    
    val itemsDashboardBn = mutableListOf<Any>(R.drawable.ic_myth_slider_5_bn, R.drawable.ic_myth_slider_2_bn, R.drawable.ic_myth_slider_3_bn, R.drawable.ic_myth_slider_4_bn, R.drawable.ic_myth_slider_1_bn)
    val itemsDashboardEn = mutableListOf<Any>(R.drawable.ic_myth_slider_5_en, R.drawable.ic_myth_slider_2_en, R.drawable.ic_myth_slider_3_en, R.drawable.ic_myth_slider_4_en, R.drawable.ic_myth_slider_1_en)
    val itemsQuarantineEn = mutableListOf<Int>(R.drawable.ic_quarantine_1_en, R.drawable.ic_quarantine_2_en, R.drawable.ic_quarantine_3_en, R.drawable.ic_quarantine_4_en, R.drawable.ic_quarantine_5_en, R.drawable.ic_quarantine_6_en)
    val itemsQuarantineBn = mutableListOf<Int>(R.drawable.ic_quarantine_1_bn, R.drawable.ic_quarantine_2_bn, R.drawable.ic_quarantine_3_bn, R.drawable.ic_quarantine_4_bn, R.drawable.ic_quarantine_5_bn, R.drawable.ic_quarantine_6_bn)
    val itemsDos = mutableListOf<Int>(R.drawable.ic_dos_and_donts_1, R.drawable.ic_dos_and_donts_2, R.drawable.ic_dos_and_donts_3, R.drawable.ic_dos_and_donts_4, R.drawable.ic_dos_and_donts_5)
    
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slider, parent, false)
        return SliderViewHolder(view)
    }
    
    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        when (source) {
            "dashboard" -> {
                if(language == "bn"){
                    Glide.with(viewHolder.sliderImage).load(itemsDashboardBn[position])
                        .into(viewHolder.sliderImage)
                } else {
                    Glide.with(viewHolder.sliderImage).load(itemsDashboardEn[position])
                        .into(viewHolder.sliderImage)
                }
            }
            "quarantine" -> {
                viewHolder.sliderImage.scaleType = ImageView.ScaleType.CENTER_CROP
                if(language == "bn") {
                    Glide.with(viewHolder.sliderImage).load(itemsQuarantineBn[position])
                        .into(viewHolder.sliderImage)
                } else {
                    Glide.with(viewHolder.sliderImage).load(itemsQuarantineEn[position])
                        .into(viewHolder.sliderImage)
                }
            }
            "dos" -> {
                Glide.with(viewHolder.sliderImage).load(itemsDos[position]).into(viewHolder.sliderImage)
            }
        }
        
        
    }
    
    override fun getCount(): Int {
        when (source) {
            "dashboard" -> {
                return itemsDashboardEn.size
            }
            "quarantine" -> {
               return itemsQuarantineEn.size
            }
            "dos" -> {
               return itemsDos.size
            }
        }
        return itemsDashboardEn.size
    }
    
    fun addSliderToDashboard(items: MutableList<String>) {
        this.itemsDashboardEn.addAll(items)
        this.itemsDashboardBn.addAll(items)
        notifyDataSetChanged()
    }
    
    class SliderViewHolder(view: View): SliderViewAdapter.ViewHolder(view) {
        val sliderImage = view.ivSlider
    }
}