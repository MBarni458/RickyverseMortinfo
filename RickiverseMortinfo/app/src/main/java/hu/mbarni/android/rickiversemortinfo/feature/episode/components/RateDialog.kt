package hu.mbarni.android.rickiversemortinfo.feature.episode.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gowtham.ratingbar.RatingBar
import hu.mbarni.android.rickiversemortinfo.R


@Composable
fun RateDialog(onDismiss: () -> Unit, onSubmit: (score: Int) -> Unit) {
    var rating by remember { mutableFloatStateOf(0.0f) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            border = BorderStroke(width = 5.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.rate_dialog_title, 1, 5),
                    modifier = Modifier.padding(8.dp),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                RatingBar(
                    value = rating,
                    activeColor = MaterialTheme.colorScheme.primary,
                    inactiveColor = MaterialTheme.colorScheme.secondaryContainer,
                    size = 30.dp,
                    onValueChange = {
                        rating = it
                    },
                    onRatingChanged = {}
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        onClick = {
                            onSubmit(rating.toInt())
                            onDismiss()
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(
                            text = stringResource(id = R.string.submit),
                            color = MaterialTheme.colorScheme.background
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))

            }
        }
    }
}