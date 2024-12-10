package io.captaingaga.airtickets.effective.mobile.main.di

import io.captaingaga.airtickets.effective.mobile.main.viewmodels.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featureMainModule = module {
    viewModelOf(::MainViewModel)
}