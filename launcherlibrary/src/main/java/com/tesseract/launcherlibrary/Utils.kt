package com.tesseract.launcherlibrary

import android.content.pm.ApplicationInfo

fun isUserApp(ai: ApplicationInfo): Boolean {
    val mask = ApplicationInfo.FLAG_SYSTEM or ApplicationInfo.FLAG_UPDATED_SYSTEM_APP
    return (ai.flags and mask) == 0
}