package xyz.palaocorona.base.data.local

import android.content.Context
import com.google.gson.Gson
import xyz.palaocorona.R
import xyz.palaocorona.data.authentication.model.User
import javax.inject.Inject

class AppPreferenceImpl @Inject constructor(context: Context): AppPreference {
    
    companion object {
        const val USER = "user"
        private const val LANGUAGE_KEY = "language_key"
    }
    
    private var preference =
        context.getSharedPreferences(context.getString(R.string.pref_name), Context.MODE_PRIVATE)
    private var editor = preference.edit()
    
    override var user: User
        get() = getObject(USER, User::class.java)
        set(value) {
            saveObject(USER, value)
        }
    
    override var language: String
        get() = getString(LANGUAGE_KEY, "en")
        set(value) {
            saveString(LANGUAGE_KEY, value)
        }
    
    private fun saveString(key: String, value: String) {
        editor.putString(key, value).apply()
    }
    
    private fun getString(key: String, defaultValue: String = ""): String {
        return preference.getString(key, defaultValue) ?: defaultValue
    }
    
    private fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }
    
    private fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return preference.getBoolean(key, defaultValue)
    }
    
    private fun saveInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }
    
    private fun getInt(key: String, defaultValue: Int): Int {
        return preference.getInt(key, defaultValue)
    }
    
    private fun saveFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }
    
    private fun getFloat(key: String, defaultValue: Float): Float {
        return preference.getFloat(key, defaultValue)
    }
    
    private fun saveLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }
    
    private fun getLong(key: String, defaultValue: Long): Long {
        return preference.getLong(key, defaultValue)
    }
    
    private fun saveObject(key: String, value: Any) {
        val valueString = Gson().toJson(value)
        saveString(key, valueString)
    }
    
    private fun <T>getObject(key: String, clazz: Class<T>): T {
        return Gson().fromJson<T>(preference.getString(key, "{}"), clazz)
    }
}