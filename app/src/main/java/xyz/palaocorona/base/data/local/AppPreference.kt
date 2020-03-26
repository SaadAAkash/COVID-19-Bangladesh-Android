package xyz.palaocorona.base.data.local

import xyz.palaocorona.data.authentication.model.User

interface AppPreference {
    
    var user: User
    var language: String
}