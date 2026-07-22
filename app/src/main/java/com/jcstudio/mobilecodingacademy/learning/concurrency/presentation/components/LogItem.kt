package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.ConcurrencyLog

@Composable
fun LogItem(
    log: ConcurrencyLog
) {
    Card(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = log.message,
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}