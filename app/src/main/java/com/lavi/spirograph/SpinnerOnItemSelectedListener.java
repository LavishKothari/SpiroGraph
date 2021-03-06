package com.lavi.spirograph;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lavi.spirograph.db.CoordinateDB;
import com.lavi.spirograph.favourites.LengthAngle;

import androidx.appcompat.app.AppCompatActivity;

public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private static int numberOfLines = 0;

    private SpiroGraphView spiroGraphView;

    private Context context;

    private LinearLayout dynamicEditTextsLayout;

    private EditTextCollection lengthsEditTextCollection;
    private EditTextCollection angleIncrementsEditTextCollection;

    private CoordinateDB coordinateDB;

    public SpinnerOnItemSelectedListener(
            SpiroGraphView spiroGraphView,
            Context context,
            Context applicationContext,
            LinearLayout dynamicEditTextsLayout,
            EditTextCollection lengthsEditTextCollection,
            EditTextCollection angleIncrementsEditTextCollection
    ) {
        coordinateDB = new CoordinateDB(applicationContext);
        this.spiroGraphView = spiroGraphView;
        this.context = context;
        this.dynamicEditTextsLayout = dynamicEditTextsLayout;
        this.lengthsEditTextCollection = lengthsEditTextCollection;
        this.angleIncrementsEditTextCollection = angleIncrementsEditTextCollection;

        this.lengthsEditTextCollection.clear();
        this.angleIncrementsEditTextCollection.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dynamicEditTextsLayout.removeAllViews();

        ((AppCompatActivity)context).invalidateOptionsMenu();

        this.lengthsEditTextCollection.clear();
        this.angleIncrementsEditTextCollection.clear();
        int n = position + 1;
        numberOfLines = 0;

        LinearLayout parentLinearLayout = getLinearLayout(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        p.setMargins(15, 0, 0, 0);
        parentLinearLayout.setLayoutParams(p);

        dynamicEditTextsLayout.addView(parentLinearLayout);

        if (coordinateDB.isEmpty() ||
                LengthAngle.getObject(coordinateDB.getFirstValue()).getLengths().size() != n) {
            coordinateDB.clearThenAdd(LengthAngle.getDefault(n));
        }

        LengthAngle lengthAngle = LengthAngle.getObject(coordinateDB.getFirstValue());

        LinearLayout lengthsLinearLayout = getLinearLayout(LinearLayout.HORIZONTAL);
        addTextView("Lengths (50 to 150):", lengthsLinearLayout);
        for (int i = 0; i < n; i++) {
            numberOfLines = lengthsEditTextCollection.addEditText(
                    context,
                    numberOfLines,
                    lengthsLinearLayout,
                    3,
                    120,
                    lengthAngle.getLengths().get(i).toString(),
                    spiroGraphView,
                    lengthsEditTextCollection,
                    angleIncrementsEditTextCollection
            );
        }

        LinearLayout speedsLinearLayout = getLinearLayout(LinearLayout.HORIZONTAL);
        addTextView(
                "Speeds (0 to 99):",
                speedsLinearLayout
        );
        for (int i = 0; i < n; i++) {
            angleIncrementsEditTextCollection.addEditText(
                    context,
                    numberOfLines,
                    speedsLinearLayout,
                    2,
                    90,
                    lengthAngle.getAngles().get(i).toString(),
                    spiroGraphView,
                    lengthsEditTextCollection,
                    angleIncrementsEditTextCollection
            );
        }

        parentLinearLayout.addView(lengthsLinearLayout);
        parentLinearLayout.addView(speedsLinearLayout);

        spiroGraphView.reset(numberOfLines);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private LinearLayout getLinearLayout(int orientation) {
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(0, 0, 0, 0);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(orientation);

        return linearLayout;
    }

    /**
     * @param text
     * @return the added {@link TextView}
     */
    private TextView addTextView(String text, LinearLayout linearLayout) {
        TextView textView = new TextView(context);
        textView.setSingleLine(false);
        textView.setTextSize(15.0f);
        textView.setTextColor(Color.rgb(0, 0, 200));
        textView.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        ));
        textView.setText(text);
        linearLayout.addView(textView);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(Typeface.MONOSPACE, Typeface.BOLD);
        return textView;
    }

}
