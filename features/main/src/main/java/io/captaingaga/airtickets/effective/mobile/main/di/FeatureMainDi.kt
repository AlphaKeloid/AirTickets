package io.captaingaga.airtickets.effective.mobile.main.di

import io.captaingaga.airtickets.effective.mobile.main.viewmodels.OffersViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureMainModule = module {
    viewModel { OffersViewModel(get()) }
}