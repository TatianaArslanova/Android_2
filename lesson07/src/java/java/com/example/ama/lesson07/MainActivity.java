package com.example.ama.lesson07;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.sv_circle).setOnClickListener(view -> view.startAnimation(
                AnimationUtils.loadAnimation(MainActivity.this, R.anim.simple_animation)));
        findViewById(R.id.sv_square).setOnClickListener(view -> view.startAnimation(
                AnimationUtils.loadAnimation(MainActivity.this, R.anim.simple_animation)));
        findViewById(R.id.sv_triangle).setOnClickListener(view -> view.startAnimation(
                AnimationUtils.loadAnimation(MainActivity.this, R.anim.simple_animation)));
    }
}
