package com.namma.raste.health.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class RoadReport(
    val id: String = "",
    val userId: String = "",
    val imageBase64: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val address: String = "",
    val damageType: String = "",
    val severity: String = "",
    val confidence: String = "",
    val urgency: String = "",
    val status: String = "Pending", // Pending, In Progress, Resolved
    @ServerTimestamp val timestamp: Timestamp? = null
)
