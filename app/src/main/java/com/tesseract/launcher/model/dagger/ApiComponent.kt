package com.tesseract.launcher.model.dagger

import com.tesseract.launcher.viewmodel.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface ApiComponent {
    fun inject(viewModel: HomeViewModel)
}
