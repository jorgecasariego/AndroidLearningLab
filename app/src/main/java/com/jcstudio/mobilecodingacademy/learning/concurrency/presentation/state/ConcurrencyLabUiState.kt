package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.state

import com.jcstudio.mobilecodingacademy.learning.concurrency.model.ConcurrencyLog

data class ConcurrencyLabUiState(
    val isRunning: Boolean = false,
    val expectedValue: Int? = null,
    val actualValue: Int? = null,
    val lostUpdates: Int? = null,
    val logs: List<ConcurrencyLog> = emptyList()
)