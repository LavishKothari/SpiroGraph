package com.lavi.spirograph.favourites;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lavi.spirograph.MainActivity;
import com.lavi.spirograph.R;
import com.lavi.spirograph.db.DBUtils;

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

        ScrollView scrollView = new ScrollView(getApplicationContext());
        scrollView.addView(parentLinearLayout);
        setContentView(scrollView);
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
        linearLayout.setBackgroundResource(R.drawable.fav_tv_border);

        TextView textView = getTextView(s, key);
        ImageButton deleteButton = getDeleteButton(linearLayout, key);
        ImageButton shareButton = getShareButton(s, key);

        linearLayout.addView(textView);
        linearLayout.addView(deleteButton);
        linearLayout.addView(shareButton);

        return linearLayout;
    }

    private TextView getTextView(String text, final String key) {
        final TextView textView = new TextView(this);
        textView.setText(text);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(1, 18, 1, 1);
        textView.setLayoutParams(params);

        textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBUtils.getCoordinateDB().clearThenAdd(key);
                Intent refresh = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(refresh);
                finish();
            }
        });
        return textView;
    }

    private ImageButton getShareButton(final String s, final String key) {
        final ImageButton shareButton = new ImageButton(this);
        shareButton.setImageResource(R.drawable.ic_share_green_24dp);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 15, 1, 1);
        shareButton.setLayoutParams(params);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT,
                            "Check out this app on Play Store: SPIROGRAPH\n" + s
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
            }
        });

        shareButton.setBackgroundColor(Color.TRANSPARENT);
        return shareButton;
    }

    private ImageButton getDeleteButton(final LinearLayout linearLayout, final String key) {
        final ImageButton deleteButton = new ImageButton(this);
        deleteButton.setImageResource(R.drawable.ic_delete_black_30dp);
        deleteButton.setOnClickListener(new View.OnClickListener() {
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

        deleteButton.setBackgroundColor(Color.TRANSPARENT);
        return deleteButton;
    }

}
