package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state

import com.jcstudio.mobilecodingacademy.learning.concurrency.model.RaceConditionTimelineStep

data class RaceConditionTimelineUiState(
    val steps: List<RaceConditionTimelineStep> = emptyList(),
    val currentStepIndex: Int = 0
) {
    val currentStep: RaceConditionTimelineStep?
        get() = steps.getOrNull(currentStepIndex)

    val totalSteps: Int
        get() = steps.size

    val canGoPrevious: Boolean
        get() = currentStepIndex > 0

    val canGoNext: Boolean
        get() = currentStepIndex < steps.lastIndex

    val isFinished: Boolean
        get() = steps.isNotEmpty() && currentStepIndex == steps.lastIndex
}