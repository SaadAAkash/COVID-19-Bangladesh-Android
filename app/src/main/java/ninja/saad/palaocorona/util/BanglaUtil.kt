package ninja.saad.palaocorona.util

val banglaNumberMap = mapOf("0" to "০",  "1" to "১", "2" to "২", "3" to "৩", "4" to "৪",
    "5" to "৫", "6" to "৬", "7" to "৭", "8" to "৮", "9" to "৯")
val englishNumberMap = mapOf("০" to "0",  "১" to "1", "২" to "2", "৩" to "3", "৪" to "4",
    "৫" to "5", "৬" to "6", "৭" to "7", "৮" to "8", "৯" to "9")


fun String.toBanglaNumber(): String {
    var banglaNumber = ""
    forEach {
        banglaNumber += if(banglaNumberMap.contains(it.toString()))
            banglaNumberMap[it.toString()]
        else
            it.toString()
    }
    return banglaNumber
}

fun String.toEnglishNumber(): String {
    var englishNumber = ""
    forEach {
        englishNumber += if(englishNumberMap.contains(it.toString()))
            englishNumberMap[it.toString()]
        else
            it.toString()
    }
    return englishNumber
}