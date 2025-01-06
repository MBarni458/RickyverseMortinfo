package hu.mbarni.android.rickiversemortinfo.feature.episode.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ActionButton(onClick: () -> Unit, contentColor: Color, title: String, icon: ImageVector) {
    Button(
        onClick = onClick,
        border = BorderStroke(width = 2.dp, color = contentColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = contentColor
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, fontSize = 25.sp)
            Icon(imageVector = icon, contentDescription = title)
        }
    }
}

@Preview
@Composable
fun ActionButtonPreview() {
    ActionButton(
        onClick = { /*TODO*/ },
        contentColor = Color.Blue,
        title = "Preview",
        icon = Icons.Default.Done
    )
}