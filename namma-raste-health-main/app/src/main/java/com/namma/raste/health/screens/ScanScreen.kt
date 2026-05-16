package com.namma.raste.health.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.namma.raste.health.model.Report
import com.namma.raste.health.model.ReportStorage
import com.namma.raste.health.viewmodel.ScanViewModel

@Composable
fun ScanScreen(
    navController: NavHostController,
    viewModel: ScanViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    val context = LocalContext.current

    var submitted by remember {
        mutableStateOf(false)
    }

    val galleryLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->

            uri?.let {
                viewModel.onImageSelected(it, context)
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicturePreview()
        ) { bitmap: Bitmap? ->

            bitmap?.let {
                viewModel.onBitmapCaptured(it)
            }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Road Scan",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),

            contentAlignment = Alignment.Center
        ) {

            uiState.selectedBitmap?.let { bitmap ->

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

            } ?: Text("No Image Selected")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Button(
                onClick = {
                    cameraLauncher.launch(null)
                }
            ) {
                Text("Camera")
            }

            Button(
                onClick = {
                    galleryLauncher.launch("image/*")
                }
            ) {
                Text("Gallery")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                submitted = false

                uiState.selectedBitmap?.let {
                    viewModel.analyzeImage(it)
                }
            },

            modifier = Modifier.fillMaxWidth()
        ) {

            if (uiState.isLoading) {

                CircularProgressIndicator()

            } else {

                Text("Analyze Image")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        uiState.analysisResult?.let { result ->

            val roadCondition =
                when (result.severity.lowercase()) {

                    "high", "severe" ->
                        "The road condition is very dangerous. Large potholes or deep cracks are detected. Immediate repair is required to avoid accidents and vehicle damage."

                    "medium", "moderate" ->
                        "The road has visible surface damage and uneven patches. Maintenance work should be scheduled soon."

                    else ->
                        "Minor road damage detected. The road is mostly safe but should be monitored regularly."
                }

            val trafficImpact =
                when (result.severity.lowercase()) {

                    "high", "severe" ->
                        "Traffic movement may become risky, especially for bikes and small vehicles."

                    "medium", "moderate" ->
                        "Vehicles may experience moderate disturbance while driving."

                    else ->
                        "Minimal impact on traffic flow."
                }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Analysis Result",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text("Damage Type: ${result.damageType}")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Severity: ${result.severity}")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Confidence: ${result.confidence}")

                    Spacer(modifier = Modifier.height(6.dp))

                    Text("Urgency: ${result.urgency}")

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Road Condition Report",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(roadCondition)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Traffic Impact",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(trafficImpact)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Recommended Action",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(result.urgency)

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {

                            val report = Report(
                                damageType = result.damageType,
                                severity = result.severity,
                                confidence = result.confidence,
                                urgency = result.urgency
                            )

                            ReportStorage.reports.add(report)

                            submitted = true
                        },

                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Text("Submit Report")
                    }

                    if (submitted) {

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Report Submitted Successfully"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "The report has been added to dashboard statistics and report history."
                        )
                    }
                }
            }
        }

        uiState.error?.let {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}