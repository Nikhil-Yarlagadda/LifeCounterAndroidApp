package com.example.lifecounter

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lifecounter.ui.theme.LifeCounterTheme
import com.example.lifecounter.ui.StartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LifeCounterTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                {
                    innerPadding ->
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    window.decorView.apply {
                        systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    }
                    LifeCounter(modifier = Modifier.padding(innerPadding))

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun lifeCounterAppPreview(){
    LifeCounterTheme {
        Surface {
            LifeCounter()
        }
    }
}