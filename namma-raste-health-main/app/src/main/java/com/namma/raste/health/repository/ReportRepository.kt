package com.namma.raste.health.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.namma.raste.health.model.RoadReport
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val reportsCollection = firestore.collection("road_reports")

    suspend fun submitReport(report: RoadReport) {
        reportsCollection.add(report).await()
    }

    suspend fun getMyReports(userId: String): List<RoadReport> {
        return reportsCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(RoadReport::class.java)
    }

    suspend fun getAllReports(): List<RoadReport> {
        return reportsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .await()
            .toObjects(RoadReport::class.java)
    }
    
    suspend fun updateReportStatus(reportId: String, status: String) {
        reportsCollection.document(reportId).update("status", status).await()
    }
}
