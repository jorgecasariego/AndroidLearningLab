package com.jcstudio.mobilecodingacademy.learning.concurrency.experiments

import com.jcstudio.mobilecodingacademy.learning.concurrency.model.CoroutineId
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.TimelineStep
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.TimelineOperation

class RaceConditionTimelineExperiment: TimelineExperiment {
    override val title: String
        get() = "Race Condition"

    override fun getSteps(): List<TimelineStep> {
        return listOf(
            TimelineStep(
                stepNumber = 1,
                activeCoroutine = CoroutineId.A,
                operation = TimelineOperation.READ,
                sharedCounterValue = 0,
                coroutineALocalValue = 0,
                coroutineBLocalValue = null,
                title = "Coroutine A reads the counter",
                explanation = """
                    Coroutine A reads the shared value 0 and stores it locally.
                    The shared counter has not changed yet.
                """.trimIndent()
            ),
            TimelineStep(
                stepNumber = 2,
                activeCoroutine = CoroutineId.B,
                operation = TimelineOperation.READ,
                sharedCounterValue = 0,
                coroutineALocalValue = 0,
                coroutineBLocalValue = 0,
                title = "Coroutine B reads the counter",
                explanation = """
                    Coroutine B reads the shared value 0 and stores it locally.
                    The shared counter has not changed yet.
                """.trimIndent()
            ),
            TimelineStep(
                stepNumber = 3,
                activeCoroutine = CoroutineId.A,
                operation = TimelineOperation.MODIFY,
                sharedCounterValue = 0,
                coroutineALocalValue = 1,
                coroutineBLocalValue = 0,
                title = "Coroutine A increments locally",
                explanation = """
                    Coroutine A adds 1 to its local value.
                    The shared counter is still 0 because nothing has been written yet.
                """.trimIndent()
            ),
            TimelineStep(
                stepNumber = 4,
                activeCoroutine = CoroutineId.B,
                operation = TimelineOperation.MODIFY,
                sharedCounterValue = 0,
                coroutineALocalValue = 1,
                coroutineBLocalValue = 1,
                title = "Coroutine B increments locally",
                explanation = """
                    Coroutine B adds 1 to its local value.
                    The shared counter is still 0 because nothing has been written yet.
                """.trimIndent()
            ),
            TimelineStep(
                stepNumber = 5,
                activeCoroutine = CoroutineId.A,
                operation = TimelineOperation.WRITE,
                sharedCounterValue = 1,
                coroutineALocalValue = 1,
                coroutineBLocalValue = 1,
                title = "Coroutine A writes 1",
                explanation = """
                    Coroutine A writes its local value to the shared counter.
                    The shared counter changes from 0 to 1.
                """.trimIndent()
            ),
            TimelineStep(
                stepNumber = 6,
                activeCoroutine = CoroutineId.B,
                operation = TimelineOperation.WRITE,
                sharedCounterValue = 1,
                coroutineALocalValue = 1,
                coroutineBLocalValue = 1,
                title = "Coroutine B overwrites the counter",
                explanation = """
                    Coroutine B also writes 1.
                    The counter remains 1, even though two increments were executed.
                """.trimIndent(),
                lostUpdateDetected = true
            )
        )
    }
}