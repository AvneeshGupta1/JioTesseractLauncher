package com.tesseract.launcherlibrary

import android.content.Context
import android.content.Intent
import android.os.Build

class Launcher {

    companion object {
        fun getAppsIntoList(mContext: Context): ArrayList<AppBackListElement> {
            val appList = ArrayList<AppBackListElement>()

            val i = Intent(Intent.ACTION_MAIN)
            i.addCategory(Intent.CATEGORY_LAUNCHER)
            val mPm = mContext.packageManager
            val resolvedApplist = mPm.queryIntentActivities(
                Intent(
                    Intent.ACTION_MAIN,
                    null
                ).addCategory(Intent.CATEGORY_LAUNCHER), 0
            )


            for (ri in resolvedApplist) {
                try {
                    if (mContext.packageName != ri.activityInfo.packageName) {
                        val info = mPm.getPackageInfo(ri.activityInfo.packageName, 0)
                        val launchIntent: Intent? =
                            mPm.getLaunchIntentForPackage(ri.activityInfo.packageName)
                        val className = launchIntent?.component!!.className.className()
                        val isSystemApp = !isUserApp(ri.activityInfo.applicationInfo)
                        val ah = AppBackListElement(
                            ri.loadLabel(mPm).toString(),
                            ri.activityInfo.packageName,
                            ri.activityInfo.loadIcon(mPm),
                            info.versionName,
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) info.longVersionCode.toString()
                            else info.versionCode.toString(),
                            className,
                            isSystemApp
                        )
                        appList.add(ah)
                    }
                } catch (e: Exception) {
                }
            }
            appList.sortBy { it.title }
            return appList
        }

        fun String.className(): String {
            return this.substring(lastIndexOf(".") + 1)
        }
    }
}