package com.pm.an.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.pm.an.R;
import com.pm.player.AudioActivity;
import com.pm.player.VideoActivity;

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
