package hu.mbarni.android.rickiversemortinfo.feature.componenets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CenteredTitle(title: String, color: Color) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(0.75f)
    ) {
        Text(
            text = title,
            fontSize = 40.sp,
            color = color,
            fontWeight = FontWeight.Bold,
            lineHeight = 42.sp
        )
    }
}