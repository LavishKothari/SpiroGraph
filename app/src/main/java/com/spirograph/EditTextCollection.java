package com.spirograph;

import android.content.Context;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.spirograph.db.DBUtils;
import com.spirograph.favourites.LengthAngle;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class EditTextCollection {

    public List<EditText> editTextList = new ArrayList<>();

    public int addEditText(
            final Context context,
            int numberOfLines,
            LinearLayout dynamicEditTextsLayout,
            int maxAllowableDigits,
            int width,
            String text,
            final SpiroGraphView spiroGraphView,
            final EditTextCollection etc1,
            final EditTextCollection etc2
    ) {
        EditText editText = new EditText(context);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        p.setMargins(0, 0, 0, 0);
        editText.setLayoutParams(p);
        editText.setId(numberOfLines + 1);
        editText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        editText.setText(text);

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxAllowableDigits);
        editText.setFilters(filterArray);

        editText.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                try {
                    ((AppCompatActivity)context).invalidateOptionsMenu();
                    DBUtils.getCoordinateDB().clearThenAdd(
                            LengthAngle.getStringRepresentation(
                                    new LengthAngle(etc1.getLengths(), etc2.getLengths())
                            )
                    );
                    MainActivity.restart(
                            etc1.getLengths(),
                            etc2.getLengths(),
                            spiroGraphView
                    );
                } catch (Exception e) {
                    Toast.makeText(
                            context,
                            "Enter a valid number",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                return false;
            }
        });

        editText.setMinWidth(width);
        editText.setMaxWidth(width);
        dynamicEditTextsLayout.addView(editText);
        editTextList.add(editText);
        numberOfLines++;
        return numberOfLines;
    }

    public List<Integer> getLengths() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < editTextList.size(); i++) {
            result.add(Integer.parseInt(editTextList.get(i).getText().toString()));
        }
        return result;
    }

    public void clear() {
        editTextList = new ArrayList<>();
    }

}
