package io.captaingaga.airtickets.effective.mobile.main.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NavParamsViewModel : ViewModel() {

    private val _params = MutableStateFlow(NavParams())
    val params = _params.asStateFlow()

    fun updateDepart(from: String) = _params.update { it.copy(departFrom = from) }

    fun updateArrive(to: String) = _params.update { it.copy(arriveTo = to) }

    data class NavParams(
        val departFrom: String = "",
        val arriveTo: String = ""
    )
}