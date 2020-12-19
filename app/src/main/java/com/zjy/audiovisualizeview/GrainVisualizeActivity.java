package com.zjy.audiovisualizeview;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.zjy.audiovisualize.view.AudioVisualizeView;

public class GrainVisualizeActivity extends AppCompatActivity {

    private AudioVisualizeView vAudioVisualize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grain_visualize);

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