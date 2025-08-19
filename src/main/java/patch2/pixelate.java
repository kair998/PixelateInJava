package patch2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class pixelate {

    int patchSize;

    private static String workingDir = System.getProperty("user.dir");

    private static void pixelateOriginalImage(String filename, int pixelatePatch, Integer Alpha) throws IOException{
        //读取
        String fileRoad = workingDir +  "\\" + filename;

//        System.out.println(fileRoad);

        BufferedImage img = ImageIO.read(new File(fileRoad));

        //创建输出流对象，我认为不应该叫做“写入”而应该叫做“写出”
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        //逐块取色
        for(int y = 0; y < img.getHeight(); y += pixelatePatch){
            for(int x = 0; x < img.getWidth(); x+= pixelatePatch){

                long sumR = 0, sumG = 0, sumB = 0, sumA = 0;
                int count = 0;

                //对块上每个元素进行取色并累计
                for(int dy = 0; dy < pixelatePatch; dy++){
                    for(int dx = 0;dx < pixelatePatch; dx++){
                        if(x + dx < img.getWidth() && y + dy < img.getHeight()){
                            int rgb = img.getRGB(x + dx, y + dy);

                            int a = (rgb >> 24) & 0xff;
                            int r = (rgb >> 16) & 0xff;
                            int g = (rgb >> 8) & 0xff;
                            int b = rgb & 0xff;

                            sumA += a;
                            sumR += r;
                            sumB += b;
                            sumG += g;
                            count ++;
                        }
                    }
                }

                //用户自由设置透明度/采用平均透明度
                int avgA = (Alpha != null) ? Alpha : (int)(sumA / count);

                //计算平均色
                int avgR = (int)(sumR / count);
                int avgG = (int)(sumG / count);
                int avgB = (int)(sumB / count);

                int avgRGB = (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB;

                //将该块平均色写入到输出流
                for(int dy = 0; dy < pixelatePatch; dy++){
                    for(int dx = 0; dx < pixelatePatch; dx++){
                        if(x + dx < img.getWidth() && y + dy < img.getHeight()){
                            output.setRGB(x + dx, y + dy, avgRGB);
                        }
                    }
                }
            }
        }

        ImageIO.write(output, "png", new File("output.png"));

    }

    private static void inNewImage(String filename, int pixelatePatch, Integer Alpha) throws IOException{
        String fileRoad = workingDir +  "\\" + filename;
        BufferedImage img = ImageIO.read(new File(fileRoad));

        BufferedImage output = new BufferedImage(img.getWidth() / pixelatePatch,
                                                img.getHeight() / pixelatePatch ,
                                                        BufferedImage.TYPE_INT_ARGB);

        //逐块取色
        for(int y = 0; y < img.getHeight(); y += pixelatePatch){
            for(int x = 0; x < img.getWidth(); x+= pixelatePatch){

                long sumR = 0, sumG = 0, sumB = 0, sumA = 0;
                int count = 0;

                //对块上每个元素进行取色并累计
                for(int dy = 0; dy < pixelatePatch; dy++){
                    for(int dx = 0;dx < pixelatePatch; dx++){
                        if(x + dx < img.getWidth() && y + dy < img.getHeight()){
                            int rgb = img.getRGB(x + dx, y + dy);

                            int a = (rgb >> 24) & 0xff;
                            int r = (rgb >> 16) & 0xff;
                            int g = (rgb >> 8) & 0xff;
                            int b = rgb & 0xff;

                            sumA += a;
                            sumR += r;
                            sumB += b;
                            sumG += g;
                            count ++;
                        }
                    }
                }

                //用户自由设置透明度/采用平均透明度
                int avgA = (Alpha != null) ? Alpha : (int)(sumA / count);

                //计算平均色
                int avgR = (int)(sumR / count);
                int avgG = (int)(sumG / count);
                int avgB = (int)(sumB / count);

                int avgRGB = (avgA << 24) | (avgR << 16) | (avgG << 8) | avgB;

                if(x / pixelatePatch >= output.getWidth() || y / pixelatePatch >= output.getHeight()){
                    continue;
                }
                output.setRGB(x / pixelatePatch, y / pixelatePatch, avgRGB);
            }
        }

        ImageIO.write(output, "png", new File("newGen.png"));
    }



    public static void main(String[] args) throws IOException {
        /*
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        String[] inputs = filename.split(" ");
        filename = inputs[0];
        int askedPacth = Integer.parseInt(inputs[1]);
        int alpha = Integer.parseInt(inputs[2]);
        pixelateOriginalImage(filename, askedPacth, alpha);
         */

        //pixelateOriginalImage("tc001.png", 16, 255);

        //inNewImage("nz.png", 32, 198);

    }


}
