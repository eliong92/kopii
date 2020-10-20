package com.eliong92.kopii.di

import android.app.Application
import com.eliong92.kopii.KopiiApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    NetworkModule::class,
    ActivityBuilder::class,
    UseCaseModule::class,
    RepositoryModule::class,
    AppModule::class
])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: KopiiApplication)
}