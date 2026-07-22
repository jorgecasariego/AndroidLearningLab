package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcstudio.mobilecodingacademy.learning.concurrency.experiments.RaceConditionExperiment
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.ConcurrencyLog
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state.ConcurrencyLabUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConcurrencyLabViewModel: ViewModel() {
    private val raceConditionExperiment = RaceConditionExperiment()
    private val _uiState = MutableStateFlow(ConcurrencyLabUiState())
    val uiState: StateFlow<ConcurrencyLabUiState> = _uiState.asStateFlow()

    fun runRaceConditionExperiment() {
        viewModelScope.launch {
            _uiState.value = ConcurrencyLabUiState(
                isRunning = true,
                logs = listOf(
                    ConcurrencyLog("Experiment started"),
                    ConcurrencyLog("Creating 100 coroutines"),
                    ConcurrencyLog("Each coroutine will perform 1,000 increments")
                )
            )

            val result = raceConditionExperiment.run()

            // update nos permite modificar el estado de forma atomica
            _uiState.update { currentState ->
                currentState.copy(
                    isRunning = false,
                    expectedValue = result.expectedValue,
                    actualValue = result.actualValue,
                    lostUpdates = result.lostUpdates,
                    logs = currentState.logs + listOf(
                        ConcurrencyLog("All coroutines completed"),
                        ConcurrencyLog("Expected value: ${result.expectedValue}"),
                        ConcurrencyLog("Actual value: ${result.actualValue}"),
                        ConcurrencyLog("Lost updates: ${result.lostUpdates}"),
                    )
                )
            }
        }
    }
}