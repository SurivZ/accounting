package surivz.productivity.accounting.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import surivz.productivity.accounting.ui.components.BalanceCard
import surivz.productivity.accounting.ui.components.MovementItem
import surivz.productivity.accounting.ui.components.MovementTypeUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToAdd: (MovementTypeUI) -> Unit,
    onNavigateToSettings: () -> Unit,
    onMovementClick: (Long) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf<MovementTypeUI?>(null) }

    val mockMovements = listOf(
        MockMovement(1, "Sueldo", 2000.0, "01 Feb", MovementTypeUI.INCOME),
        MockMovement(2, "Supermercado", 150.0, "02 Feb", MovementTypeUI.EXPENSE),
        MockMovement(3, "Préstamo a Juan", 50.0, "03 Feb", MovementTypeUI.LOAN),
        MockMovement(4, "Pago de Deuda", 100.0, "04 Feb", MovementTypeUI.DEBT),
        MockMovement(5, "Internet (Futuro)", 40.0, "15 Feb", MovementTypeUI.FUTURE_PAY)
    )

    val rotation by animateFloatAsState(
        targetValue = if (expanded) 45f else 0f,
        label = "FAB Rotation"
    )

    val menuOptions = listOf(
        MenuOptionData(
            "Ingreso",
            Icons.Default.ArrowUpward,
            Color(0xFF4CAF50),
            MovementTypeUI.INCOME
        ),
        MenuOptionData(
            "Gasto",
            Icons.Default.ArrowDownward,
            Color(0xFFF44336),
            MovementTypeUI.EXPENSE
        ),
        MenuOptionData("Pago Futuro", Icons.Default.Schedule, Color(0xFF9C27B0), null),
        MenuOptionData("Me deben", Icons.Default.Upload, Color(0xFF2196F3), MovementTypeUI.LOAN),
        MenuOptionData("Yo debo", Icons.Default.Download, Color(0xFFFF9800), MovementTypeUI.DEBT)
    )

    val filteredMovements = remember(selectedFilter) {
        if (selectedFilter == null) mockMovements
        else mockMovements.filter { it.type == selectedFilter }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Mi Contabilidad", fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Configuración")
                    }
                }
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom),
                    exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom)
                ) {
                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        menuOptions.forEach { option ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 4.dp)
                            ) {
                                Surface(
                                    shape = MaterialTheme.shapes.medium,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    tonalElevation = 2.dp,
                                    modifier = Modifier.padding(end = 12.dp)
                                ) {
                                    Text(
                                        text = option.label,
                                        modifier = Modifier.padding(
                                            horizontal = 12.dp,
                                            vertical = 6.dp
                                        ),
                                        style = MaterialTheme.typography.labelLarge,
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                SmallFloatingActionButton(
                                    onClick = {
                                        expanded = false
                                        option.type?.let { onNavigateToAdd(it) }
                                    },
                                    containerColor = option.color,
                                    contentColor = Color.White,
                                    shape = CircleShape
                                ) {
                                    Icon(
                                        option.icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                FloatingActionButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.graphicsLayer { rotationZ = rotation },
                    containerColor = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Mostrar menú",
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                BalanceCard(
                    totalBalance = 1500.50,
                    income = 2000.0,
                    expense = 499.50
                )
            }

            item {
                Column {
                    Text(
                        text = "Filtrar por",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedFilter == null,
                                onClick = { selectedFilter = null },
                                label = { Text("Todos") })
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == MovementTypeUI.INCOME,
                                onClick = { selectedFilter = MovementTypeUI.INCOME },
                                label = { Text("Ingresos") })
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == MovementTypeUI.EXPENSE,
                                onClick = { selectedFilter = MovementTypeUI.EXPENSE },
                                label = { Text("Gastos") })
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == MovementTypeUI.FUTURE_PAY,
                                onClick = { selectedFilter = MovementTypeUI.FUTURE_PAY },
                                label = { Text("Futuros") }
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == MovementTypeUI.LOAN,
                                onClick = { selectedFilter = MovementTypeUI.LOAN },
                                label = { Text("Por Cobrar") })
                        }
                        item {
                            FilterChip(
                                selected = selectedFilter == MovementTypeUI.DEBT,
                                onClick = { selectedFilter = MovementTypeUI.DEBT },
                                label = { Text("Por Pagar") })
                        }
                    }
                }
            }

            item {
                Text(
                    text = if (selectedFilter == null) "Movimientos recientes" else "Resultados del filtro",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontWeight = FontWeight.SemiBold
                )
            }

            items(filteredMovements) { movement ->
                MovementItem(
                    title = movement.title,
                    amount = movement.amount,
                    date = movement.date,
                    type = movement.type,
                    onClick = { onMovementClick(movement.id) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 0.5.dp,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

data class MenuOptionData(
    val label: String,
    val icon: ImageVector,
    val color: Color,
    val type: MovementTypeUI?
)

data class MockMovement(
    val id: Long,
    val title: String,
    val amount: Double,
    val date: String,
    val type: MovementTypeUI
)