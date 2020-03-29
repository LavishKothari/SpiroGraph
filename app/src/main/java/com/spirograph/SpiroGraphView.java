package com.spirograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.spirograph.shapes.Line;
import com.spirograph.shapes.Point;

import java.util.ArrayList;
import java.util.List;


public class SpiroGraphView extends View {

    private Utils utils = new Utils(getContext());

    private Paint paint;

    private List<Line> lines = new ArrayList<>();

    private List<Point> points = new ArrayList<>();

    private boolean stop = false;

    // constructor
    public SpiroGraphView(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);

        reset(2);

        this.postInvalidate();
    }

    public void reset(int n) {
        points = new ArrayList<>();
        stop = false;
        lines = Line.getLines(utils.getScreenWidth(), utils.getScreenHeight(), n);
        invalidate();
    }

    public void reset(List<Integer> lengths) {
        points = new ArrayList<>();
        stop = false;
        lines = Line.getLines(
                utils.getScreenWidth(),
                utils.getScreenHeight(),
                lengths.size(),
                lengths
        );
        invalidate();
    }

    public void resumeButtonClicked() {
        stop = false;
        invalidate();
    }

    public void stopButtonClicked() {
        stop = true;
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        Point newPoint = new Point(
                lines.get(Line.getNumberOfLines() - 1).getStopX(),
                lines.get(Line.getNumberOfLines() - 1).getStopY()
        );
        points.add(newPoint);

        Path path = new Path();
        path.moveTo(points.get(0).getX(), points.get(0).getY());
        for (int i = 1; i < points.size(); i++) {
            path.lineTo(points.get(i).getX(), points.get(i).getY());
        }
        canvas.drawPath(path, paint);
        for (int i = 0; i < Line.getNumberOfLines(); i++) {
            lines.get(i).draw(paint, canvas);
        }

        /*
            stop only after drawing
         */
        if (stop) {
            return;
        }

        List<Line> newLines = new ArrayList<>();
        for (int i = 0; i < Line.getNumberOfLines(); i++) {
            if (i == 0) {
                newLines.add(lines.get(i).getRotatedLine());
            } else {
                newLines.add(
                        lines.get(i).getRotatedLine(
                                lines.get(i - 1).getStopX(),
                                lines.get(i - 1).getStopY()
                        )
                );
            }
        }
        lines = newLines;

        invalidate();
    }

}
