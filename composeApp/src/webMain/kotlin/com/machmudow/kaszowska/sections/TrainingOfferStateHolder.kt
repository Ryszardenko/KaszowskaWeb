package com.machmudow.kaszowska.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.machmudow.kaszowska.model.TrainingOffer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// Immutable state holder for training offer data
data class TrainingOfferState(
    val trainingOffer: TrainingOffer? = null,
    val isVisible: Boolean = false,
    val isLoaded: Boolean = false
)

// Singleton StateFlow holder for training offer state
object TrainingOfferStateHolder {
    private val _state = MutableStateFlow(TrainingOfferState())
    val state: StateFlow<TrainingOfferState> = _state.asStateFlow()

    fun updateState(update: (TrainingOfferState) -> TrainingOfferState) {
        _state.update(update)
    }
}

@Composable
fun rememberTrainingOfferStateHolder(
    data: TrainingOffer?,
): TrainingOfferStateHolder {
    LaunchedEffect(data) {
        if (!TrainingOfferStateHolder.state.value.isLoaded && data != null) {
            TrainingOfferStateHolder.updateState {
                it.copy(
                    trainingOffer = data,
                    isVisible = true,
                    isLoaded = true
                )
            }
        }
    }

    return TrainingOfferStateHolder
}
