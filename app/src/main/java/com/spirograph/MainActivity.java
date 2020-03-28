package com.spirograph;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button stopButton;
    Button resumeButton;
    Button restartButton;

    SpiroGraphView spiroGraphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopButton = findViewById(R.id.stopButton);
        resumeButton = findViewById(R.id.resumeButton);
        restartButton = findViewById(R.id.restartButton);

        linearLayout = findViewById(R.id.linearLayout);
        spiroGraphView = new SpiroGraphView(this);
        linearLayout.addView(spiroGraphView);

        Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"1", "2", "three"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );
        spinner.setAdapter(adapter);

    }

    public void stopButtonOnClick(View view) {
        spiroGraphView.stopButtonClicked();
    }

    public void resumeButtonOnClick(View view) {
        spiroGraphView.resumeButtonClicked();
    }

    public void restartButtonOnClick(View view) {
        spiroGraphView.restartButtonClicked();
    }

}
