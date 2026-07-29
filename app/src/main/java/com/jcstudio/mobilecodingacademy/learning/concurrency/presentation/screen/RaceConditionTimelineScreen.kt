package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.CoroutineId
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components.timeline.CoroutineLane
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components.timeline.SharedCounterCard
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state.RaceConditionTimelineUiState
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.viewmodel.ConcurrencyLabViewModel

@Composable
fun RaceConditionTimelineScreen(
    modifier: Modifier = Modifier,
    viewModel: ConcurrencyLabViewModel = viewModel(),
) {
    val timelineUiState by viewModel.timeLineUiState.collectAsStateWithLifecycle()

    RaceConditionTimelineContent(
        modifier = modifier,
        uiState = timelineUiState,
        onPreviousClick = viewModel::previousTimelineStep,
        onNextClick = viewModel::nextTimelineStep,
        onRestartClick = viewModel::restartTimeline
    )
}

@Composable
fun RaceConditionTimelineContent(
    modifier: Modifier = Modifier,
    uiState: RaceConditionTimelineUiState,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    onRestartClick: () -> Unit,
) {

    val currentStep = uiState.currentStep

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Race Condition Timeline",
            style = MaterialTheme.typography.headlineSmall
        )

        if (currentStep == null) {
            Text(text = "No timeline steps available")
            return@Column
        }

        Text(
            text = "Step ${uiState.currentStepIndex + 1} of ${uiState.totalSteps}",
            style = MaterialTheme.typography.labelLarge
        )

        Text(
            text = currentStep.title,
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CoroutineLane(
                coroutineId = CoroutineId.A,
                currentStep = currentStep,
                modifier = Modifier.weight(1f)
            )

            CoroutineLane(
                coroutineId = CoroutineId.B,
                currentStep = currentStep,
                modifier = Modifier.weight(1f)
            )
        }

        SharedCounterCard(
            sharedCounterValue = currentStep.sharedCounterValue,
            coroutineALocalValue = currentStep.coroutineALocalValue,
            coroutineBLocalValue = currentStep.coroutineBLocalValue,
            lostUpdateDetected = currentStep.lostUpdateDetected
        )

        Text(
            text = currentStep.explanation,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedButton(
                onClick = onPreviousClick,
                enabled = uiState.canGoPrevious,
                modifier = Modifier.weight(1f)
            ) {
                Text("Previous")
            }

            Button(
                onClick = onNextClick,
                enabled = uiState.canGoNext,
                modifier = Modifier.weight(1f)
            ) {
                Text("Next")
            }

            OutlinedButton(
                onClick = onRestartClick,
                modifier = Modifier.weight(1f)
            ) {
                Text("Restart")
            }
        }
    }
}
