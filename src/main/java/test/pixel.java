package test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class pixel {
    public static void main(String[] args) throws IOException {
        int width = 1, height = 1;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        WritableRaster raster = img.getRaster();
        int[] red = new int[]{255, 0, 0, 255}; // RGBA 值
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                raster.setPixel(x, y, red);
            }
        }

        ImageIO.write(img, "png", new File("red.png"));
    }
}
