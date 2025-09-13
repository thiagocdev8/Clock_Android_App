package com.clock_android_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clock_android_app.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding _binding;
    private final String _tag = "MainActivity";
    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            _binding.batteryLevel.setText(getString(R.string.label_battery_percentage, level));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        _binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(_binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(_binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setFlags();
        setListeners();
        hideOptions();
        startClock();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(batteryReceiver);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.checkbox_batteryLevel) {
            toggleBatteryLevel();
        }
        else if (v.getId() == R.id.image_view_settings) {
            showOptions();
        }
        else if (v.getId() == R.id.image_view_close) {
            hideOptions();
        }
    }

    private void startClock() {
        LocalDateTime now = LocalDateTime.now();



        DateTimeFormatter formatterTimeNow = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterTimeSeconds = DateTimeFormatter.ofPattern("ss");

        String timeNow = now.format(formatterTimeNow);
        String secondsNow = now.format(formatterTimeSeconds);

        _binding.timeNow.setText(timeNow);
        _binding.timeSeconds.setText(secondsNow);

    }
    private void showOptions() {

        int duration = 400;
        _binding.checkboxBatteryLevel.animate().translationY(0).setDuration(duration);
        _binding.imageViewClose.animate().translationY(0).setDuration(duration);
    }

    private void hideOptions() {
        int duration = 400;
        _binding.checkboxBatteryLevel.animate().translationY(300).setDuration(duration);
        _binding.imageViewClose.animate().translationY(300).setDuration(duration);
    }
    private void setListeners(){
        _binding.checkboxBatteryLevel.setOnClickListener(this);
        _binding.imageViewSettings.setOnClickListener(this);
        _binding.imageViewClose.setOnClickListener(this);
    }

    private void toggleBatteryLevel() {

        boolean isVisible = _binding.batteryLevel.getVisibility() == View.VISIBLE;

        _binding.batteryLevel.setVisibility(isVisible ? View.GONE : View.VISIBLE);
    }

    private void setFlags(){

    }


}