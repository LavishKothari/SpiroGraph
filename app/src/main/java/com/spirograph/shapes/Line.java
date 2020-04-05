package com.spirograph.shapes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Line {
    private float startX, startY, stopX, stopY;

    private float length;
    private float angle;
    private int angleIncrement;

    private int color;

    private static int numberOfLines = 1;

    public static int getNumberOfLines() {
        return numberOfLines;
    }

    public Line(
            float startX,
            float startY,
            float stopX,
            float stopY,
            int color,
            float length,
            float angle,
            Integer angleIncrement
    ) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        this.color = color;
        this.length = length;
        this.angleIncrement = angleIncrement;
        this.angle = angle;
    }

    public static List<Line> getLines(
            int screenWidth,
            int screenHeight,
            int numberOfLines,
            List<Integer> lengths,
            List<Integer> angleIncrements
    ) {
        Line.numberOfLines = numberOfLines;

        List<Integer> color = Arrays.asList(
                0xFFFF00FF,
                0xFF005500,
                0xFF0000FF,
                0xFF009999
        );

        List<Line> lines = new ArrayList<>();
        int currentDistance = 0;
        for (int i = 0; i < numberOfLines; i++) {
            lines.add(
                    new Line(
                            screenWidth / 2.0f + currentDistance,
                            screenHeight / 2.0f,
                            screenWidth / 2.0f + currentDistance + lengths.get(i),
                            screenHeight / 2.0f,
                            color.get(i),
                            lengths.get(i),
                            0.0f,
                            angleIncrements.get(i)
                    )
            );
            currentDistance += lengths.get(i);
        }
        return lines;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getStopX() {
        return stopX;
    }

    public void setStopX(float stopX) {
        this.stopX = stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public void setStopY(float stopY) {
        this.stopY = stopY;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void draw(Paint paint, Canvas canvas) {
        int prevColor = paint.getColor();
        paint.setColor(color);
        canvas.drawLine(this.startX, this.startY, this.stopX, this.stopY, paint);
        paint.setColor(prevColor);
    }

    private float getLength() {
        return (float) Math.sqrt(
                (stopX - startX) * (stopX - startX) +
                        (stopY - startY) * (stopY - startY)
        );
    }

    private float getSanitizedAngleIncrement(int currentAngleIncrement) {
        return currentAngleIncrement / 800.0f;
    }

    public Line getRotatedLine(float x, float y) {
        float length = getLength();
        return new Line(
                x,
                y,
                (float) (x + length * Math.cos(angle + getSanitizedAngleIncrement(angleIncrement))),
                (float) (y - length * Math.sin(angle + getSanitizedAngleIncrement(angleIncrement))),
                this.color,
                length,
                angle + getSanitizedAngleIncrement(angleIncrement),
                this.angleIncrement
        );
    }

    public Line getRotatedLine() {
        float length = getLength();
        return new Line(
                this.startX,
                this.startY,
                (float) (startX + length * Math.cos(angle + getSanitizedAngleIncrement(angleIncrement))),
                (float) (startY - length * Math.sin(angle + getSanitizedAngleIncrement(angleIncrement))),
                this.color,
                length,
                angle + getSanitizedAngleIncrement(angleIncrement),
                this.angleIncrement
        );
    }

    @Override
    public String toString() {
        return "Line{" +
                "startX=" + startX +
                ", startY=" + startY +
                ", stopX=" + stopX +
                ", stopY=" + stopY +
                ", color=" + color +
                '}';
    }
}
