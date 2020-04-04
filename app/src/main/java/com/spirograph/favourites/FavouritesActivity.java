package com.spirograph.favourites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spirograph.MainActivity;
import com.spirograph.db.CoordinateDB;
import com.spirograph.db.FavouritesDB;

import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class FavouritesActivity extends AppCompatActivity {

    FavouritesDB favouritesDB;
    CoordinateDB coordinateDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        favouritesDB = new FavouritesDB(getApplicationContext());
        coordinateDB = new CoordinateDB(getApplicationContext());

        LinearLayout parentLinearLayout = new LinearLayout(getApplicationContext());
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);

        Set<String> values = favouritesDB.getAllValues();
        for (String s : values) {
            LengthAngle ln = LengthAngle.getObject(s);
            parentLinearLayout.addView(getInnerLayout(ln.toString(), s));
        }
        setContentView(parentLinearLayout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private LinearLayout getInnerLayout(
            final String s,
            final String key
    ) {
        final LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        final TextView tv = new TextView(this);
        tv.setText(s);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coordinateDB.clearThenAdd(key);
                Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });
        final Button button = new Button(this);
        button.setText("Delete");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favouritesDB.remove(key);
                linearLayout.removeAllViews();
            }
        });
        linearLayout.addView(tv);
        linearLayout.addView(button);
        return linearLayout;
    }
}
