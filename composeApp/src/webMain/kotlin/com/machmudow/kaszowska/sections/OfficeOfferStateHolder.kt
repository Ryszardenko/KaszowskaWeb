package com.machmudow.kaszowska.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.machmudow.kaszowska.model.OfficeOffer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OfficeOfferState(
    val officeOffer: OfficeOffer? = null,
    val isVisible: Boolean = false,
    val isLoaded: Boolean = false
)

object OfficeOfferStateHolder {
    private val _state = MutableStateFlow(OfficeOfferState())
    val state: StateFlow<OfficeOfferState> = _state.asStateFlow()

    fun updateState(update: (OfficeOfferState) -> OfficeOfferState) {
        _state.update(update)
    }
}

@Composable
fun rememberOfficeOfferStateHolder(
    data: OfficeOffer?,
): OfficeOfferStateHolder {
    LaunchedEffect(data) {
        if (!OfficeOfferStateHolder.state.value.isLoaded && data != null) {
            OfficeOfferStateHolder.updateState {
                it.copy(
                    officeOffer = data,
                    isVisible = true,
                    isLoaded = true
                )
            }
        }
    }

    return OfficeOfferStateHolder
}
