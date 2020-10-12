package com.eliong92.kopii.di

import com.eliong92.kopii.MainActivity
import com.eliong92.kopii.MainFragmentProvider
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    abstract fun bindMainActivity(): MainActivity
}