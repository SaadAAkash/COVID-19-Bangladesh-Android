package ninja.saad.palaocorona.ui.dialogs

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_no_internet_connection.*
import ninja.saad.palaocorona.R
import org.jetbrains.anko.sdk27.coroutines.onClick

class NoInternetConnectionDialog(context: Context,
                                 val callback: NoInternetDialogCallback): AlertDialog(context) {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_no_internet_connection)
        btnRetry.onClick {
            callback.retry()
            dismiss()
        }
    }
    
    interface NoInternetDialogCallback {
        fun retry()
    }
}