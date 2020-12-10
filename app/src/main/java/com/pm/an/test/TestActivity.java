package com.pm.an.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.util.Size;
import android.view.Display;
import android.widget.Button;

import com.common.data.LiveDataManager;
import com.pm.an.R;
import com.pm.player.AudioActivity;
import com.pm.player.VideoActivity;

import java.time.Instant;

/**
 * @author pmcho
 */
public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final Button button = findViewById(R.id.btn_goto_video);
        button.setOnClickListener(v -> startActivity(new Intent(this, VideoActivity.class)));
        findViewById(R.id.btn_goto_audio).setOnClickListener(v -> {
            startActivity(new Intent(this, AudioActivity.class));
        });
    }
}
