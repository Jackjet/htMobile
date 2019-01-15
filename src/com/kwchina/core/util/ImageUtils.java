package com.kwchina.core.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;


/**
* 把图片印刷到图片上
* @author yidwo
*
*/

public class ImageUtils {



/**
* 
* @param pressImg --用作水印的图片
* @param targetImg --把水印图片附加到的目标图片
* @param x 距离图片右下角的左方向偏移量
* @param y 距离图片右下角的上方向偏移量
*/
public final static void pressImage(String pressImg, String targetImg, int x, int y) {
   try {
    File _file = new File(targetImg);
    Image src = ImageIO.read(_file);
   
    int wideth = src.getWidth(null);
    int height = src.getHeight(null);
   
    BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
   
    Graphics g = image.createGraphics();
    g.drawImage(src, 0, 0, wideth, height, null);

    // 水印文件
    File _filebiao = new File(pressImg);
    Image src_biao = ImageIO.read(_filebiao);
   
    int wideth_biao = src_biao.getWidth(null);
    int height_biao = src_biao.getHeight(null);
   
    //x、y分别为距离左、上距离
    g.drawImage(src_biao, x,  y, wideth_biao, height_biao, null);
    //此为距离右、下距离
    //g.drawImage(src_biao, wideth - wideth_biao -x, height -height_biao - y, wideth_biao, height_biao, null);
    g.dispose();
   
    FileOutputStream out = new FileOutputStream(targetImg);
    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    encoder.encode(image);
   
    out.close();
   } catch (Exception e) {
    e.printStackTrace();
   }
}


public static void main(String[] args) {
   pressImage("d:/sipgllogo.png", "d:/kwIP.jpg", 5, 5);
}
}

