package hu.mbarni.android.rickiversemortinfo.feature.character.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoText(text:String, color: Color) {
     Column {
        Text(text= text, fontSize = 30.sp, color = color)
        Spacer(modifier = Modifier.padding(5.dp,5.dp))
    }

}

@Preview
@Composable
fun InfoTextPreview(){
    InfoText(text = "Preview", color = Color.Blue)
}