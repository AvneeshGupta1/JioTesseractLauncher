package com.tesseract.launcher.viewmodel

import android.app.Application
import android.content.pm.PackageManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.tesseract.launcher.model.LauncherApplication
import com.tesseract.launcher.model.dagger.StringService
import com.tesseract.launcherlibrary.AppBackListElement
import com.tesseract.launcherlibrary.Launcher
import org.jetbrains.anko.doAsync
import javax.inject.Inject


class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val mPm: PackageManager

    @Inject
    lateinit var stringService: StringService

    init {
        (application as LauncherApplication).getAppComponent().inject(this)
        mPm = stringService.getAppContext().packageManager
    }

    fun getAppBackList(): MutableLiveData<List<AppBackListElement>> {
        val appBackupResponse: MutableLiveData<List<AppBackListElement>> = MutableLiveData()
        doAsync {
            val list = Launcher.getAppsIntoList(stringService.getAppContext())
            appBackupResponse.postValue(list)
        }
        return appBackupResponse
    }


}