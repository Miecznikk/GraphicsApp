package com.graphics.drawables;

import java.awt.*;

public class DrawablePoint extends Drawable{
    private Point point;
    public DrawablePoint(Point point, Color color){
        this.point = point;
        this.color = color;
    }
    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(this.point.x,this.point.y,5,5);
    }

    @Override
    public boolean contains(Point point) {
        return false;
    }

    @Override
    public void move(int dx, int dy) {
        return;
    }
}
