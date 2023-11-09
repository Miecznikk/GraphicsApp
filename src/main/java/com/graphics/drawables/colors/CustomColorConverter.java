package com.graphics.drawables.colors;

import java.awt.*;

public class CustomColorConverter {

    public static int[] rgbToHsv(Color rgb){
        float r = rgb.getRed() / 255.0f;
        float g = rgb.getGreen() / 255.0f;
        float b = rgb.getBlue() /255.0f;

        float max = Math.max(Math.max(r,g),b);
        float min = Math.min(Math.min(r,g),b);

        float hue=0;

        if(max == min){
            hue=0;
        } else if (max == r){
            hue = ((g - b) / (max- min) + 6) % 6;
        } else if (max == g) {
            hue = (b - r) / (max - min) + 2;
        } else if (max == b) {
            hue = (r - g) / (max - min) + 4;
        }

        hue*=60;

        float saturation = (max == 0) ? 0 : (1 - (min / max));

        return new int[] {Math.round(hue), Math.round(saturation * 100), Math.round(max * 100)};
    }

    public static int[] hsvToRGB(int[] hsv) {
        int hue = (hsv[0] % 360 + 360) % 360;

        float normalizedSaturation = hsv[1] / 100.0f;
        float normalizedValue = hsv[2] / 100.0f;

        float hueSegment = hue / 60.0f;
        int hueSegmentIndex = (int) Math.floor(hueSegment);

        float fractionalHue = hueSegment - hueSegmentIndex;

        float p = normalizedValue * (1 - normalizedSaturation);
        float q = normalizedValue * (1 - normalizedSaturation * fractionalHue);
        float t = normalizedValue * (1 - normalizedSaturation * (1 - fractionalHue));

        float red, green, blue;

        switch (hueSegmentIndex) {
            case 0 -> { red = normalizedValue; green = t; blue = p; }
            case 1 -> { red = q; green = normalizedValue; blue = p; }
            case 2 -> { red = p; green = normalizedValue; blue = t; }
            case 3 -> { red = p; green = q; blue = normalizedValue; }
            case 4 -> { red = t; green = p; blue = normalizedValue; }
            case 5 -> { red = normalizedValue; green = p; blue = q; }
            default -> throw new IllegalArgumentException("Invalid hue value");
        }

        int redInt = (int) (red * 255);
        int greenInt = (int) (green * 255);
        int blueInt = (int) (blue * 255);

        return new int[]{redInt, greenInt, blueInt};
    }

    public static String rgbToHex(Color rgb){
        return String.format("%s%s%s",
                String.format("%02X", rgb.getRed()),
                String.format("%02X", rgb.getGreen()),
                String.format("%02X", rgb.getBlue()));
    }

    public static int[] hexToRgb(String hexColor){
        int hexValue = Integer.parseInt(hexColor, 16);

        int red = (hexValue >> 16) & 0xFF;
        int green = (hexValue >> 8) & 0xFF;
        int blue = hexValue & 0xFF;

        return new int[]{red, green, blue};
    }

    public static int[] rgbToCMYK(Color rgb) {
        float r = rgb.getRed() / 255.0f;
        float g = rgb.getGreen() / 255.0f;
        float b = rgb.getBlue() / 255.0f;

        float max = Math.max(Math.max(r, g), b);

        float k = 1 - max;

        float c = (1 - r - k) / (1 - k);
        float m = (1 - g - k) / (1 - k);
        float y = (1 - b - k) / (1 - k);

        int cmykC = (int) (c * 100);
        int cmykM = (int) (m * 100);
        int cmykY = (int) (y * 100);
        int cmykK = (int) (k * 100);

        return new int[]{cmykC, cmykM, cmykY, cmykK};
    }

    public static int[] cmykToRGB(int[] cmyk) {

        float cc = cmyk[0] / 100.0f;
        float cm = cmyk[1] / 100.0f;
        float cy = cmyk[2] / 100.0f;
        float ck = cmyk[3] / 100.0f;

        int red = (int) ((1 - cc) * (1 - ck) * 255);
        int green = (int) ((1 - cm) * (1 - ck) * 255);
        int blue = (int) ((1 - cy) * (1 - ck) * 255);

        return new int[]{red, green, blue};
    }
}
