package com.example.timercompose

import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class TimerViewModel:ViewModel() {
    public var timerSecond by mutableStateOf<Long>(0)
    private set

    public fun addTimerSecondWithTickDelta(){
        timerSecond+=tickDelta
    }

    public var stopped by mutableStateOf<Boolean>(true)
        private set

    public var tickDelta=0;

    private suspend fun onTick(){
        while(!stopped){
            timerSecond+=tickDelta
            delay(1000)
        }
    }

    fun start(tickDelta:Int=1){
        stopped=false;
        this.tickDelta=tickDelta;
        //viewModelScope.launch { onTick() }
    }

    fun pause(){
        tickDelta=0;
    }

    fun stop(){
        stopped=true;
        tickDelta=0;
        timerSecond=0;
    }
}