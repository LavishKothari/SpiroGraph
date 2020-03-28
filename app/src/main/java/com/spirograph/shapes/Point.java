package com.spirograph.shapes;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Point {
    private float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Float.compare(point.x, x) == 0 &&
                Float.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return new Float(x).hashCode() + new Float(y).hashCode();
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void drawPoint(Paint paint, Canvas canvas) {
        canvas.drawPoint(this.x, this.y, paint);
    }
}
