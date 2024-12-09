package io.captaingaga.airtickets.effective.mobile.search.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.wrapAsAppResult
import io.captaingaga.airtickets.effective.mobile.data.mappers.toOfferTicketModelList
import io.captaingaga.airtickets.effective.mobile.data.models.OfferTicketModel
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchOffersTicketsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OffersTicketsViewModel(
    private val useCase: FetchOffersTicketsUseCase
) : ViewModel() {

    private val _offersTickets =
        MutableStateFlow<AppResult<List<OfferTicketModel>>>(AppResult.Loading)
    val offersTickets = _offersTickets.asStateFlow()

    init {
        fetchOffersTickets()
    }

    private fun fetchOffersTickets() {
        viewModelScope.launch {
            useCase().wrapAsAppResult()
                .onEach { appResult ->
                    when (appResult) {
                        is AppResult.Failure -> AppResult.Failure(appResult.exception)
                        AppResult.Loading -> _offersTickets.update { AppResult.Loading }
                        is AppResult.Success -> _offersTickets.update { AppResult.Success(appResult.data.toOfferTicketModelList()) }
                    }
                }.collect()
        }
    }
}