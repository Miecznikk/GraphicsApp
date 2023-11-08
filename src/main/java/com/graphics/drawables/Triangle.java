package com.graphics.drawables;

import java.awt.*;

public class Triangle extends Drawable {
    private Polygon triangle;
    public Triangle(Polygon triangle){
        this.triangle = triangle;
    }
    @Override
    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillPolygon(triangle);
    }
    @Override
    public boolean contains(Point point) {
        return triangle.contains(point);
    }
    @Override
    public void move(int dx, int dy) {
        triangle.translate(dx, dy);
    }
}
