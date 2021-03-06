package com.tesseract.launcher.model.dagger

import android.content.Context

class StringService(private val appContext: Context) {

    fun getString(id: Int): String =
        appContext.resources.getString(id)

    fun getAppContext(): Context {
        return appContext
    }

    fun getString(id: Int, vararg formatArgs: Any): String =
        appContext.getString(id, *formatArgs)

    fun getStringArray(id: Int) = appContext.resources.getStringArray(id)

}