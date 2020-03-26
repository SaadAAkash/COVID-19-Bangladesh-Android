package xyz.palaocorona.data.testyourself.model

import com.google.gson.annotations.SerializedName

data class Question(
	
	@SerializedName("images")
	val images: MutableList<String> = mutableListOf(),
	
	@SerializedName("texts")
	val texts: MutableList<LocaleData> = mutableListOf(),
	
	@SerializedName("view_type")
	val viewType: String = "",
	
	@SerializedName("title")
	val title: LocaleData = LocaleData(),
	
	@SerializedName("type")
	val type: String = "",
	
	@SerializedName("span_count")
	var spanCount: Int = 0,
	
	@SerializedName("hint")
	var hint: String = "",
	
	@SerializedName("visible_on")
	var visibleOn: Int = 0,
	
	@SerializedName("check_on")
	var checkOn: Int = 0,
	
	@SerializedName("level")
	var level: Int = 1,
	
	@SerializedName("single_selection")
	var singleSelection: Boolean = true

) {
	
	var selectedAnswerPosition = 0
	var selectedAnswer: LocaleData = LocaleData()
	var isChecked = false
}

data class LocaleData(
	@SerializedName("en") var englishText: String = "",
	@SerializedName("bn") var banglaText: String = ""
)