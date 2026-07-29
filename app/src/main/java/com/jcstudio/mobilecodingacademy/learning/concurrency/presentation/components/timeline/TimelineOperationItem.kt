package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.components.timeline

import android.R
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jcstudio.mobilecodingacademy.learning.concurrency.model.TimelineOperation
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.model.TimelineOperationStatus

@Composable
fun TimelineOperationItem(
    operation: TimelineOperation,
    status: TimelineOperationStatus,
    modifier: Modifier = Modifier
) {
    val containerColor = when(status) {
        TimelineOperationStatus.PENDING -> MaterialTheme.colorScheme.surfaceVariant
        TimelineOperationStatus.CURRENT -> MaterialTheme.colorScheme.primaryContainer
        TimelineOperationStatus.COMPLETED -> MaterialTheme.colorScheme.secondaryContainer
    }

    val borderColor = when(status) {
        TimelineOperationStatus.PENDING -> Color.Transparent
        TimelineOperationStatus.CURRENT -> MaterialTheme.colorScheme.primary
        TimelineOperationStatus.COMPLETED -> MaterialTheme.colorScheme.secondary
    }

    val statusSymbol = when(status) {
        TimelineOperationStatus.PENDING -> "○"
        TimelineOperationStatus.CURRENT -> "●"
        TimelineOperationStatus.COMPLETED -> "✓"
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        color = containerColor
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 10.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = statusSymbol,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = operation.name,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineOperationItemPreview() {
    MaterialTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TimelineOperationItem(
                operation = TimelineOperation.READ,
                status = TimelineOperationStatus.COMPLETED,
            )

            TimelineOperationItem(
                operation = TimelineOperation.MODIFY,
                status = TimelineOperationStatus.CURRENT,
            )

            TimelineOperationItem(
                operation = TimelineOperation.WRITE,
                status = TimelineOperationStatus.PENDING,
            )
        }
    }
}