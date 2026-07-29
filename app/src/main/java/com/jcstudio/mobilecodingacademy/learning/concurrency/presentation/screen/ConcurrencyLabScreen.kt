package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components.LogItem
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components.ResultRow
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state.ConcurrencyLabUiState
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.viewmodel.ConcurrencyLabViewModel

@Composable
fun ConcurrencyLabScreen(
    modifier: Modifier = Modifier,
    viewModel: ConcurrencyLabViewModel = viewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ConcurrencyLabContent(
        modifier = modifier,
        uiState = uiState,
        onRunClick = viewModel::runRaceConditionExperiment
    )
}

@Composable
fun ConcurrencyLabContent(
    modifier: Modifier = Modifier,
    uiState: ConcurrencyLabUiState,
    onRunClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Race Condition Experiment",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRunClick,
            enabled = !uiState.isRunning,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (uiState.isRunning) {
                    "Running"
                } else {
                    "Run Experiment"
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        uiState.expectedValue?.let {
            ExperimentResultSection(uiState)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Execution Log",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.logs) { log ->
                LogItem(log)
            }
        }
    }
}

@Composable
fun ExperimentResultSection(
    uiState: ConcurrencyLabUiState,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ResultRow(
            label = "Expected",
            value = uiState.expectedValue.toString()
        )

        ResultRow(
            label = "Actual",
            value = uiState.actualValue.toString()
        )

        ResultRow(
            label = "Lost updates",
            value = uiState.lostUpdates.toString()
        )

        val hasRaceCondition = (uiState.lostUpdates ?: 0) > 0

        Text(
            text =  if (hasRaceCondition) {
                "Race condition detected"
            } else {
                "Not lost updates detected in this run"
            },
            style = MaterialTheme.typography.titleSmall
        )
    }
}