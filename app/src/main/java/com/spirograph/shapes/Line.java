package com.spirograph.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Line {
    private float startX, startY, stopX, stopY;

    private int color;

    public Line(float startX, float startY, float stopX, float stopY, int color) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        this.color = color;
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

    public double getLength() {
        return Math.sqrt(
                (stopX - startX) * (stopX - startX) +
                        (stopY - startY) * (stopY - startY)
        );
    }

    public Line getRotatedLine(float x, float y, double angle) {
        double length = getLength();
        return new Line(
                x,
                y,
                (float) (x + length * Math.cos(angle)),
                (float) (y - length * Math.sin(angle)),
                this.color
        );
    }

    public Line getRotatedLine(double angle) {
        double length = getLength();
        return new Line(
                this.startX,
                this.startY,
                (float) (startX + length * Math.cos(angle)),
                (float) (startY - length * Math.sin(angle)),
                this.color
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
