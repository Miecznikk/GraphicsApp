package com.graphics;

import com.graphics.drawables.colors.CustomCmykColorPicker;
import com.graphics.drawables.colors.CustomHsvColorPicker;
import com.graphics.drawables.colors.CustomRgbColorPicker;
import com.graphics.drawables.utils.ShapeButtonListener;
import com.graphics.drawables.utils.ShapeType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolsPanel extends JPanel {
    private CanvasPanel canvas;

    public ToolsPanel(CanvasPanel canvas) {
        this.canvas = canvas;
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(200, 600));
        this.setLocation(650, 0);

        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton triangleButton = new JButton("Triangle");
        JButton rectangleButton = new JButton("Rectangle");
        JButton ellipseButton = new JButton("Ellipse");
        JButton lineButton = new JButton("Line");
        JButton freeDrawButton = new JButton("Pencil");
        JButton colorRgbPickButton = new JButton("RGB");
        JButton colorHsvPickButton = new JButton("HSV");
        JButton colorCmykPickButton = new JButton("CMYK");
        JButton textButton = new JButton("Text");
        JButton saveButton = new JButton("Save image");
        JButton resetButton = new JButton("Reset");
        JPanel pickersPanel = new JPanel();
        JPanel colorPanel = new JPanel();
        pickersPanel.setBackground(Color.LIGHT_GRAY);
        colorPanel.setPreferredSize(new Dimension(30,30));
        colorPanel.setBackground(this.canvas.getCurrentColor());

        colorRgbPickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = CustomRgbColorPicker.showDialog(ToolsPanel.this, canvas.getCurrentColor());

                if (newColor != null) {
                    canvas.setCurrentColor(newColor);
                    colorPanel.setBackground(newColor);
                }
            }
        });
        colorHsvPickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = CustomHsvColorPicker.showDialog(ToolsPanel.this, canvas.getCurrentColor());

                if (newColor != null) {
                    canvas.setCurrentColor(newColor);
                    colorPanel.setBackground(newColor);
                }
            }
        });
        colorCmykPickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color newColor = CustomCmykColorPicker.showDialog(ToolsPanel.this, canvas.getCurrentColor());

                if (newColor != null) {
                    canvas.setCurrentColor(newColor);
                    colorPanel.setBackground(newColor);
                }
            }
        });
        triangleButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.TRIANGLE));
        rectangleButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.RECTANGLE));
        ellipseButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.ELLIPSE));
        lineButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.LINE));
        freeDrawButton.addActionListener(new ShapeButtonListener(canvas, ShapeType.PENCIL));
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
        add(freeDrawButton);
        add(textButton);
        add(saveButton);
        add(resetButton);
        pickersPanel.add(colorRgbPickButton);
        pickersPanel.add(colorHsvPickButton);
        pickersPanel.add(colorCmykPickButton);
        add(pickersPanel);
        add(colorPanel);
    }
}
