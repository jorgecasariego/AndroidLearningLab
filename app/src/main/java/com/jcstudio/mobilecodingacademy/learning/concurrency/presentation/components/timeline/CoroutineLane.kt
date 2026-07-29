package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.CoroutineId
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.RaceConditionTimelineStep
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.TimelineOperation
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.model.TimelineOperationStatus

@Composable
fun CoroutineLane(
    coroutineId: CoroutineId,
    currentStep: RaceConditionTimelineStep,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Coroutine ${coroutineId.name}",
            style = MaterialTheme.typography.titleMedium
        )

        TimelineOperation.entries.forEach { operation ->
            TimelineOperationItem(
                operation = operation,
                status = getOperationStatus(
                    operation = operation,
                    coroutineId = coroutineId,
                    currentStep = currentStep
                )
            )

        }
    }
}

/*
        Nuestra secuencia siempre es:

        1 A READ
        2 B READ
        3 A MODIFY
        4 B MODIFY
        5 A WRITE
        6 B WRITE
*
* */
private fun getOperationStatus(
    operation: TimelineOperation,
    coroutineId: CoroutineId,
    currentStep: RaceConditionTimelineStep,
): TimelineOperationStatus {

    val operationStepNumber = when (coroutineId to operation) {
        CoroutineId.A to TimelineOperation.READ -> 1
        CoroutineId.B to TimelineOperation.READ -> 2
        CoroutineId.A to TimelineOperation.MODIFY -> 3
        CoroutineId.B to TimelineOperation.MODIFY -> 4
        CoroutineId.A to TimelineOperation.WRITE -> 5
        CoroutineId.B to TimelineOperation.WRITE -> 6
        else -> error("Unsupported coroutine operation")
    }

    /*
        Ejemplo:
            coroutineId = CoroutineId.B
            operation = TimelineOperation.MODIFY
            currentStep.stepNumber = 4

        Obtenemos:

            operationStepNumber = 4
            currentStep.stepNumber = 4

            Return: TimelineOperationStatus.CURRENT

         Ejemplo 2:
            coroutineId = CoroutineId.A
            operation = TimelineOperation.MODIFY
            operationStepNumber = 3
            currentStep = 4

            Return: TimelineOperationStatus.COMPLETED
     */
    return when {
        operationStepNumber < currentStep.stepNumber -> TimelineOperationStatus.COMPLETED
        operationStepNumber == currentStep.stepNumber -> TimelineOperationStatus.CURRENT
        else -> TimelineOperationStatus.PENDING
    }
}