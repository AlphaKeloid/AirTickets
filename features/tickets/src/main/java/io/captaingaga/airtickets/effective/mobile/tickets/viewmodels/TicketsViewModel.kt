package io.captaingaga.airtickets.effective.mobile.tickets.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.wrapAsAppResult
import io.captaingaga.airtickets.effective.mobile.data.mappers.toTicketModelList
import io.captaingaga.airtickets.effective.mobile.data.models.TicketModel
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchTicketsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TicketsViewModel(
    private val useCase: FetchTicketsUseCase
) : ViewModel() {

    private val _tickets = MutableStateFlow<AppResult<List<TicketModel>>>(AppResult.Loading)
    val tickets = _tickets.asStateFlow()

    init {
        fetchTickets()
    }

    private fun fetchTickets() {
        viewModelScope.launch {
            useCase().wrapAsAppResult()
                .onEach { appResult ->
                    when (appResult) {
                        is AppResult.Failure -> AppResult.Failure(appResult.exception)
                        AppResult.Loading -> _tickets.update { AppResult.Loading }
                        is AppResult.Success -> _tickets.update { AppResult.Success(appResult.data.toTicketModelList()) }
                    }
                }.collect()
        }
    }
}