package xyz.palaocorona.base.ui

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.LocaleList
import androidx.annotation.RequiresApi
import xyz.palaocorona.base.data.local.AppPreference
import xyz.palaocorona.base.data.local.AppPreferenceImpl
import java.util.*


class LocaleManager(var context: Context) {
    
    private val prefs: AppPreference
    
    fun setLocale(c: Context): Context {
        this.context = c
        return updateResources(c, language)
    }
    
    fun setNewLocale(c: Context, language: String): Context {
        persistLanguage(language)
        return updateResources(c, language)
    }
    
    val language: String
        get() = prefs.language
    
    
    private fun persistLanguage(language: String) {
        prefs.language = language
    }
    
    private fun updateResources(
        context: Context,
        language: String?
    ): Context {
        var context = context
        val locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config =
            Configuration(res.configuration)
        if (isAtLeastVersion(VERSION_CODES.N)) {
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            setLocaleForApi24(config, locale)
            context = context.createConfigurationContext(config)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else if (isAtLeastVersion(VERSION_CODES.JELLY_BEAN_MR1)) {
            
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            config.setLocale(locale)
            context = context.createConfigurationContext(config)
            resources.updateConfiguration(configuration, resources.displayMetrics)
        } else {
            config.locale = locale
            res.updateConfiguration(config, res.displayMetrics)
        }
        return context
    }
    
    @RequiresApi(api = VERSION_CODES.N)
    private fun setLocaleForApi24(
        config: Configuration,
        target: Locale
    ) {
        val set: MutableSet<Locale> =
            LinkedHashSet()
        // bring the target locale to the front of the list
        set.add(target)
        val all = LocaleList.getDefault()
        for (i in 0 until all.size()) {
            // append other locales supported by the user
            set.add(all[i])
        }
        val locales = set.toTypedArray()
        config.setLocales(LocaleList(*locales))
    }
    
    companion object {
        
        fun getLocale(res: Resources): Locale {
            val config = res.configuration
            return if (Build.VERSION.SDK_INT >= VERSION_CODES.N) config.locales[0] else config.locale
        }
    }
    
    fun isAtLeastVersion(version: Int): Boolean {
        return Build.VERSION.SDK_INT >= version
    }
    
    init {
        prefs = AppPreferenceImpl(context)
    }
}