package ninja.saad.palaocorona.ui.features.faq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.google.android.material.textview.MaterialTextView
import ninja.saad.palaocorona.R
import java.util.HashMap
class FAQExpandableListAdapter(private val context: Context?, private val expandableListTitle: List<String>,
                               private val expandableListDetail: HashMap<String, List<String>>
) : BaseExpandableListAdapter() {
    
    override fun getChild(listPosition: Int, expandedListPosition: Int): String? {
        return this.expandableListDetail[this.expandableListTitle[listPosition]]?.get(expandedListPosition)
    }
    
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    
    override fun getChildView(listPosition: Int, expandedListPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup
    ): View? {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context
                ?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_faq_content, null)
        }
        val expandedListTextView = convertView?.findViewById<MaterialTextView>(R.id.expandedListItem)
        expandedListTextView?.text = expandedListText
        return convertView
    }
    
    override fun getChildrenCount(listPosition: Int): Int {
        expandableListDetail.let {
            it[this.expandableListTitle[listPosition]].let {
                if (it != null) {
                    return it.size
                } else
                    return 0
            }
        }
        /*return this.expandableListDetail[this.expandableListTitle[listPosition]]!!
                .size*/
    }
    
    override fun getGroup(listPosition: Int): Any {
        return this.expandableListTitle[listPosition]
    }
    
    override fun getGroupCount(): Int {
        return this.expandableListTitle.size
    }
    
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    
    override fun getGroupView(listPosition: Int, isExpanded: Boolean,
                              convertView: View?, parent: ViewGroup
    ): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater = this.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.item_faq_header, null)
        }
        val listTitleTextView = convertView!!
            .findViewById<MaterialTextView>(R.id.listTitle)
        listTitleTextView.text = listTitle
        return convertView
    }
    
    override fun hasStableIds(): Boolean {
        return false
    }
    
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }
}