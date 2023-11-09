package com.graphics;

import javax.swing.*;
import java.awt.*;

public class Window {
    public Window() {
        JFrame frame = new JFrame("GIGA PAINT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(850, 600);
        frame.setResizable(false);

        CanvasPanel canvasPanel = new CanvasPanel();
        ToolsPanel toolsPanel = new ToolsPanel(canvasPanel);

        frame.setLayout(new BorderLayout());
        frame.add(canvasPanel, BorderLayout.CENTER);
        frame.add(toolsPanel, BorderLayout.EAST);

        frame.setVisible(true);
    }
}
