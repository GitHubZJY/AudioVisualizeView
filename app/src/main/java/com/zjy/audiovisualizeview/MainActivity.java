package com.zjy.audiovisualizeview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zjy.audiovisualizeview.utils.PermissionUtils;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView vReflect, vCircle, vSingle, vWave, vNet, vGrain;
    private static final int RECORD_AUDIO = 10001;
    private static final int READ_EXTERNAL_STORAGE = 10002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vReflect = findViewById(R.id.reflect_entrance);
        vCircle = findViewById(R.id.circle_entrance);
        vSingle = findViewById(R.id.single_entrance);
        vWave = findViewById(R.id.wave_entrance);
        vNet = findViewById(R.id.net_entrance);
        vGrain = findViewById(R.id.grain_entrance);
        vSingle.setOnClickListener(this);
        vReflect.setOnClickListener(this);
        vCircle.setOnClickListener(this);
        vWave.setOnClickListener(this);
        vNet.setOnClickListener(this);
        vGrain.setOnClickListener(this);

        PermissionUtils.requestPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO, RECORD_AUDIO);
        PermissionUtils.requestPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.single_entrance:
                intent = new Intent(MainActivity.this, SingleVisualizeActivity.class);
                break;
            case R.id.reflect_entrance:
                intent = new Intent(MainActivity.this, ReflectVisualizeActivity.class);
                break;
            case R.id.circle_entrance:
                intent = new Intent(MainActivity.this, CircleVisualizeActivity.class);
                break;
            case R.id.wave_entrance:
                intent = new Intent(MainActivity.this, WaveVisualizeActivity.class);
                break;
            case R.id.net_entrance:
                intent = new Intent(MainActivity.this, NetVisualizeActivity.class);
                break;
            case R.id.grain_entrance:
                intent = new Intent(MainActivity.this, GrainVisualizeActivity.class);
                break;
        }
        startActivity(intent);
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
    }
}