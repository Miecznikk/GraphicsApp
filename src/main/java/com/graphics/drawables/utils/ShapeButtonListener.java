package com.graphics.drawables.utils;

import com.graphics.CanvasPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShapeButtonListener implements ActionListener {
    private final CanvasPanel canvas;
    private final ShapeType shapeType;

    public ShapeButtonListener(CanvasPanel canvas, ShapeType shapeType) {
        this.canvas = canvas;
        this.shapeType = shapeType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        canvas.setSelectedShape(shapeType);
    }
}
