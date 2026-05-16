package com.namma.raste.health.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.namma.raste.health.model.ReportStorage
import com.namma.raste.health.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    Scaffold(

        topBar = {

            Column {

                TopAppBar(

                    title = {

                        Column {

                            Text(
                                text = "Government of India",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = 2.sp
                            )

                            Text(
                                text = "NAMMA RASTE HEALTH",
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },

                    actions = {

                        IconButton(
                            onClick = {
                                navController.navigate(Screen.Profile.route)
                            }
                        ) {

                            Surface(
                                modifier = Modifier.size(36.dp),
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            ) {

                                Box(
                                    contentAlignment = Alignment.Center
                                ) {

                                    Text(
                                        text = "JD",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        },

        containerColor = MaterialTheme.colorScheme.background

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(20.dp)
        ) {

            Text(
                text = "Command Dashboard",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(20.dp))

            DashboardStats()

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Critical Scans",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (ReportStorage.reports.isEmpty()) {

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "No Reports Yet",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Scan and submit road damage reports to see live updates here.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

            } else {

                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    ReportStorage.reports.takeLast(3).reversed().forEach { report ->

                        val severityColor =
                            when {
                                report.severity.contains("high", true) ->
                                    Color(0xFFDC2626)

                                report.severity.contains("moderate", true) ->
                                    Color(0xFFD97706)

                                else ->
                                    Color(0xFF15803D)
                            }

                        RecentScanItem(
                            title = report.damageType,
                            location = "Bengaluru Road Zone",
                            severity = report.severity,
                            severityColor = severityColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Infrastructure Actions",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.height(320.dp)
            ) {

                item {

                    ActionCard(
                        label = "New Road Scan",
                        icon = Icons.Default.CameraAlt,
                        description = "Detect potholes with AI"
                    ) {
                        navController.navigate(Screen.Scan.route)
                    }
                }

                item {

                    ActionCard(
                        label = "Damage Mapping",
                        icon = Icons.Default.Map,
                        description = "View real-time feed"
                    ) {
                        navController.navigate(Screen.Map.route)
                    }
                }

                item {

                    ActionCard(
                        label = "Recent Reports",
                        icon = Icons.Default.Assignment,
                        description = "Track infrastructure"
                    ) {
                        navController.navigate(Screen.Reports.route)
                    }
                }

                item {

                    ActionCard(
                        label = "Civic Rewards",
                        icon = Icons.Default.Star,
                        description = "Earn hero points"
                    ) {
                        navController.navigate(Screen.Rewards.route)
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardStats() {

    val totalReports = ReportStorage.reports.size

    val severeReports =
        ReportStorage.reports.count {

            it.severity.contains("high", true) ||
                    it.severity.contains("severe", true) ||
                    it.urgency.contains("immediate", true)
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        StatCard(
            label = "Total Reports",
            value = totalReports.toString(),
            subtitle = "Live",
            modifier = Modifier.weight(1f)
        )

        StatCard(
            label = "Severe",
            value = severeReports.toString(),
            subtitle = "Critical",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.displayLarge,
                color =
                    if (label == "Severe")
                        Color(0xFFDC2626)
                    else
                        MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color =
                    if (label == "Severe")
                        Color(0xFFFEF2F2)
                    else
                        Color(0xFFF0FDF4)
            ) {

                Text(
                    text = subtitle,
                    modifier = Modifier.padding(
                        horizontal = 8.dp,
                        vertical = 4.dp
                    ),
                    style = MaterialTheme.typography.labelSmall,
                    color =
                        if (label == "Severe")
                            Color(0xFFDC2626)
                        else
                            Color(0xFF166534)
                )
            }
        }
    }
}

@Composable
fun ActionCard(
    label: String,
    icon: ImageVector,
    description: String,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        modifier = Modifier.height(140.dp),
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column {

                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = description,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RecentScanItem(
    title: String,
    location: String,
    severity: String,
    severityColor: Color
) {

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.BrokenImage,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        shape = RoundedCornerShape(4.dp),
                        color = severityColor.copy(alpha = 0.1f)
                    ) {

                        Text(
                            text = severity,
                            modifier = Modifier.padding(
                                horizontal = 6.dp,
                                vertical = 2.dp
                            ),
                            style = MaterialTheme.typography.labelSmall,
                            color = severityColor
                        )
                    }
                }

                Text(
                    text = location,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "#RR-${(1000..9999).random()}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )

                    Text(
                        text = "Just Now",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}