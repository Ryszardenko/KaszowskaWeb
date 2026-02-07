package com.machmudow.kaszowska.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.machmudow.kaszowska.model.TrainingOffer
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response

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
    val json = remember {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }

    LaunchedEffect(Unit) {
        if (!TrainingOfferStateHolder.state.value.isLoaded) {
            try {
                val response =
                    window.fetch("composeResources/kaszowska.composeapp.generated.resources/files/training_offer.json")
                        .await<Response>()
                val jsonString = response.text().await<String>()
                val trainingOffer = json.decodeFromString<TrainingOffer>(jsonString)
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
