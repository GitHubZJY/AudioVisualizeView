package com.zjy.audiovisualizeview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.zjy.audiovisualize.view.AudioVisualizeView;
import com.zjy.audiovisualizeview.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity {

    private AudioVisualizeView vAudioVisualize;
    private static final int RECORD_AUDIO = 10001;
    private static final int READ_EXTERNAL_STORAGE = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vAudioVisualize = findViewById(R.id.audio_visualize_view);
        vAudioVisualize.doPlay(R.raw.sound);

        PermissionUtils.requestPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO);
        PermissionUtils.requestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RECORD_AUDIO:
            case READ_EXTERNAL_STORAGE:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vAudioVisualize != null) {
           vAudioVisualize.release();
        }
    }
}