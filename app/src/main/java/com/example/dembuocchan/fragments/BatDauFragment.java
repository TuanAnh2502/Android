package com.example.dembuocchan.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.dembuocchan.DanhgiaActivity;
import com.example.dembuocchan.R;

import java.util.Calendar;
import java.util.Locale;

public class BatDauFragment extends Fragment implements SensorEventListener{
    private TextView stepCountTextView;
    private TextView distanceTextView;
    private TextView timeTextView;

    private Button pauseButton;
    private SensorManager sensorManager;
    private Sensor stepCounterSensor;
    private int stepCount = 0;
    private ProgressBar progressBar;
    private boolean isPaused = false;
    private long timePause = 0;
    private float stepLengthInMeters = 0.762f;
    private int lastResetDay = -1;
    private long startTime ;
    private int steoCountTarget = 5000;
    private TextView stepCountTargetTextView ;
    private Handler timerhandler = new Handler();
    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long milis = System.currentTimeMillis()- startTime;
            int seconds = (int) milis/1000;
            int min  = seconds/60;
            seconds = seconds%60;
            timeTextView.setText(String.format(Locale.getDefault(),"Time: %02d:%02d",min,seconds));
            timerhandler.postDelayed(this,1000);
        }
    };
    @Override
    public void onStop() {
        super.onStop();
        if(stepCounterSensor!= null){
            sensorManager.unregisterListener(this);
            timerhandler.removeCallbacks(timerRunnable);
        }
    }
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this,stepCounterSensor);
        timerhandler.removeCallbacks(timerRunnable);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(stepCounterSensor!= null){
            sensorManager.registerListener((SensorEventListener) this,stepCounterSensor,SensorManager.SENSOR_DELAY_NORMAL);
            timerhandler.postDelayed(timerRunnable,0);
        }
    }
    View batdauview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        batdauview=inflater.inflate(R.layout.fragment_bat_dau, container, false);
        stepCountTextView = batdauview.findViewById(R.id.stepCountTextView);
        distanceTextView = batdauview.findViewById(R.id.distaceTextView);
        timeTextView = batdauview.findViewById(R.id.timeTextView);
        pauseButton = batdauview.findViewById(R.id.pauseButton);
        stepCountTargetTextView = batdauview.findViewById(R.id.stepTargetTextView);
        progressBar = batdauview.findViewById(R.id.progressBar);

        startTime = System.currentTimeMillis();
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        progressBar.setMax(100);
        stepCountTargetTextView.setText("Step Goal:"+steoCountTarget);

        if(stepCounterSensor ==null){
            stepCountTextView.setText("Step counter not available");
        }
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPausedButtonClicked(v);
            }
        });
        return batdauview;
    }
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            // Lấy ngày hiện tại
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_YEAR);

            // Kiểm tra nếu đã đổi ngày
            if (currentDay != lastResetDay) {
                // Reset số bước và cập nhật ngày cuối cùng reset
                stepCount=0;
                lastResetDay = currentDay;
            }
            stepCount = (int) sensorEvent.values[0];
            stepCountTextView.setText("Step Count:"+ stepCount);
            if(stepCount>= steoCountTarget){
                stepCountTargetTextView.setText("Step Goal Achieved");
            }
            float km=stepCount*stepLengthInMeters*100;
            float distanceInKm =  stepCount*stepLengthInMeters/1000;
            distanceTextView.setText(String.format(Locale.getDefault(),"Distance:%.2f km",distanceInKm));
            int phantram=(int) (km/steoCountTarget);
            progressBar.setProgress(phantram);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    public void onPausedButtonClicked(View view){
        if(isPaused){
            isPaused = false;
            pauseButton.setText("Pause");
            startTime = System.currentTimeMillis()-timePause;
            timerhandler.postDelayed(timerRunnable,0);
        }else {
            isPaused = true;
            pauseButton.setText("Resume");
            timerhandler.removeCallbacks(timerRunnable);
            timePause = System.currentTimeMillis()-startTime;
        }
    }
}