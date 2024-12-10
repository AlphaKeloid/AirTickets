package io.captaingaga.airtickets.effective.mobile.main.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.wrapAsAppResult
import io.captaingaga.airtickets.effective.mobile.data.mappers.toDomainSearch
import io.captaingaga.airtickets.effective.mobile.data.mappers.toOfferModelList
import io.captaingaga.airtickets.effective.mobile.data.mappers.toSearchModel
import io.captaingaga.airtickets.effective.mobile.data.models.OfferModel
import io.captaingaga.airtickets.effective.mobile.data.models.SearchModel
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchOffersUseCase
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FindLastDestinationUseCase
import io.captaingaga.airtickets.effective.mobile.domain.usecases.InsertDestinationUseCase
import io.captaingaga.airtickets.effective.mobile.main.components.RouteParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val state: SavedStateHandle,
    private val fetchUseCase: FetchOffersUseCase,
    private val insertUseCase: InsertDestinationUseCase,
    private val findLastUseCase: FindLastDestinationUseCase,
) : ViewModel() {

    private val _offers = MutableStateFlow<AppResult<List<OfferModel>>>(AppResult.Loading)
    val offer = _offers.asStateFlow()

    private val _params = MutableStateFlow(
        state.getStateFlow(KEY_ROUTE, RouteParams()).value
    )
    val params = _params.asStateFlow()

    init {
        fetchOffers()
        findLastDestination()
    }

    private fun findLastDestination() {
        viewModelScope.launch {
            findLastUseCase().collect { last ->
                val search = last.toSearchModel()
                _params.update { it.copy(departFrom = search.destination) }
                state[KEY_ROUTE] = params.value
            }
        }
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            fetchUseCase().wrapAsAppResult()
                .onEach { appResult ->
                    when (appResult) {
                        is AppResult.Failure -> AppResult.Failure(appResult.exception)
                        AppResult.Loading -> _offers.update { AppResult.Loading }
                        is AppResult.Success -> _offers.update { AppResult.Success(appResult.data.toOfferModelList()) }
                    }
                }.collect()
        }
    }

    fun updateDepart(from: String) {
        _params.update { it.copy(departFrom = from) }
        viewModelScope.launch {
            insertUseCase(SearchModel(1, from).toDomainSearch())
        }
        state[KEY_ROUTE] = params.value
    }


    fun updateArrive(to: String) {
        _params.update { it.copy(arriveTo = to) }
        state[KEY_ROUTE] = params.value
    }

    private companion object {
        const val KEY_ROUTE = "ROUTE"
    }
}