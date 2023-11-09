package com.graphics.drawables.colors;

import javax.swing.*;
import java.awt.*;

public class CustomHsvColorPicker extends JDialog {
    private Color selectedColor;
    private JPanel colorPanel;
    private JSlider hueSlider, saturationSlider, valueSlider;
    private JLabel hexLabel, cmykLabel, rgbLabel;
    private JTextField hueField, saturationField, valueField;

    public CustomHsvColorPicker(JFrame parent, Color initialColor) {
        super(parent, "Custom HSV Color Picker", true);
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
        rgbLabel = new JLabel("RGB: " +
                selectedColor.getRed() + ", " +
                selectedColor.getGreen() + ", " +
                selectedColor.getBlue()
        );

        hueSlider = createSlider("Hue", 0, 360, CustomColorConverter.rgbToHsv(initialColor)[0]);
        saturationSlider = createSlider("Saturation", 0, 100, CustomColorConverter.rgbToHsv(initialColor)[1]);
        valueSlider = createSlider("Value", 0, 100, CustomColorConverter.rgbToHsv(initialColor)[2]);

        hueField = createTextField("Hue", CustomColorConverter.rgbToHsv(initialColor)[0]);
        saturationField = createTextField("Saturation", CustomColorConverter.rgbToHsv(initialColor)[1]);
        valueField = createTextField("Value", CustomColorConverter.rgbToHsv(initialColor)[2]);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        setLayout(new FlowLayout());
        JPanel colorAndValuePanel = new JPanel(new GridLayout(2, 1));
        colorAndValuePanel.add(colorPanel);
        colorAndValuePanel.add(hexLabel);
        colorAndValuePanel.add(cmykLabel);
        colorAndValuePanel.add(rgbLabel);

        JPanel slidersPanel = new JPanel(new GridLayout(3, 1));
        slidersPanel.add(hueSlider);
        slidersPanel.add(saturationSlider);
        slidersPanel.add(valueSlider);

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 2));
        fieldsPanel.add(new JLabel("Hue:"));
        fieldsPanel.add(hueField);
        fieldsPanel.add(new JLabel("Saturation:"));
        fieldsPanel.add(saturationField);
        fieldsPanel.add(new JLabel("Value:"));
        fieldsPanel.add(valueField);

        add(colorAndValuePanel);
        add(slidersPanel);
        add(fieldsPanel);
        add(okButton);

        pack();
        setLocationRelativeTo(parent);

        hueSlider.addChangeListener(e -> updateColor());
        saturationSlider.addChangeListener(e -> updateColor());
        valueSlider.addChangeListener(e -> updateColor());

        hueField.addActionListener(e -> updateColorFromFields());
        saturationField.addActionListener(e -> updateColorFromFields());
        valueField.addActionListener(e -> updateColorFromFields());
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
        int hue = hueSlider.getValue();
        int saturation = saturationSlider.getValue();
        int value = valueSlider.getValue();
        updateColorValues(hue, saturation, value);
    }

    private void updateColorFromFields() {
        try {
            int hue = Integer.parseInt(hueField.getText());
            int saturation = Integer.parseInt(saturationField.getText());
            int value = Integer.parseInt(valueField.getText());

            hue = Math.min(Math.max(hue, 0), 360);
            saturation = Math.min(Math.max(saturation, 0), 100);
            value = Math.min(Math.max(value, 0), 100);

            updateColorValues(hue, saturation, value);
        } catch (NumberFormatException ex) {

        }
    }

    private void updateColorValues(int hue, int saturation, int value) {
        int[] tmpColor = CustomColorConverter.hsvToRGB(new int[]{hue, saturation, value});
        this.selectedColor = new Color(tmpColor[0], tmpColor[1], tmpColor[2]);
        hexLabel.setText("HEX: " + CustomColorConverter.rgbToHex(selectedColor));
        cmykLabel.setText("CMYK: " +
                CustomColorConverter.rgbToCMYK(selectedColor)[0] + ", " +
                CustomColorConverter.rgbToCMYK(selectedColor)[1] + ", " +
                CustomColorConverter.rgbToCMYK(selectedColor)[2] + ", " +
                CustomColorConverter.rgbToCMYK(selectedColor)[3]);
        rgbLabel.setText("RGB: " +
                selectedColor.getRed() + ", " +
                selectedColor.getGreen() + ", " +
                selectedColor.getBlue()
        );
        colorPanel.setBackground(selectedColor);

        hueSlider.setValue(hue);
        saturationSlider.setValue(saturation);
        valueSlider.setValue(value);

        hueField.setText(String.valueOf(hue));
        saturationField.setText(String.valueOf(saturation));
        valueField.setText(String.valueOf(value));


    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public static Color showDialog(Component parent, Color initialColor) {
        CustomHsvColorPicker colorPicker = new CustomHsvColorPicker(getFrame(parent), initialColor);
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