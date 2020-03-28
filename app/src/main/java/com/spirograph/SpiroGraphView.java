package com.spirograph;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;

import com.spirograph.shapes.Line;
import com.spirograph.shapes.Point;

import java.util.ArrayList;
import java.util.List;


public class SpiroGraphView extends View {
    private Paint paint;
    private List<Line> lines = new ArrayList<>();
    private List<Double> angles = new ArrayList<>();
    private List<Double> angleIncrements = new ArrayList<>();

    private List<Point> points = new ArrayList<>();

    private int numberOfLines = 2;

    private boolean stop = false;

    public void stopButtonClicked() {
        stop = true;
    }

    public void setNumberOfLines(int n) {
        this.numberOfLines = n;
    }

    public void resumeButtonClicked() {
        stop = false;
        invalidate();
    }

    public void restartButtonClicked() {
        points = new ArrayList<>();
        initializeLines();
        stop = false;
        invalidate();
    }

    public SpiroGraphView(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(2);

        initializeLines();

        this.postInvalidate();
    }

    private void initializeLines() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        ((Activity) getContext()).getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        lines = new ArrayList<>();
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
                screenWidth / 2 + 300,
                screenHeight / 2,
                Color.GREEN
        );
        Line line3 = new Line(
                screenWidth / 2 + 300,
                screenHeight / 2,
                screenWidth / 2 + 350,
                screenHeight / 2,
                Color.YELLOW
        );
        Line line4 = new Line(
                screenWidth / 2 + 350,
                screenHeight / 2,
                screenWidth / 2 + 420,
                screenHeight / 2,
                Color.BLUE
        );
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        lines.add(line4);

        lines = lines.subList(0, numberOfLines);

        angles = new ArrayList<>();
        for (int i = 0; i < numberOfLines; i++) {
            angles.add(0.0);
        }

        angleIncrements.add(0.01);
        angleIncrements.add(0.08);
        angleIncrements.add(0.2);
        angleIncrements.add(0.1);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        Point newPoint = new Point(
                lines.get(numberOfLines - 1).getStopX(),
                lines.get(numberOfLines - 1).getStopY()
        );
        points.add(newPoint);

        Path path = new Path();
        path.moveTo(points.get(0).getX(), points.get(0).getY());
        for (int i = 1; i < points.size(); i++) {
//            points.get(i).drawPoint(paint, canvas);
            path.lineTo(points.get(i).getX(), points.get(i).getY());
        }
        canvas.drawPath(path, paint);
        for (int i = 0; i < numberOfLines; i++) {
            lines.get(i).draw(paint, canvas);
        }

        /*
            stop only after drawing
         */
        if (stop) {
            return;
        }

        for (int i = 0; i < numberOfLines; i++) {
            angles.set(i, angles.get(i) + angleIncrements.get(i));
        }

        List<Line> newLines = new ArrayList<>();
        for (int i = 0; i < numberOfLines; i++) {
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
