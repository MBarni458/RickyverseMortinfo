package hu.mbarni.android.rickiversemortinfo.feature.favorites.componenets

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AnimatedButton(color: Color, fontSize: Int, onClick: () -> Unit, title: String) {
    Button(
        colors = ButtonDefaults.buttonColors(
            contentColor = color,
            containerColor = MaterialTheme.colorScheme.background
        ),
        onClick = onClick,
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier.animateContentSize(animationSpec = tween(1000))
    ) {
        Text(
            text = title,
            fontSize = fontSize.sp
        )
    }
}