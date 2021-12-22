package com.example.timercompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.timercompose.ui.theme.Blue
import com.example.timercompose.ui.theme.TimerComposeTheme
import java.lang.StringBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class MainActivity : ComponentActivity() {
    private val timerViewModel by lazy{
        defaultViewModelProviderFactory.create(TimerViewModel::class.java)
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TimerService.OnCreate= Consumer {
            it.setTimerViewModel(timerViewModel)
        }
        startService(Intent(this,TimerService::class.java))
        setContent {
            TimerComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    TimerPanel(timerViewModel)
                }
            }
        }
    }
}

@Composable
@ExperimentalAnimationApi
fun TimerPanel(timerViewModel:TimerViewModel){
    Column {
        val fillMaxSizeModifier= Modifier
            .fillMaxSize()
        Spacer(modifier = fillMaxSizeModifier.weight(0.15F))
        //Greeting("Android")
        Text(timerViewModel.timerSecond.toString(), modifier =fillMaxSizeModifier.weight(0.45F),
            textAlign = TextAlign.Center,fontSize = 60.sp)
        Row(modifier = fillMaxSizeModifier.weight(0.30F)){
            val evenWeightModifier=fillMaxSizeModifier.weight(1F)
            var showPauseAndStop by remember{ mutableStateOf(false)}
            AnimatedVisibility(visible = !showPauseAndStop) {
                IconButton(onClick = {
                    timerViewModel.start(1)
                    showPauseAndStop=true
                },iconResID = android.R.drawable.ic_media_play,modifier = evenWeightModifier)
            }
            AnimatedVisibility(visible = showPauseAndStop,modifier = evenWeightModifier) {
                Row {
                    //pause
                    IconButton(onClick = {
                        timerViewModel.pause()
                        showPauseAndStop=false
                    },iconResID = R.drawable.ic_pause,modifier = evenWeightModifier)
                    //stop
                    IconButton(onClick = {
                        timerViewModel.stop()
                        showPauseAndStop=false
                    },iconResID = R.drawable.ic_stop,modifier = evenWeightModifier)
                }
            }
        }
        Spacer(fillMaxSizeModifier.weight(0.10F))
    }
}

@Composable
fun IconButton(onClick:() -> Unit,iconResID:Int,modifier: Modifier){
    Button(onClick = onClick,
        modifier = modifier,colors = ButtonDefaults.buttonColors(Blue, Color.White)){
        val painter=painterResource(id = iconResID)
        Icon(painter= painter,contentDescription = null)
    }
}