package com.zjy.audiovisualizeview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zjy.audiovisualize.view.AudioVisualizeView;

public class MainActivity extends AppCompatActivity {

    private AudioVisualizeView vAudioVisualize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vAudioVisualize = findViewById(R.id.audio_visualize_view);
        vAudioVisualize.doPlay(R.raw.sound);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vAudioVisualize != null) {
           vAudioVisualize.release();
        }
    }
}