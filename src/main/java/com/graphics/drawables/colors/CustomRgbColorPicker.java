package com.graphics.drawables.colors;

import javax.swing.*;
import java.awt.*;

public class CustomRgbColorPicker extends JDialog {
    private Color selectedColor;
    private JPanel colorPanel;
    private JSlider redSlider, greenSlider, blueSlider;
    private JLabel hexLabel, cmykLabel, hsvLabel;
    private JTextField redField, greenField, blueField;

    public CustomRgbColorPicker(JFrame parent, Color initialColor) {
        super(parent, "Custom Color Picker", true);
        selectedColor = initialColor;

        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(150, 50));
        colorPanel.setBackground(selectedColor);

        hexLabel = new JLabel("HEX: " + CustomColorConverter.rgbToHex(initialColor));
        cmykLabel = new JLabel("CMYK: " +
                CustomColorConverter.rgbToCMYK(initialColor)[0] + ", " +
                CustomColorConverter.rgbToCMYK(initialColor)[1] + ", " +
                CustomColorConverter.rgbToCMYK(initialColor)[2] + ", " +
                CustomColorConverter.rgbToCMYK(initialColor)[3]
        );
        hsvLabel = new JLabel("HSV: " +
                CustomColorConverter.rgbToHsv(initialColor)[0] + ", " +
                CustomColorConverter.rgbToHsv(initialColor)[1] + ", " +
                CustomColorConverter.rgbToHsv(initialColor)[2]
        );
        redSlider = createSlider("Red", 0, 255, selectedColor.getRed());
        greenSlider = createSlider("Green", 0, 255, selectedColor.getGreen());
        blueSlider = createSlider("Blue", 0, 255, selectedColor.getBlue());

        redField = createTextField("Red", selectedColor.getRed());
        greenField = createTextField("Green", selectedColor.getGreen());
        blueField = createTextField("Blue", selectedColor.getBlue());

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        setLayout(new FlowLayout());
        JPanel colorAndValuePanel = new JPanel(new GridLayout(2, 1));
        colorAndValuePanel.add(colorPanel);
        colorAndValuePanel.add(hexLabel);
        colorAndValuePanel.add(cmykLabel);
        colorAndValuePanel.add(hsvLabel);

        JPanel slidersPanel = new JPanel(new GridLayout(3, 1));
        slidersPanel.add(redSlider);
        slidersPanel.add(greenSlider);
        slidersPanel.add(blueSlider);

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2));
        fieldsPanel.add(new JLabel("Red:"));
        fieldsPanel.add(redField);
        fieldsPanel.add(new JLabel("Green:"));
        fieldsPanel.add(greenField);
        fieldsPanel.add(new JLabel("Blue:"));
        fieldsPanel.add(blueField);

        add(colorAndValuePanel);
        add(slidersPanel);
        add(fieldsPanel);
        add(okButton);

        pack();
        setLocationRelativeTo(parent);

        redSlider.addChangeListener(e -> updateColor());
        greenSlider.addChangeListener(e -> updateColor());
        blueSlider.addChangeListener(e -> updateColor());

        redField.addActionListener(e -> updateColorFromFields());
        greenField.addActionListener(e -> updateColorFromFields());
        blueField.addActionListener(e -> updateColorFromFields());
    }

    private JSlider createSlider(String label, int min, int max, int initialValue) {
        JSlider slider = new JSlider(min, max, initialValue);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(label));
        return slider;
    }

    private JTextField createTextField(String label, int initialValue) {
        JTextField textField = new JTextField(String.valueOf(initialValue));
        textField.setColumns(4);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        return textField;
    }

    private void updateColor() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        updateColorValues(red, green, blue);
    }

    private void updateColorFromFields() {
        try {
            int red = Integer.parseInt(redField.getText());
            int green = Integer.parseInt(greenField.getText());
            int blue = Integer.parseInt(blueField.getText());

            red = Math.min(Math.max(red, 0), 255);
            green = Math.min(Math.max(green, 0), 255);
            blue = Math.min(Math.max(blue, 0), 255);

            updateColorValues(red, green, blue);
        } catch (NumberFormatException ex) {

        }
    }

    private void updateColorValues(int red, int green, int blue) {
        selectedColor = new Color(red, green, blue);
        hexLabel.setText("HEX: "+CustomColorConverter.rgbToHex(selectedColor));
        cmykLabel.setText("CMYK: " +
                CustomColorConverter.rgbToCMYK(selectedColor)[0] + ", " +
                CustomColorConverter.rgbToCMYK(selectedColor)[1] + ", " +
                CustomColorConverter.rgbToCMYK(selectedColor)[2] + ", " +
                CustomColorConverter.rgbToCMYK(selectedColor)[3]);
        hsvLabel.setText("HSV: " +
                CustomColorConverter.rgbToHsv(selectedColor)[0] + ", " +
                CustomColorConverter.rgbToHsv(selectedColor)[1] + ", " +
                CustomColorConverter.rgbToHsv(selectedColor)[2]
        );
        colorPanel.setBackground(selectedColor);

        redSlider.setValue(red);
        greenSlider.setValue(green);
        blueSlider.setValue(blue);

        redField.setText(String.valueOf(red));
        greenField.setText(String.valueOf(green));
        blueField.setText(String.valueOf(blue));
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public static Color showDialog(Component parent, Color initialColor) {
        CustomRgbColorPicker colorPicker = new CustomRgbColorPicker(getFrame(parent), initialColor);
        colorPicker.setVisible(true);
        return colorPicker.getSelectedColor();
    }

    private static JFrame getFrame(Component parent) {
        if (parent instanceof JFrame) {
            return (JFrame) parent;
        } else if (parent != null) {
            return getFrame(parent.getParent());
        }
        return null;
    }
}