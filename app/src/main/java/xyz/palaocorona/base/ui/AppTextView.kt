package xyz.palaocorona.base.ui

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textview.MaterialTextView
import xyz.palaocorona.R

class AppTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : MaterialTextView(context, attrs, defStyleAttr) {

    init {
        setTextIsSelectable(true)
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        val textString = text.toString()
        val spannable = SpannableString(textString)
        var banglaTextStarted = false
        var startIndex = 0
        val textSizeProportion = 1.65f

        val engFont = Typeface.create(ResourcesCompat.getFont(context, R.font.popins), Typeface.NORMAL)
        spannable.setSpan(CustomTypefaceSpan("", engFont), 0, text?.length ?: 0, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        
        for(i in textString.indices) {
            if(banglaTextStarted && (textString.codePointAt(i) < 2433 ||
                        textString.codePointAt(i) > 2552)) {
                banglaTextStarted = false
                val font = ResourcesCompat.getFont(context, R.font.noto_sans)
                spannable.setSpan(CustomTypefaceSpan("", font), startIndex, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            } else if(!banglaTextStarted && textString.codePointAt(i) in 2433..2552) {
                banglaTextStarted = true
                startIndex = i
            }
        }
        super.setText(spannable, type)
    }
}