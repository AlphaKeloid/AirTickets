package io.captaingaga.airtickets.effective.mobile.main.di

import io.captaingaga.airtickets.effective.mobile.main.viewmodels.MainViewModel
import io.captaingaga.airtickets.effective.mobile.main.viewmodels.NavParamsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureMainModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { NavParamsViewModel() }
}