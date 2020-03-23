package ninja.saad.palaocorona.data.testyourself.model

import com.google.gson.annotations.SerializedName
import ninja.saad.palaocorona.util.TYPE
import ninja.saad.palaocorona.util.VIEW_TYPE

data class Question(

	@SerializedName("images")
	val images: MutableList<String> = mutableListOf(),

	@SerializedName("texts")
	val texts: MutableList<String> = mutableListOf(),

	@SerializedName("view_type")
	val viewType: String = "",

	@SerializedName("title")
	val title: String = "",

	@SerializedName("type")
	val type: String = "",
	
	@SerializedName("span_count")
	var spanCount: Int = 0,
	
	@SerializedName("hint")
	var hint: String = "",
	
	@SerializedName("visible_on")
	var visibleOn: Int = 0,
	
	@SerializedName("check_on")
	var checkOn: Int = 0

) {
	
	var selectedAnswerPosition = 0
	var selectedAnswer: String = ""
	var isChecked = false
}