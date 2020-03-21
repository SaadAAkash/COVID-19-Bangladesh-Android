package ninja.saad.palaocorona.ui.features.testyourself

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.marginStart
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kotlinx.android.synthetic.main.fragment_test_yourself.*
import kotlinx.android.synthetic.main.item_drop_down_option.view.*
import ninja.saad.palaocorona.R
import ninja.saad.palaocorona.base.ui.AppChip
import ninja.saad.palaocorona.base.ui.BaseFragment
import ninja.saad.palaocorona.data.testyourself.ButtonTypeRadioAdapter
import ninja.saad.palaocorona.data.testyourself.model.Question
import ninja.saad.palaocorona.util.GridSpacingItemDecoration
import ninja.saad.palaocorona.util.TYPE
import ninja.saad.palaocorona.util.VIEW_TYPE
import org.jetbrains.anko.backgroundColorResource
import org.jetbrains.anko.dimen
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.jetbrains.anko.support.v4.dimen
import org.jetbrains.anko.textColorResource
import java.util.*
import kotlin.math.max
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
                    } else {
                        setupButtonRadio(question)
                    }
                    
                } else if(question.viewType == VIEW_TYPE.DROPDOWN.value) {
                    setupDropDown(question)
                } else if(question.viewType == VIEW_TYPE.CHECKBOX.value) {
                    setupCheckbox(question)
                } else if(question.viewType == VIEW_TYPE.EDITABLE.value) {
                    setupEditText(question)
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
        views.add(mView)
    }
    
    private fun setupButtonRadio(question: Question) {
        var mView = LayoutInflater.from(context).inflate(R.layout.item_radio_button, null)
        val childIds = mutableListOf<Int>()
        mView.id = Random.nextInt()
        (mView as LinearLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        mView.findViewById<MaterialTextView>(childIds[0]).text = question.title
    
        mView.findViewById<RecyclerView>(childIds[1]).layoutManager =
            GridLayoutManager(requireContext(), max(question.spanCount, 1))
        mView.findViewById<RecyclerView>(childIds[1]).adapter =
            ButtonTypeRadioAdapter(object: ButtonTypeRadioAdapter.ButtonTypeRadioAdapterCallback {
                    
                    override fun onItemClick(position: Int) {
                        viewModel.setAnswer(question, position)
                    }
                }).apply {
                    setItems(texts = question.texts, images = question.images)
                }
        
        viewPool.addView(mView)
        viewPool.invalidate()
        mView.invalidate()
        mView.post {
            mView.layoutParams.width = viewPool.measuredWidth
        }
        views.add(mView)
    }
    
    private fun setupDropDown(question: Question) {
        var mView = LayoutInflater.from(context).inflate(R.layout.item_drop_down_option, null)
        val childIds = mutableListOf<Int>()
        mView.id = Random.nextInt()
        (mView as LinearLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        (mView.findViewById<TextInputLayout>(childIds[1]).children.first() as FrameLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        mView.findViewById<MaterialTextView>(childIds[0]).text = question.title
        val actv = mView.findViewById<TextInputLayout>(childIds[1]).findViewById<AutoCompleteTextView>(childIds[3])
        if(question.hint.isNotEmpty()) mView.findViewById<TextInputLayout>(childIds[1]).hint = question.hint
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, question.texts)
        actv.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                viewModel.setAnswer(question, position)
            }
        actv.setAdapter(arrayAdapter)
        
        viewPool.addView(mView)
        views.add(mView)
    }
    
    private fun setupCheckbox(question: Question) {
        var mView = LayoutInflater.from(context).inflate(R.layout.item_checkbox, null)
        val childIds = mutableListOf<Int>()
        mView.id = Random.nextInt()
        (mView as LinearLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        mView.findViewById<AppCompatCheckBox>(childIds[0]).text = question.title
        mView.findViewById<AppCompatCheckBox>(childIds[0]).setOnCheckedChangeListener { _, isChecked ->
            viewModel.setChecked(question, isChecked)
        }
    
        viewPool.addView(mView)
        views.add(mView)
    }
    
    private fun setupEditText(question: Question) {
        var mView = LayoutInflater.from(context).inflate(R.layout.item_edit_text, null)
        val childIds = mutableListOf<Int>()
        mView.id = Random.nextInt()
        (mView as LinearLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        (mView.findViewById<TextInputLayout>(childIds[1]).children.first() as FrameLayout).children.forEach {
            val id = Random.nextInt()
            childIds.add(id)
            it.id = id
        }
        mView.findViewById<MaterialTextView>(childIds[0]).text = question.title
        val et = mView.findViewById<TextInputLayout>(childIds[1]).findViewById<TextInputEditText>(childIds[3])
        if(question.hint.isNotEmpty()) mView.findViewById<TextInputLayout>(childIds[1]).hint = question.hint
        et.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(text: Editable?) {
                viewModel.setEditableAnswer(question, text.toString())
            }
    
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            
            }
    
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            
            }
    
        })
        viewPool.addView(mView)
        views.add(mView)
    }
}