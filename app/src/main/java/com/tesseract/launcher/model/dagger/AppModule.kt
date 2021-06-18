package com.tesseract.launcher.model.dagger

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var app: Application) {
    @Singleton
    @Provides
    fun provideApplication(): Application {
        return app
    }


    @Singleton
    @Provides
    fun provideString(app: Application): StringService {
        return StringService(app)
    }
}