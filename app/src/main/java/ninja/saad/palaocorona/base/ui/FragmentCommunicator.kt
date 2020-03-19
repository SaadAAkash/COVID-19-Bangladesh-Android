package ninja.saad.palaocorona.base.ui

import android.os.Bundle
import java.util.*

interface FragmentCommunicator {

    fun startActivity(clz: Class<*>?, bundle: Bundle?)
    fun toggleLanguage()
}