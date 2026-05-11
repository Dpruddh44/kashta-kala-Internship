package com.kalakashta.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.kalakashta.app.core.designsystem.Artisan
import com.kalakashta.app.core.designsystem.ArtisanTheme
import com.kalakashta.app.core.nav.ArtisanNavGraph

class ArtisanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtisanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Artisan.palette.canvas
                ) {
                    ArtisanNavGraph()
                }
            }
        }
    }
}
