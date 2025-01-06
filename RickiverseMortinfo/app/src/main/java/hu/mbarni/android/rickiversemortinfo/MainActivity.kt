package hu.mbarni.android.rickiversemortinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import hu.mbarni.android.rickiversemortinfo.navigation.NavGraph
import hu.mbarni.android.rickiversemortinfo.ui.theme.RickiverseMortinfoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickiverseMortinfoTheme {
                NavGraph()
            }
        }
    }
}