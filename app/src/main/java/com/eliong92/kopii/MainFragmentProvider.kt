package com.eliong92.kopii

import com.eliong92.kopii.viewModel.MainViewModelModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {
    @ContributesAndroidInjector(modules = [MainViewModelModule::class])
    abstract fun provideMainFragmentFactory(): MainFragment
}