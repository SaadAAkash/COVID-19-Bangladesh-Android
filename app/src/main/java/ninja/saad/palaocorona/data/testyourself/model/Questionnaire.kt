package ninja.saad.palaocorona.data.testyourself.model

import com.google.gson.annotations.SerializedName

data class Questionnaire(

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

)