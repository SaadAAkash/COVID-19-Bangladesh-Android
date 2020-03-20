package ninja.saad.palaocorona.data.authentication.model

data class User(
    var name: String = "",
    var age: String = "",
    var gender: Long = 0L,
    var phoneNumber: String = ""
)