package hu.mbarni.android.rickiversemortinfo.feature.componenets

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    onClick: () -> Unit,
    icon: ImageVector? = null,
    painter: Painter? = null,
    iconColor: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = iconColor
        )
    ) {
        if (painter != null) {
            Icon(painter = painter, contentDescription = "", modifier = Modifier.size(35.dp))
        } else {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "", modifier = Modifier.size(35.dp))
            }
        }
    }
}