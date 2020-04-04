package com.spirograph.favourites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spirograph.MainActivity;
import com.spirograph.db.DBUtils;

import java.util.Set;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayout parentLinearLayout = new LinearLayout(getApplicationContext());
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);

        Set<String> values = DBUtils.getFavouritesDB().getAllValues();
        for (String s : values) {
            LengthAngle ln = LengthAngle.getObject(s);
            parentLinearLayout.addView(getInnerLayout(ln.toString(), s));
        }
        setContentView(parentLinearLayout);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh);
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(refresh);
        finish();
    }

    private LinearLayout getInnerLayout(
            final String s,
            final String key
    ) {
        final LinearLayout linearLayout = new LinearLayout(getApplicationContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        final TextView tv = new TextView(this);
        tv.setText(s);
        tv.setBackgroundColor(0xFFCCCCCC);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.getCoordinateDB().clearThenAdd(key);
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
                new AlertDialog.Builder(FavouritesActivity.this)
                        .setTitle("Confirmation Message")
                        .setMessage("Are you sure you want to delete this SpiroGraph?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(
                                android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        DBUtils.getFavouritesDB().remove(key);
                                        linearLayout.removeAllViews();
                                    }
                                }
                        )
                        .setNegativeButton(
                                android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                }
                        ).show();
            }
        });
        linearLayout.addView(tv);
        linearLayout.addView(button);
        return linearLayout;
    }
}
