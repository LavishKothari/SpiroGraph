package com.spirograph;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SpinnerOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private static int numberOfLines = 0;

    private SpiroGraphView spiroGraphView;

    private Context context;

    private LinearLayout dynamicEditTextsLayout;

    public SpinnerOnItemSelectedListener(SpiroGraphView spiroGraphView, Context context,
                                         LinearLayout dynamicEditTextsLayout) {
        this.spiroGraphView = spiroGraphView;
        this.context = context;
        this.dynamicEditTextsLayout = dynamicEditTextsLayout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        dynamicEditTextsLayout.removeAllViews();
        int n = position + 1;
        spiroGraphView.reset(n);
        numberOfLines = 0;
        EditTextCollection.clear();
        for (int i = 0; i < n; i++) {
            numberOfLines = EditTextCollection.addEditText(
                    context,
                    numberOfLines,
                    dynamicEditTextsLayout
            );
        }
        spiroGraphView.restartButtonClicked();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
