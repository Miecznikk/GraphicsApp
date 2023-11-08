package com.graphics.drawables;

import java.awt.*;

public abstract class Drawable {
    protected Color color = Color.BLACK;
    public void draw(Graphics g){

    }

    public abstract boolean contains(Point point);

    public abstract void move(int dx, int dy);
}

