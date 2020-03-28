package com.spirograph;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.spirograph.shapes.Line;
import com.spirograph.shapes.Point;

import java.util.ArrayList;
import java.util.List;


public class SpiroGraphView extends View {
    Paint paint;
    long startTime;
    List<Line> lines = new ArrayList<>();
    List<Double> angles = new ArrayList<>();
    List<Double> angleIncrements = new ArrayList<>();

    List<Point> points = new ArrayList<>();

    public SpiroGraphView(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        this.startTime = System.currentTimeMillis();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);


        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        Line line1 = new Line(
                screenWidth / 2,
                screenHeight / 2,
                screenWidth / 2 + 200,
                screenHeight / 2,
                Color.RED
        );
        Line line2 = new Line(
                screenWidth / 2 + 200,
                screenHeight / 2,
                screenWidth / 2 + 350,
                screenHeight / 2,
                Color.BLUE
        );
        Line line3 = new Line(
                screenWidth / 2 + 350,
                screenHeight / 2,
                screenWidth / 2 + 450,
                screenHeight / 2,
                Color.BLUE
        );
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);

        angles.add(0.0);
        angles.add(0.0);
        angles.add(0.0);

        angleIncrements.add(0.01);
        angleIncrements.add(0.08);
        angleIncrements.add(0.2);

        this.postInvalidate();
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        points.add(
                new Point(
                        lines.get(lines.size() - 1).getStopX(),
                        lines.get(lines.size() - 1).getStopY()
                )
        );

        for (int i = 0; i < points.size(); i++) {
            points.get(i).drawPoint(paint, canvas);
        }

        for (int i = 0; i < angles.size(); i++) {
            angles.set(i, angles.get(i) + angleIncrements.get(i));
        }

        List<Line> newLines = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            if (i == 0) {
                newLines.add(lines.get(i).getRotatedLine(angles.get(i)));
            } else {
                newLines.add(
                        lines.get(i).getRotatedLine(
                                lines.get(i - 1).getStopX(),
                                lines.get(i - 1).getStopY(),
                                angles.get(i)
                        )
                );
            }
        }
        lines = newLines;

        invalidate();
    }

}
