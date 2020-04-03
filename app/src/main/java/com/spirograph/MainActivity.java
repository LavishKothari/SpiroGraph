package com.spirograph;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.spirograph.favourites.FavouritesActivity;
import com.spirograph.favourites.FavouritesDB;
import com.spirograph.favourites.LengthAngle;
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

    FavouritesDB favouritesDB;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        favouritesDB = new FavouritesDB(getApplicationContext());
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("  " + "SpiroGraph");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher_round);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

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
        spinner.setSelection(1);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.inviteFriend: {
                try {
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Check out this app on playstore: SPIROGRAPH"
                    );
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                } catch (Exception e) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Message sending failed!",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                break;
            }
            case R.id.help: {
                Toast.makeText(
                        getApplicationContext(),
                        "Under Development!!",
                        Toast.LENGTH_SHORT
                ).show();
                break;
            }
            case R.id.showFavourite: {
                Intent intent = new Intent(this, FavouritesActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.favouriteIcon: {
                try {
                    LengthAngle lengthAngle = new LengthAngle(
                            lengthsEditText.getLengths(),
                            angleIncrementsEditTexts.getLengths()
                    );
                    String strRepresentation = LengthAngle.getStringRepresentation(lengthAngle);
                    boolean isFav = favouritesDB.getAllValues().contains(strRepresentation);
                    if (isFav) {
                        favouritesDB.remove(strRepresentation);
                        menuItem.setIcon(R.drawable.ic_star_border_white_30dp);
                    } else {
                        favouritesDB.add(strRepresentation);
                        menuItem.setIcon(R.drawable.ic_star_white_30dp);
                    }
                } catch (Exception e) {
                    showEnterValidNumberToast();
                }
                break;
            }
        }
        return super.onOptionsItemSelected(menuItem);
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
            showEnterValidNumberToast();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void showEnterValidNumberToast() {
        Toast.makeText(
                this,
                "Enter valid numbers",
                Toast.LENGTH_LONG
        ).show();
    }

}
