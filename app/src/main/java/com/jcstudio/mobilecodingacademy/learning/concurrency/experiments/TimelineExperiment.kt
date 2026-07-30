package com.jcstudio.mobilecodingacademy.learning.concurrency.experiments

import com.jcstudio.mobilecodingacademy.learning.concurrency.model.TimelineStep

interface TimelineExperiment {
    val title: String

    fun getSteps(): List<TimelineStep>
}