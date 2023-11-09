package com.graphics.drawables.colors;

import javax.swing.*;
import java.awt.*;

public class CustomCmykColorPicker extends JDialog {
    private Color selectedColor;
    private JPanel colorPanel;
    private JSlider cyanSlider, magentaSlider, yellowSlider, blackSlider;
    private JLabel hexLabel, hsvLabel, rgbLabel;
    private JTextField cyanField, magentaField, yellowField, blackField;

    public CustomCmykColorPicker(JFrame parent, Color initialColor) {
        super(parent, "Custom CMYK Color Picker", true);
        selectedColor = initialColor;

        colorPanel = new JPanel();
        colorPanel.setPreferredSize(new Dimension(150, 50));
        colorPanel.setBackground(selectedColor);

        hexLabel = new JLabel("HEX: " + CustomColorConverter.rgbToHex(initialColor));
        hsvLabel = new JLabel("HSV: " +
                CustomColorConverter.rgbToHsv(initialColor)[0] + ", " +
                CustomColorConverter.rgbToHsv(initialColor)[1] + ", " +
                CustomColorConverter.rgbToHsv(initialColor)[2]
        );
        rgbLabel = new JLabel("RGB: " +
                selectedColor.getRed() + ", " +
                selectedColor.getGreen() + ", " +
                selectedColor.getBlue()
        );

        cyanSlider = createSlider("Cyan", 0, 100, CustomColorConverter.rgbToCMYK(initialColor)[0]);
        magentaSlider = createSlider("Magenta", 0, 100, CustomColorConverter.rgbToCMYK(initialColor)[1]);
        yellowSlider = createSlider("Yellow", 0, 100, CustomColorConverter.rgbToCMYK(initialColor)[2]);
        blackSlider = createSlider("Black", 0, 100, CustomColorConverter.rgbToCMYK(initialColor)[3]);

        cyanField = createTextField("Cyan", CustomColorConverter.rgbToCMYK(initialColor)[0]);
        magentaField = createTextField("Magenta", CustomColorConverter.rgbToCMYK(initialColor)[1]);
        yellowField = createTextField("Yellow", CustomColorConverter.rgbToCMYK(initialColor)[2]);
        blackField = createTextField("Black", CustomColorConverter.rgbToCMYK(initialColor)[3]);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> dispose());

        setLayout(new FlowLayout());
        JPanel colorAndValuePanel = new JPanel(new GridLayout(2, 1));
        colorAndValuePanel.add(colorPanel);
        colorAndValuePanel.add(hexLabel);
        colorAndValuePanel.add(hsvLabel);
        colorAndValuePanel.add(rgbLabel);

        JPanel slidersPanel = new JPanel(new GridLayout(4, 1));
        slidersPanel.add(cyanSlider);
        slidersPanel.add(magentaSlider);
        slidersPanel.add(yellowSlider);
        slidersPanel.add(blackSlider);

        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2));
        fieldsPanel.add(new JLabel("Cyan:"));
        fieldsPanel.add(cyanField);
        fieldsPanel.add(new JLabel("Magenta:"));
        fieldsPanel.add(magentaField);
        fieldsPanel.add(new JLabel("Yellow:"));
        fieldsPanel.add(yellowField);
        fieldsPanel.add(new JLabel("Black:"));
        fieldsPanel.add(blackField);

        add(colorAndValuePanel);
        add(slidersPanel);
        add(fieldsPanel);
        add(okButton);

        pack();
        setLocationRelativeTo(parent);

        cyanSlider.addChangeListener(e -> updateColor());
        magentaSlider.addChangeListener(e -> updateColor());
        yellowSlider.addChangeListener(e -> updateColor());
        blackSlider.addChangeListener(e -> updateColor());

        cyanField.addActionListener(e -> updateColorFromFields());
        magentaField.addActionListener(e -> updateColorFromFields());
        yellowField.addActionListener(e -> updateColorFromFields());
        blackField.addActionListener(e -> updateColorFromFields());
    }

    private JSlider createSlider(String label, int min, int max, int initialValue) {
        JSlider slider = new JSlider(min, max, initialValue);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
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
        int cyan = cyanSlider.getValue();
        int magenta = magentaSlider.getValue();
        int yellow = yellowSlider.getValue();
        int black = blackSlider.getValue();
        updateColorValues(cyan, magenta, yellow, black);
    }

    private void updateColorFromFields() {
        try {
            int cyan = Integer.parseInt(cyanField.getText());
            int magenta = Integer.parseInt(magentaField.getText());
            int yellow = Integer.parseInt(yellowField.getText());
            int black = Integer.parseInt(blackField.getText());

            cyan = Math.min(Math.max(cyan, 0), 100);
            magenta = Math.min(Math.max(magenta, 0), 100);
            yellow = Math.min(Math.max(yellow, 0), 100);
            black = Math.min(Math.max(black, 0), 100);

            updateColorValues(cyan, magenta, yellow, black);
        } catch (NumberFormatException ex) {

        }
    }

    private void updateColorValues(int cyan, int magenta, int yellow, int black) {
        int[] tmpColor = CustomColorConverter.cmykToRGB(new int[]{cyan, magenta, yellow, black});
        this.selectedColor = new Color(tmpColor[0], tmpColor[1], tmpColor[2]);
        hexLabel.setText("HEX: " + CustomColorConverter.rgbToHex(selectedColor));
        hsvLabel.setText("HSV: " +
                CustomColorConverter.rgbToHsv(selectedColor)[0] + ", " +
                CustomColorConverter.rgbToHsv(selectedColor)[1] + ", " +
                CustomColorConverter.rgbToHsv(selectedColor)[2]
        );
        rgbLabel.setText("RGB: " +
                selectedColor.getRed() + ", " +
                selectedColor.getGreen() + ", " +
                selectedColor.getBlue()
        );
        colorPanel.setBackground(selectedColor);

        cyanSlider.setValue(cyan);
        magentaSlider.setValue(magenta);
        yellowSlider.setValue(yellow);
        blackSlider.setValue(black);

        cyanField.setText(String.valueOf(cyan));
        magentaField.setText(String.valueOf(magenta));
        yellowField.setText(String.valueOf(yellow));
        blackField.setText(String.valueOf(black));
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public static Color showDialog(Component parent, Color initialColor) {
        CustomCmykColorPicker colorPicker = new CustomCmykColorPicker(getFrame(parent), initialColor);
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