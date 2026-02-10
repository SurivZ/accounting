package surivz.productivity.accounting.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Output
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

enum class MovementTypeUI { INCOME, EXPENSE, LOAN, DEBT, FUTURE_PAY }

@Composable
fun MovementItem(
    title: String,
    amount: Double,
    date: String,
    type: MovementTypeUI,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val (icon, color, bgColor) = getMovementVisuals(type)

    val prefix = if (type == MovementTypeUI.INCOME || type == MovementTypeUI.DEBT) "+" else "-"
    val amountColor = if (type == MovementTypeUI.INCOME || type == MovementTypeUI.DEBT)
        Color(0xFF2E7D32)
    else
        Color(0xFFC62828)

    ListItem(
        modifier = modifier.clickable { onClick() },
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Text(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingContent = {
            Text(
                text = "$prefix $amount",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = amountColor
            )
        }
    )
}

@Composable
private fun getMovementVisuals(type: MovementTypeUI): Triple<ImageVector, Color, Color> {
    return when (type) {
        MovementTypeUI.INCOME -> Triple(
            Icons.Default.ArrowUpward,
            Color(0xFF1B5E20),
            Color(0xFFE8F5E9)
        )

        MovementTypeUI.EXPENSE -> Triple(
            Icons.Default.ArrowDownward,
            Color(0xFFB71C1C),
            Color(0xFFFFEBEE)
        )

        MovementTypeUI.LOAN -> Triple(
            Icons.Default.Output,
            Color(0xFF0D47A1),
            Color(0xFFE3F2FD)
        )

        MovementTypeUI.DEBT -> Triple(
            Icons.AutoMirrored.Filled.Input,
            Color(0xFFE65100),
            Color(0xFFFFF3E0)
        )

        MovementTypeUI.FUTURE_PAY -> Triple(
            Icons.Default.Schedule,
            Color(0xFF7B1FA2),
            Color(0xFFF3E5F5)
        )
    }
}