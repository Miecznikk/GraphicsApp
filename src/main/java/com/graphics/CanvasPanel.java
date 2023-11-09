package com.graphics;

import com.graphics.drawables.*;
import com.graphics.drawables.TextField;
import com.graphics.drawables.utils.ShapeType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CanvasPanel extends JPanel {
    private ShapeType selectedShape = ShapeType.TRIANGLE;
    private List<Drawable> drawables = new ArrayList<>();
    private Drawable selectedDrawable = null;
    private Point lastMousePosition = null;
    private Color currentColor = Color.BLACK;
    private int startX = 0, startY = 0, endX = 0, endY = 0;

    private int liveStartX, liveStartY, liveEndX, liveEndY;

    public CanvasPanel() {
        this.setBackground(Color.WHITE);
        this.setSize(650, 600);
        this.setLocation(0, 0);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                startX = e.getX();
                startY = e.getY();
                endX = e.getX();
                endY = e.getY();
                liveStartX = startX;
                liveStartY = startY;
                liveEndX = endX;
                liveEndY = endY;
                selectDrawable(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                endX = e.getX();
                endY = e.getY();

                if (selectedDrawable == null) {
                    drawables.add(createDrawable());
                } else {
                    moveSelectedDrawable(e.getPoint());
                    selectedDrawable = null;
                }

                liveStartX = liveStartY = liveEndX = liveEndY;

                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedDrawable != null) {
                    moveSelectedDrawable(e.getPoint());
                } else if (selectedShape == ShapeType.PENCIL) {
                    drawables.add(new DrawablePoint(e.getPoint(),currentColor));
                } else {
                    liveEndX = e.getX();
                    liveEndY = e.getY();
                }

                repaint();
            }
        });
    }

    public void setSelectedShape(ShapeType shape) {
        selectedShape = shape;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Drawable drawable : drawables) {
            drawable.draw(g);
        }

        if (liveStartX != liveEndX || liveStartY != liveEndY) {
            Drawable livePreviewDrawable = createDrawable();
            livePreviewDrawable.draw(g);
        }
    }

    private Drawable createDrawable() {
        if (selectedShape == ShapeType.TRIANGLE) {
            int[] xPoints = {liveStartX, (liveStartX + liveEndX) / 2, liveEndX};
            int[] yPoints = {liveEndY, liveStartY, liveEndY};
            return new Triangle(new Polygon(xPoints, yPoints, 3),currentColor);
        } else if (selectedShape == ShapeType.RECTANGLE) {
            int width = Math.abs(liveEndX - liveStartX);
            int height = Math.abs(liveEndY - liveStartY);
            int x = Math.min(liveStartX, liveEndX);
            int y = Math.min(liveStartY, liveEndY);
            return new RectangleShape(new Rectangle(x, y, width, height),currentColor);
        } else if (selectedShape == ShapeType.ELLIPSE) {
            int width = Math.abs(liveEndX - liveStartX);
            int height = Math.abs(liveEndY - liveStartY);
            int x = Math.min(liveStartX, liveEndX);
            int y = Math.min(liveStartY, liveEndY);
            return new EllipseShape(new Ellipse2D.Double(x, y, width, height),currentColor);
        } else if (selectedShape == ShapeType.LINE) {
            return new Line(startX, startY, liveEndX, liveEndY,currentColor);
        } else if (selectedShape == ShapeType.TEXT) {
            String initialText = "Sample Text";
            Font textFont = new Font("Arial", Font.BOLD, 24);
            return new TextField(initialText, startX, startY, textFont);
        } else if (selectedShape == ShapeType.PENCIL) {
            return new DrawablePoint(new Point(startX,startY),this.currentColor);
        }

        return null;
    }

    private void selectDrawable(Point point) {
        for (int i = drawables.size() - 1; i >= 0; i--) {
            Drawable drawable = drawables.get(i);
            if (drawable.contains(point)) {
                selectedDrawable = drawable;
                lastMousePosition = point;
                return;
            }
        }
    }

    private void moveSelectedDrawable(Point currentMousePosition) {
        if (selectedDrawable != null && lastMousePosition != null) {
            int dx = currentMousePosition.x - lastMousePosition.x;
            int dy = currentMousePosition.y - lastMousePosition.y;

            selectedDrawable.move(dx, dy);
            lastMousePosition = currentMousePosition;
        }
    }

    public void clearAllDrawables(){
        this.drawables.clear();
        repaint();
    }

    public void saveImageToFile(String filePath){
        BufferedImage image = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        paint(g2d);
        g2d.dispose();

        try{
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("Image saved to " + filePath);
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color color){
        this.currentColor = color;
    }
}