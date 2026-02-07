package com.machmudow.kaszowska.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.machmudow.kaszowska.model.TrainingOffer
import com.machmudow.kaszowska.utils.loadJsonFromResources
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
fun rememberTrainingOfferStateHolder(): TrainingOfferStateHolder {
    LaunchedEffect(Unit) {
        if (!TrainingOfferStateHolder.state.value.isLoaded) {
            try {
                val trainingOffer = loadJsonFromResources<TrainingOffer>("training_offer.json")
                TrainingOfferStateHolder.updateState {
                    it.copy(
                        trainingOffer = trainingOffer,
                        isVisible = true,
                        isLoaded = true
                    )
                }
            } catch (e: Exception) {
                console.log("Error loading training_offer.json: ${e.message}")
                TrainingOfferStateHolder.updateState {
                    it.copy(isVisible = true, isLoaded = true)
                }
            }
        }
    }

    return TrainingOfferStateHolder
}
