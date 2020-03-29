package com.spirograph;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private static int numberOfLines = 0;

    private SpiroGraphView spiroGraphView;

    private Context context;

    private LinearLayout dynamicEditTextsLayout;

    private EditTextCollection lengthsEditTextCollection;

    public SpinnerOnItemSelectedListener(
            SpiroGraphView spiroGraphView,
            Context context,
            LinearLayout dynamicEditTextsLayout,
            EditTextCollection lengthsEditTextCollection
    ) {
        this.spiroGraphView = spiroGraphView;
        this.context = context;
        this.dynamicEditTextsLayout = dynamicEditTextsLayout;
        this.lengthsEditTextCollection = lengthsEditTextCollection;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dynamicEditTextsLayout.removeAllViews();
        int n = position + 1;
        spiroGraphView.reset(n);
        numberOfLines = 0;

        LinearLayout parentLinearLayout = getLinearLayout(LinearLayout.VERTICAL);

        dynamicEditTextsLayout.addView(parentLinearLayout);

        LinearLayout lengthsLinearLayout = getLinearLayout(LinearLayout.HORIZONTAL);
        TextView lengthsTextView = addTextView("  Lengths (between 50 to 150): ", lengthsLinearLayout);
        for (int i = 0; i < n; i++) {
            numberOfLines = lengthsEditTextCollection.addEditText(
                    context,
                    numberOfLines,
                    lengthsLinearLayout
            );
        }

        LinearLayout speedsLinearLayout = getLinearLayout(LinearLayout.HORIZONTAL);
        EditTextCollection editTextCollectionForSpeeds = new EditTextCollection();
        TextView speedsTextView = addTextView(
                "  Speeds (between 0 to 100): ",
                speedsLinearLayout
        );
        for (int i = 0; i < n; i++) {
            editTextCollectionForSpeeds.addEditText(
                    context,
                    numberOfLines,
                    speedsLinearLayout
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
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
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
        return textView;
    }

}
