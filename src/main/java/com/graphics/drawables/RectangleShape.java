package com.graphics.drawables;

import java.awt.*;

public class RectangleShape extends Drawable {
    private Rectangle rectangle;
    public RectangleShape(Rectangle rectangle){
        this.rectangle = rectangle;
    }
    @Override
    public void draw(Graphics g){
        g.setColor(this.color);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
    @Override
    public boolean contains(Point point) {
        return rectangle.contains(point);
    }
    @Override
    public void move(int dx, int dy) {
        rectangle.translate(dx, dy);
    }
}
