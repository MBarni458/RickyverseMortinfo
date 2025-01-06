package hu.mbarni.android.rickiversemortinfo.feature.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import hu.mbarni.android.rickiversemortinfo.R
import hu.mbarni.android.rickiversemortinfo.navigation.routes.MainPagesNavigator
import hu.mbarni.android.rickiversemortinfo.navigation.routes.Route

@Composable
fun BottomBar(modifier: Modifier, navigator: MainPagesNavigator, selected: Route) {
    Row(
        modifier = modifier
            .background(color = Color.Black)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(
            onClick = { navigator.navigateTo(Route.EPISODES) },
            icon = Icons.Default.Home,
            iconColor = if (selected == Route.EPISODES) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
        )
        IconButton(
            onClick = { navigator.navigateTo(Route.CHARACTERS) },
            painter = painterResource(id = R.drawable.rickicon),
            iconColor = if (selected == Route.CHARACTERS) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
        )
        IconButton(
            onClick = { navigator.navigateTo(Route.FAVORITES) },
            icon = Icons.Default.Favorite,
            iconColor = if (selected == Route.FAVORITES) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
        )
    }
}
