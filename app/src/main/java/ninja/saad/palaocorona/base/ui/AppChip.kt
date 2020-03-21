package ninja.saad.palaocorona.base.ui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import ninja.saad.palaocorona.R
import org.jetbrains.anko.backgroundColorResource

class AppChip @JvmOverloads constructor(context: Context,
                                        attributeSet: AttributeSet? = null,
                                        defStyle: Int = 0) : Chip(context, attributeSet, defStyle) {
    
    init {
        setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                compoundButton.backgroundColorResource = R.color.colorAccent
            } else {
                compoundButton.backgroundColorResource = R.color.colorPrimary
            }
        }
    }
}