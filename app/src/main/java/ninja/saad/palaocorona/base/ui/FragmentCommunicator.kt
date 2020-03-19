package ninja.saad.palaocorona.base.ui

import android.os.Bundle

interface FragmentCommunicator {

    fun startActivity(clz: Class<*>?, bundle: Bundle?)
}