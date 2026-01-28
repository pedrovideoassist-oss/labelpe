package com.validatech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.validatech.ui.navigation.ValidaTechNavHost
import com.validatech.ui.theme.ValidaTechTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ValidaTechTheme {
                ValidaTechNavHost()
            }
        }
    }
}
