package ninja.saad.palaocorona.ui.features.testyourself

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.marginStart
import androidx.lifecycle.Observer
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_test_yourself.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.AppChip
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.data.testyourself.model.Question
import ninja.saad.palaocorona.util.TYPE
import ninja.saad.palaocorona.util.VIEW_TYPE
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.dimen
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.textColorResource
import java.util.*
import kotlin.random.Random

class TestYourselfFragment: BaseFragment<TestYourselfViewModel>() {
    
    
    private var views = mutableListOf<View>()
    
    override val layoutId: Int
        get() = R.layout.fragment_test_yourself
    
    override fun onCreate(savedInstanceState: Bundle?) {
        isActivityAsViewModelLifeCycleOwner = true
        super.onCreate(savedInstanceState)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel.getQuestionnaire()
        
        viewModel.questionnaire.observe(this, Observer { questions ->
            
            questions.forEach { question ->
                if(question.viewType == VIEW_TYPE.RADIO.value) {
                    if(question.type == TYPE.NORMAL.value) {
                        setupNormalRadio(question)
                    } else if(question.type == TYPE.CHIP.value) {
                        setupChipRadio(question)
                    }
                    
                }
            }
        })
        
    }
    
    private fun setupNormalRadio(question: Question) {
        var mView = LayoutInflater.from(context).inflate(R.layout.item_radio_normal, null)
        val childIds = mutableListOf<Int>()
        mView.id = Random.nextInt()
        (mView as LinearLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        mView.findViewById<MaterialTextView>(childIds[0]).text = question.title
        question.texts.forEach {
            val radio = RadioButton(context).apply {
                text = it
                id = Random.nextInt()
            }
            val radioGroup = mView.findViewById<RadioGroup>(childIds[1])
            radioGroup.clearCheck()
            radioGroup.addView(radio)
            radio.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) viewModel.setAnswer(question, it)
                radio.isChecked = isChecked
            }
        }
        
        viewPool.addView(mView)
        val constraintSet = ConstraintSet()
        constraintSet.clone(viewPool)
        constraintSet.connect(mView.id, ConstraintSet.TOP,
            if(views.size > 0) views.last().id else viewPool.id,
            if(views.size > 0) ConstraintSet.BOTTOM else ConstraintSet.TOP)
        constraintSet.applyTo(viewPool)
        views.add(mView)
    }
    
    private fun setupChipRadio(question: Question) {
        var mView = LayoutInflater.from(context).inflate(R.layout.item_radio_chip, null)
        val childIds = mutableListOf<Int>()
        mView.id = Random.nextInt()
        (mView as LinearLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        mView.findViewById<MaterialTextView>(childIds[0]).text = question.title
        question.texts.forEach {
            val chip = Chip(requireContext()).apply {
                text = it
                id = Random.nextInt()
                isCheckable = true
                textColorResource = R.color.white
                isCheckedIconVisible = false
                setChipBackgroundColorResource(R.color.colorPrimary)
            }
            val chipGroup = mView.findViewById<ChipGroup>(childIds[1])
            chipGroup.isSingleSelection = true
            chipGroup.addView(chip)
            chip.onCheckedChange { buttonView, isChecked ->
                
                if(isChecked) chip.setChipBackgroundColorResource(R.color.colorAccent)
                else chip.setChipBackgroundColorResource(R.color.colorPrimary)
                
                if(isChecked) viewModel.setAnswer(question, it)
                chip.isChecked = isChecked
            }
        }
    
        viewPool.addView(mView)
        val constraintSet = ConstraintSet()
        constraintSet.clone(viewPool)
        constraintSet.connect(mView.id, ConstraintSet.TOP,
            if(views.size > 0) views.last().id else viewPool.id,
            if(views.size > 0) ConstraintSet.BOTTOM else ConstraintSet.TOP)
        constraintSet.applyTo(viewPool)
        views.add(mView)
    }
    
    private fun setupButtonRadio(question: Question) {
    
    }
}