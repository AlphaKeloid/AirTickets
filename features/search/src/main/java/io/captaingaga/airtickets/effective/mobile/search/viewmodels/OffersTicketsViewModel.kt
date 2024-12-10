package io.captaingaga.airtickets.effective.mobile.search.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.wrapAsAppResult
import io.captaingaga.airtickets.effective.mobile.data.mappers.toOfferTicketModelList
import io.captaingaga.airtickets.effective.mobile.data.models.OfferTicketModel
import io.captaingaga.airtickets.effective.mobile.data.utils.toFormattedDate
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchOffersTicketsUseCase
import io.captaingaga.airtickets.effective.mobile.search.comppnents.RouteParam
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant

class OffersTicketsViewModel(
    private val useCase: FetchOffersTicketsUseCase,
    private val state: SavedStateHandle,
    private val from: String,
    private val to: String
) : ViewModel() {

    private val _offersTickets =
        MutableStateFlow<AppResult<List<OfferTicketModel>>>(AppResult.Loading)
    val offersTickets = _offersTickets.asStateFlow()

    private val _route = MutableStateFlow(
        state.getStateFlow(KEY_ROUTE, RouteParam(from, to)).value
    )
    val route = _route.asStateFlow()

    private val _date = MutableStateFlow(
        state.getStateFlow(KEY_DATE, Instant.now().toEpochMilli().toFormattedDate()).value
    )
    val date = _date.asStateFlow()


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

    fun updateRoute(inputFrom: String, inputTo: String) {
        _route.update { it.copy(from = inputFrom, to = inputTo) }
        state[KEY_ROUTE] = route.value
    }

    fun updateDate(input: String) {
        _date.update { input }
        state[KEY_DATE] = date.value
    }

    private companion object {
        const val KEY_ROUTE = "route"
        const val KEY_DATE = "date"
    }
}