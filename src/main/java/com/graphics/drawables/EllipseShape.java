package com.graphics.drawables;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class EllipseShape extends Drawable {
    private Ellipse2D ellipse;

    public EllipseShape(Ellipse2D ellipse) {
        this.ellipse = ellipse;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillOval(
                (int) ellipse.getX(),
                (int) ellipse.getY(),
                (int) ellipse.getWidth(),
                (int) ellipse.getHeight()
        );
    }
    @Override
    public boolean contains(Point point) {
        return ellipse.contains(point);
    }
    @Override
    public void move(int dx, int dy) {
        double x = ellipse.getX() + dx;
        double y = ellipse.getY() + dy;
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();
        ellipse.setFrame(x, y, width, height);
    }
}