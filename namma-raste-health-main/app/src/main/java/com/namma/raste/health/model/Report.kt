package com.namma.raste.health.model

data class Report(
    val damageType: String,
    val severity: String,
    val confidence: String,
    val urgency: String
)