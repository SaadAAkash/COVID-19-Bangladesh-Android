package xyz.palaocorona.ui.dialogs

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_no_internet_connection.*
import xyz.palaocorona.R
import org.jetbrains.anko.sdk27.coroutines.onClick

class NoInternetConnectionDialog(context: Context,
                                 val callback: NoInternetDialogCallback?): AlertDialog(context) {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_no_internet_connection)
        btnRetry.onClick {
            callback?.retry()
            dismiss()
        }
        if(callback == null) btnRetry.visibility = View.INVISIBLE
        else btnRetry.visibility = View.VISIBLE
    }
    
    interface NoInternetDialogCallback {
        fun retry()
    }
}