package com.jcstudio.mobilecodingacademy.learning.concurrency.model

enum class CoroutineId {
    A,
    B
}

enum class TimelineOperation {
    READ,
    MODIFY,
    WRITE
}

data class RaceConditionTimelineStep(
    val stepNumber: Int,
    val activeCoroutine: CoroutineId,
    val operation: TimelineOperation,
    val sharedCounterValue: Int,
    val coroutineALocalValue: Int?,
    val coroutineBLocalValue: Int?,
    val title: String,
    val explanation: String,
    val lostUpdateDetected: Boolean = false,
)