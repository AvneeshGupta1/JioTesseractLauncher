package com.tesseract.launcher.model

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.tesseract.launcher.model.dagger.ApiComponent
import com.tesseract.launcher.model.dagger.AppModule
import com.tesseract.launcher.model.dagger.DaggerApiComponent

class LauncherApplication : MultiDexApplication() {
    private lateinit var mAppComponent: ApiComponent
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this);
        mAppComponent = DaggerApiComponent.builder()
            .appModule(AppModule(this)).build()
    }

    fun getAppComponent(): ApiComponent {
        return mAppComponent
    }

}