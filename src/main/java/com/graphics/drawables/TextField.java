package com.graphics.drawables;

import java.awt.*;
public class TextField extends Drawable {
    private String text;
    private int x, y;
    private Font font;

    public TextField(String text, int x, int y, Font font) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.font = font;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.setFont(font);
        g.drawString(text, x, y);
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