package com.namma.raste.health.ai

import android.graphics.Bitmap
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import javax.inject.Inject

class GeminiService @Inject constructor() {
    
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "YOUR_GEMINI_API_KEY" // Placeholder
    )

    suspend fun analyzeRoad(bitmap: Bitmap): RoadAnalysisResult = withContext(Dispatchers.IO) {
        try {
            val response = generativeModel.generateContent(
                content {
                    image(bitmap)
                    text("Analyze this road image. Detect whether the road is healthy or damaged. " +
                            "Identify potholes, cracks, uneven surfaces, water damage, or broken roads. " +
                            "Return JSON: { \"damageType\":\"\", \"severity\":\"\", \"confidence\":\"\", \"urgency\":\"\" }")
                }
            )
            
            val jsonText = response.text ?: "{}"
            val json = JSONObject(jsonText.substringAfter("{").substringBeforeLast("}").let { "{$it}" })
            
            RoadAnalysisResult(
                damageType = json.optString("damageType", "Unknown"),
                severity = json.optString("severity", "Low"),
                confidence = json.optString("confidence", "0%"),
                urgency = json.optString("urgency", "Normal")
            )
        } catch (e: Exception) {
            throw e
        }
    }
}

data class RoadAnalysisResult(
    val damageType: String,
    val severity: String,
    val confidence: String,
    val urgency: String
)
