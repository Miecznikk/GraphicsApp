package com.graphics.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageLoader {
    public static void main(String[] args){
        prepareCanvasAndDraw("test_images/ppm-test-07-p3-big.ppm",1);
    }

    public static void prepareCanvasAndDraw(String filePath, int pixelSize) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String formatType = br.readLine().split("#")[0].trim();
            if (List.of("P1", "P2", "P3").contains(formatType)) {
                String[] dimensions = br.readLine().split("#")[0].split(" ");
                int width = Integer.parseInt(dimensions[0]);
                int height = Integer.parseInt(dimensions[1]);
                int colorValue = 1;
                if(!formatType.equals("P1")){
                    colorValue = Integer.parseInt(br.readLine().split("#")[0].trim());
                }
                List<Integer> pixels = new ArrayList<>();
                String line;

                while ((line = br.readLine()) != null) {
                    line = line.split("#")[0].trim();

                    if (!line.isEmpty()) {
                        String[] values = line.split("\\s+");

                        for (String val : values) {
                            pixels.add(255 / colorValue * Integer.parseInt(val));
                        }
                    }
                }
                JFrame frame = new JFrame("Image Loader");
                JPanel panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        switch (formatType){
                            case "P1":
                                drawPbmImage(g,pixels,pixelSize,width,height);
                                break;
                            case "P2":
                                drawPgmImage(g,pixels,pixelSize,width,height);
                                break;
                            case "P3":
                                drawPpmImage(g, pixels, pixelSize, width, height);
                                break;
                        }
                    }
                };
                panel.setPreferredSize(new Dimension(width*pixelSize,height*pixelSize));
                frame.setContentPane(panel);
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
                panel.repaint();
            }
            else if (List.of("P4","P5","P6").contains(formatType)){
                try (FileInputStream fileInputStream = new FileInputStream(filePath);
                     DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
                    String format = readString(dataInputStream);
                    int width = readInt(dataInputStream);
                    int height = readInt(dataInputStream);
                    int colorValue = 255;
                    if(!formatType.equals("P4")){
                        colorValue = readInt(dataInputStream);
                    }
                    List<Integer> pixels = new ArrayList<>();

                    while(dataInputStream.available() > 0) {
                        pixels.add(255 / colorValue * dataInputStream.readUnsignedByte());
                    }
                    if(format.equals("P4")){
                        pixels.remove(0);
                    }
                    System.out.println(pixels);
                    JFrame frame = new JFrame("Image Loader");
                    JPanel panel = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            switch (format){
                                case "P4":
                                    drawPbmImage(g,pixels,pixelSize,width,height);
                                    break;
                                case "P5":
                                    break;
                                case "P6":
                                    drawPpmImage(g,pixels,pixelSize,width,height);
                                    break;
                            }
                        }
                    };
                    panel.setPreferredSize(new Dimension(width*pixelSize,height*pixelSize));
                    frame.setContentPane(panel);
                    frame.pack();
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                    panel.repaint();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                throw new Exception("Wrong file format");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void drawPpmImage(Graphics g, List<Integer> pixels, int pixelSize, int width, int height) {
        List<Color> colors = new ArrayList<>();
        for(int i=0;i<pixels.size();i+=3){
            int red = pixels.get(i);
            int green = pixels.get(i+1);
            int blue = pixels.get(i+2);

            colors.add(new Color(red,green,blue));
        }
        int which = 0;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                g.setColor(colors.get(which));
                g.fillRect(j*pixelSize,i*pixelSize,pixelSize,pixelSize);
                which++;
            }
        }
    }
    private static void drawPbmImage(Graphics g, List<Integer> pixels, int pixelSize, int width, int height) {
        int which = 0;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                if (pixels.get(which) == 255){
                    g.setColor(Color.BLACK);
                }
                else if (pixels.get(which) == 0){
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j*pixelSize,i*pixelSize,pixelSize,pixelSize);
                which++;
            }
        }
    }

    private static void drawPgmImage(Graphics g, List<Integer> pixels, int pixelSize, int width, int height){
        int which= 0;
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                int col = pixels.get(which);
                g.setColor(new Color(col,col,col));
                g.fillRect(j*pixelSize,i*pixelSize,pixelSize,pixelSize);
                which++;
            }
        }
    }
    private static String readString(DataInputStream dataInputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        char c;
        while ((c = (char) dataInputStream.readByte()) != '\n') {
            builder.append(c);
        }
        return builder.toString().trim();
    }

    private static int readInt(DataInputStream dataInputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        char c;
        while ((c = (char) dataInputStream.readByte()) != ' ' && c != '\n' && c!= '\r') {
            builder.append(c);
        }
        return Integer.parseInt(builder.toString());
    }

    private static void writePbmToFile(List<Integer> pixels, int width, int height){
        String filePath = "images/testWrite1.pbm";
        try (
             FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            bufferedWriter.write("P4");
            bufferedWriter.newLine();
            bufferedWriter.write(width + " " + height);
            bufferedWriter.newLine();

        } catch (IOException e){
            e.printStackTrace();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {

            for (Integer pixel : pixels) {
                dataOutputStream.write(pixel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writePgmToFile(List<Integer> pixels, int width, int height){
        String filePath = "images/testWrite2.pgm";
        try (
                FileWriter fileWriter = new FileWriter(filePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){

            bufferedWriter.write("P5");
            bufferedWriter.newLine();
            bufferedWriter.write(width + " " + height);
            bufferedWriter.newLine();

        } catch (IOException e){
            e.printStackTrace();
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath, true);
             DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream)) {

            for (Integer pixel : pixels) {
                dataOutputStream.writeInt(pixel);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}