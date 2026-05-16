package com.namma.raste.health.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.namma.raste.health.model.ReportStorage

@Composable
fun MyReportsScreen(
    navController: NavHostController
) {

    val reports = ReportStorage.reports

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "My Reports",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (reports.isEmpty()) {

            Text("No Reports Submitted")

        } else {

            LazyColumn {

                items(reports) { report ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {

                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {

                            Text("Damage: ${report.damageType}")

                            Text("Severity: ${report.severity}")

                            Text("Confidence: ${report.confidence}")

                            Text("Urgency: ${report.urgency}")
                        }
                    }
                }
            }
        }
    }
}