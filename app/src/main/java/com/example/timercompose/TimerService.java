package com.example.timercompose;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class TimerService extends Service {
    private static final String TAG=TimerService.class.getSimpleName();

    public static Consumer<TimerService> OnCreate;

    public void setOnCreate(Consumer<TimerService> onCreate){
        OnCreate=onCreate;
    }

    //private final AtomicLong mTick=new AtomicLong();

    private TimerViewModel mTimerViewModel;

    public void setTimerViewModel(TimerViewModel timerViewModel) {
        if(mTimerViewModel!=null){
            throw new RuntimeException("timer view model has been set");
        }
        this.mTimerViewModel = timerViewModel;
        mTickerThread.start();
    }

    private final Thread mTickerThread=new Thread("ticker"){
        @Override
        public void run() {
            super.run();
            while(true){
                if(mTimerViewModel.getStopped()){
                    continue;
                }
                mTimerViewModel.addTimerSecondWithTickDelta();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        if(OnCreate!=null) {
            OnCreate.accept(this);
        }
    }
}
