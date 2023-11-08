package com.graphics;

import com.graphics.drawables.utils.ShapeButtonListener;
import com.graphics.drawables.utils.ShapeType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOError;
import java.io.IOException;

public class ToolsPanel extends JPanel {
    private CanvasPanel canvas;

    public ToolsPanel(CanvasPanel canvas) {
        this.canvas = canvas;
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(150, 600)); // Use preferred size instead of setSize
        this.setLocation(650, 0);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton triangleButton = new JButton("Triangle");
        JButton rectangleButton = new JButton("Rectangle");
        JButton ellipseButton = new JButton("Ellipse");
        JButton lineButton = new JButton("Line");
        JButton textButton = new JButton("Text");
        JButton saveButton = new JButton("Save image");
        JButton resetButton = new JButton("Reset");

        triangleButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.TRIANGLE));
        rectangleButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.RECTANGLE));
        ellipseButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.ELLIPSE));
        lineButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.LINE));
        textButton.addActionListener(new ShapeButtonListener(canvas,ShapeType.TEXT));
        saveButton.addActionListener(e ->{
            this.canvas.saveImageToFile("image.png");
        });
        resetButton.addActionListener(e -> {
            this.canvas.clearAllDrawables();
        });

        add(triangleButton);
        add(rectangleButton);
        add(ellipseButton);
        add(lineButton);
        add(textButton);
        add(saveButton);
        add(resetButton);
    }
}
