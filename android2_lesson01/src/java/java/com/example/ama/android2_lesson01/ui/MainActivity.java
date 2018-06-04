package com.example.ama.android2_lesson01.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.ama.android2_lesson01.R;

/**
 * Class of main activity
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Launcher.runListOfNotesFragment(this, false);
        }
    }
}