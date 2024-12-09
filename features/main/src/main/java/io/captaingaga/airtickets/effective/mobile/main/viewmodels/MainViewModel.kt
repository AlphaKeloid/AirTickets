package io.captaingaga.airtickets.effective.mobile.main.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.captaingaga.airtickets.effective.mobile.common.AppResult
import io.captaingaga.airtickets.effective.mobile.common.wrapAsAppResult
import io.captaingaga.airtickets.effective.mobile.data.mappers.toOfferModelList
import io.captaingaga.airtickets.effective.mobile.data.models.OfferModel
import io.captaingaga.airtickets.effective.mobile.domain.usecases.FetchOffersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val useCase: FetchOffersUseCase
) : ViewModel() {

    private val _offers = MutableStateFlow<AppResult<List<OfferModel>>>(AppResult.Loading)
    val offer = _offers.asStateFlow()

    private val _textFrom = MutableStateFlow("")
    val textFrom = _textFrom.asStateFlow()

    init {
        fetchOffers()
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            useCase().wrapAsAppResult()
                .onEach { appResult ->
                    when (appResult) {
                        is AppResult.Failure -> AppResult.Failure(appResult.exception)
                        AppResult.Loading -> _offers.update { AppResult.Loading }
                        is AppResult.Success -> _offers.update { AppResult.Success(appResult.data.toOfferModelList()) }
                    }
                }.collect()
        }
    }

    // TODO: save to storage
    fun updateTextFrom(input: String) = _textFrom.update { input }
}