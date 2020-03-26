package ninja.saad.palaocorona.data.authentication.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String = "",
    var age: String = "",
    var gender: String = "",
    var phoneNumber: String = ""
): Parcelable