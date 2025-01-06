package hu.mbarni.android.rickiversemortinfo.feature.componenets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage


@Composable
fun CharacterCard(
    onClick: () -> Unit,
    url: String,
    title: String,
    contentColor: Color,
    containerColor: Color
) {
    Button(
        onClick = onClick,
        border = BorderStroke(width = 2.dp, color = contentColor),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = url, contentDescription = title, modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .size(80.dp)
            )
            Text(
                text = title,
                fontSize = 30.sp,
                modifier = Modifier.padding(start = 10.dp),
                lineHeight = 35.sp
            )
        }
    }
}

@Preview
@Composable
fun CharacterCardPreview() {
    CharacterCard(
        onClick = { /*TODO*/ },
        url = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
        title = "PreviewImage",
        contentColor = Color.Blue,
        containerColor = Color.White
    )
}