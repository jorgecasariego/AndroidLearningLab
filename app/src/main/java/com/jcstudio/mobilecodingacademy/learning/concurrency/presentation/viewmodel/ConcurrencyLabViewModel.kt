package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jcstudio.mobilecodingacademy.learning.concurrency.experiments.RaceConditionExperiment
import com.jcstudio.mobilecodingacademy.learning.concurrency.experiments.RaceConditionTimelineExperiment
import com.jcstudio.mobilecodingacademy.learning.concurrency.experiments.TimelineExperiment
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.ConcurrencyLog
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state.ConcurrencyLabUiState
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state.RaceConditionTimelineUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConcurrencyLabViewModel : ViewModel() {
    private val raceConditionExperiment = RaceConditionExperiment()

    private val experiment: TimelineExperiment = RaceConditionTimelineExperiment()

    private val _uiState = MutableStateFlow(ConcurrencyLabUiState())
    val uiState: StateFlow<ConcurrencyLabUiState> = _uiState.asStateFlow()

    private val _timelineUiState = MutableStateFlow(
        RaceConditionTimelineUiState(
            steps = experiment.getSteps()
        )
    )

    val timeLineUiState: StateFlow<RaceConditionTimelineUiState> = _timelineUiState.asStateFlow()

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
            /*
            *   Why should we use update()?
            *   "Because update performs the transformation atomically.
            *   If multiple coroutines modify the same StateFlow concurrently,
            *   using value = value.copy(...) can lose updates because it performs a
            *   read-modify-write sequence. update() avoids that race condition."
            * */
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

    fun nextTimelineStep() {
        _timelineUiState.update { currenState ->
            if (currenState.canGoNext) {
                currenState.copy(
                    currentStepIndex = currenState.currentStepIndex + 1
                )
            } else {
                currenState
            }
        }
    }

    fun previousTimelineStep() {
        _timelineUiState.update { currentState ->
            if (currentState.canGoPrevious) {
                currentState.copy(
                    currentStepIndex = currentState.currentStepIndex - 1
                )
            } else {
                currentState
            }
        }
    }

    fun restartTimeline() {
        _timelineUiState.update { currentState ->
            currentState.copy(
                currentStepIndex = 0
            )
        }
    }
}