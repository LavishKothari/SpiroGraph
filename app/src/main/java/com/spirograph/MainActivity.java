package com.spirograph;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.spirograph.shapes.Line;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button stopButton;
    Button resumeButton;
    Button restartButton;
    Button submitButton;
    LinearLayout dynamicEditTexts;
    SpiroGraphView spiroGraphView;

    EditTextCollection lengthsEditText = new EditTextCollection();
    EditTextCollection angleIncrementsEditTexts = new EditTextCollection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("on create called");

        stopButton = findViewById(R.id.stopButton);
        resumeButton = findViewById(R.id.resumeButton);
        restartButton = findViewById(R.id.restartButton);
        submitButton = findViewById(R.id.submitButton);

        dynamicEditTexts = findViewById(R.id.dynamicEditTexts);

        linearLayout = findViewById(R.id.linearLayout);

        spiroGraphView = new SpiroGraphView(this);
        linearLayout.addView(spiroGraphView);

        final Spinner spinner = findViewById(R.id.spinner);
        String[] items = new String[]{"1", "2", "3", "4"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new SpinnerOnItemSelectedListener(
                        spiroGraphView,
                        this,
                        dynamicEditTexts,
                        lengthsEditText,
                        angleIncrementsEditTexts
                )
        );
    }

    public void submitButtonOnClick(View view) {
        try {
            List<Integer> lengths = lengthsEditText.getLengths();
            List<Integer> intAngleIncrements = angleIncrementsEditTexts.getLengths();
            List<Float> angleIncrements = new ArrayList<>();
            for (int i = 0; i < intAngleIncrements.size(); i++) {
                angleIncrements.add(intAngleIncrements.get(i) / 800.0f);
            }
            spiroGraphView.reset(lengths, angleIncrements);
        } catch (NumberFormatException ex) {
            Snackbar
                    .make(
                            linearLayout,
                            "Enter valid numbers",
                            Snackbar.LENGTH_LONG
                    ).show();
        }
    }

    public void stopButtonOnClick(View view) {
        spiroGraphView.stopButtonClicked();
    }

    public void resumeButtonOnClick(View view) {
        spiroGraphView.resumeButtonClicked();
    }

    public void restartButtonOnClick(View view) {
        try {
            List<Integer> lengths = lengthsEditText.getLengths();
            List<Integer> intAngleIncrements = angleIncrementsEditTexts.getLengths();
            List<Float> angleIncrements = new ArrayList<>();
            for (int i = 0; i < intAngleIncrements.size(); i++) {
                angleIncrements.add(intAngleIncrements.get(i) / 800.0f);
            }
            spiroGraphView.reset(lengths, angleIncrements);
        } catch (NumberFormatException ex) {
            spiroGraphView.reset(Line.getNumberOfLines());
        }
    }

}
