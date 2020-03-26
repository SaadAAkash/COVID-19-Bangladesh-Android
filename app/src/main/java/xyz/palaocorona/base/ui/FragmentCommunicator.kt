package xyz.palaocorona.base.ui

import android.os.Bundle
import java.util.*

interface FragmentCommunicator {

    fun startActivity(clz: Class<*>, bundle: Bundle? = null)
    fun toggleLanguage()
    fun getCurrentLocale(): Locale
    fun onFragmentResume()
    fun showLoader()
    fun hideLoader()
}