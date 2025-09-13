package com.clock_android_app;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.clock_android_app.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding _binding;
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

    private void showOptions() {
        _binding.checkboxBatteryLevel.animate().translationY(0).setDuration(400);
        _binding.imageViewClose.animate().translationY(0).setDuration(400);
    }

    private void hideOptions() {
        _binding.checkboxBatteryLevel.animate().translationY(300).setDuration(400);
        _binding.imageViewClose.animate().translationY(300).setDuration(400);
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