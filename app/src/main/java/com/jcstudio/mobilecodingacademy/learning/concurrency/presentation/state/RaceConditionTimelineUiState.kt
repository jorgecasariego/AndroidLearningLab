package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state

import com.jcstudio.mobilecodingacademy.learning.concurrency.model.TimelineStep

data class RaceConditionTimelineUiState(
    val steps: List<TimelineStep> = emptyList(),
    val currentStepIndex: Int = 0
) {
    val currentStep: TimelineStep?
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