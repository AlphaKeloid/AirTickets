package io.captaingaga.airtickets.effective.mobile.tickets.di

import io.captaingaga.airtickets.effective.mobile.tickets.viewmodels.TicketsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val ticketsModule = module {
    viewModel { TicketsViewModel(get()) }
}