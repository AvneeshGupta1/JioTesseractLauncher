package com.tesseract.launcher.utils

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import java.util.ArrayList

fun isMyAppLauncherDefault(context: Context): Boolean {
    val filter = IntentFilter(Intent.ACTION_MAIN)
    filter.addCategory(Intent.CATEGORY_HOME)
    val filters: MutableList<IntentFilter> = ArrayList()
    filters.add(filter)
    val myPackageName = context.packageName
    val activities: List<ComponentName> = ArrayList()
    val packageManager = context.packageManager as PackageManager
    packageManager.getPreferredActivities(filters, activities, null)
    for (activity in activities) {
        if (myPackageName == activity.packageName) {
            return true
        }
    }
    return false
}
