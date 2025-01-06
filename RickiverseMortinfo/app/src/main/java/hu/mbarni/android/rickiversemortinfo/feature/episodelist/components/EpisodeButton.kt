package hu.mbarni.android.rickiversemortinfo.feature.episodelist.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EpisodeButton(contentColor: Color, containerColor: Color, onClick: () -> Unit, title: String) {
    Column(
        modifier = Modifier.padding(vertical = 5.dp)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = contentColor,
                    shape = CircleShape
                ),
            colors = ButtonDefaults.buttonColors(
                contentColor = contentColor,
                containerColor = containerColor
            )
        ) {
            Text(
                text = title,
                fontSize = 30.sp,
                modifier = Modifier.padding(vertical = 15.dp),
                lineHeight = 35.sp
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
    }
}