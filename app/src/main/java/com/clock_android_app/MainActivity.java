package com.clock_android_app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity {



    EditText timeNow;
    EditText timeSeconds;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable clockRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        timeNow = findViewById(R.id.timeNow);
        timeSeconds = findViewById(R.id.timeSeconds);


        clockRunnable = new Runnable() {
            @Override
            public void run() {
                LocalDateTime time = LocalDateTime.now();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                String formattedTime = time.format(formatter);

                DateTimeFormatter formatterSegundos = DateTimeFormatter.ofPattern("ss");
                String formattedTime2 = time.format(formatterSegundos);

                timeNow.setText(formattedTime);
                timeSeconds.setText(formattedTime2);


                handler.postDelayed(this, 1000);
            }
        };

        handler.post(clockRunnable);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop updates when activity is destroyed
        handler.removeCallbacks(clockRunnable);
    }
}