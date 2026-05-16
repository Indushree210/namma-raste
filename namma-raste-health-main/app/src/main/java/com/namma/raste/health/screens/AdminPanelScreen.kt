package com.namma.raste.health.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(navController: NavHostController) {

    Scaffold(

        topBar = {

            TopAppBar(
                title = {
                    Text("Government Admin Panel")
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            Text(
                text = "Pending Reports",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            val reports = listOf(1, 2, 3)

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                items(reports) { index ->

                    AdminReportItem(index)
                }
            }
        }
    }
}

@Composable
fun AdminReportItem(index: Int) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Severity: High",
                    color = Color(0xFFDC2626),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "#RR-001$index",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pothole reported near MG Road",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Reporter ID: dev_user_75",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = { },
                    shape = RoundedCornerShape(12.dp)
                ) {

                    Text("Assign Team")
                }

                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.primary
                    )
                ) {

                    Text(
                        text = "Resolve",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}