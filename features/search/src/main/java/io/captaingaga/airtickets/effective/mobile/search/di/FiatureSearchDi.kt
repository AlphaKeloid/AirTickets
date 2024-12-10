package io.captaingaga.airtickets.effective.mobile.search.di

import io.captaingaga.airtickets.effective.mobile.search.viewmodels.OffersTicketsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureSearchModule = module {
    viewModel { parameters ->
        OffersTicketsViewModel(
            get(),
            get(),
            from = parameters.get(),
            to = parameters.get()
        )
    }
}