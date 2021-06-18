package com.tesseract.launcherlibrary

import android.graphics.drawable.Drawable

class AppBackListElement(
    val title: String = "",
    val packageName: String? = null,
    var mIcon: Drawable? = null,
    var versionCode: String? = null,
    var versionName: String? = null,
    var parentActivityName: String? = null,
    val isSystemApp: Boolean
)
