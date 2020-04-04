package com.spirograph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import com.spirograph.db.CoordinateDB;
import com.spirograph.favourites.LengthAngle;
import com.spirograph.shapes.Line;
import com.spirograph.shapes.Point;

import java.util.ArrayList;
import java.util.List;


public class SpiroGraphView extends View {

    private Utils utils = new Utils(getContext());

    private Paint paint;

    private List<Line> lines = new ArrayList<>();

    private Path path = new Path();

    private boolean stop = false;

    private CoordinateDB coordinateDB;

    // constructor
    public SpiroGraphView(Context context, Context applicationContext) {
        super(context);

        coordinateDB = new CoordinateDB(applicationContext);

        paint = new Paint();
        paint.setAntiAlias(true);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);

        if (coordinateDB.isEmpty()) {
            coordinateDB.clearThenAdd(LengthAngle.getDefault(3));
        }
        reset();

        this.postInvalidate();
    }

    private void reset() {
        int n = LengthAngle.getObject(coordinateDB.getFirstValue()).getLengths().size();
        reset(n);
    }

    public void reset(int n) {
        path = new Path();
        stop = false;
        String lengthAngleString = coordinateDB.getFirstValue();
        LengthAngle lengthAngle = LengthAngle.getObject(lengthAngleString);
        lines = Line.getLines(
                utils.getScreenWidth(),
                utils.getScreenHeight(),
                n,
                lengthAngle.getLengths(),
                lengthAngle.getAngles()
        );
        invalidate();
    }

    public void reset(List<Integer> lengths, List<Integer> angleIncrements) {
        path = new Path();
        stop = false;
        lines = Line.getLines(
                utils.getScreenWidth(),
                utils.getScreenHeight(),
                lengths.size(),
                lengths,
                angleIncrements
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

        if (path.isEmpty()) {
            path.moveTo(newPoint.getX(), newPoint.getY());
        } else {
            path.lineTo(newPoint.getX(), newPoint.getY());
        }

        canvas.drawPath(path, paint);
        /*
            stop only after drawing all the points
         */
        if (stop) {
            return;
        }

        for (int i = 0; i < Line.getNumberOfLines(); i++) {
            lines.get(i).draw(paint, canvas);
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
