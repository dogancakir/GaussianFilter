/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaussianfilter;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.paint.Color;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author dogan.cakir
 */
public class GaussianFilter {
    static double w=0.5;
    static double[][] convolve()
    {
        double convolve[][]=new double[15][15];
        for(int x=7;x>-8;x--)
        {
            for(int y=7;y>-8;y--)
            {
                
                convolve[x+7][y+7]=(-2*x*Math.exp(-(x*x+y*y)/(2*w*w)))/(2*Math.PI*w*w*w*w);
            }
        }
        return convolve;        

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String path="C:\\Users\\dogan.cakir\\Documents\\NetBeansProjects\\ImageRotation\\vayne.jpg";
        BufferedImage img =ImageIO.read(new File(path));
        for(w=0.10;w<1.00;w+=0.10)
        {
        BufferedImage nimg =ImageIO.read(new File(path));
        double conv[][]=convolve();
        for(int i=7;i<img.getWidth()-7;i++)
        {
            for(int j=7;j<img.getHeight()-7;j++)
            {
                
                double total=0;
                for(int x=-7;x<8;x++)
                {
                    for(int y=-7;y<8;y++)
                    {
                        
                        int rgb = img.getRGB(i+x,j+y);
                        int red = ((rgb >> 16) & 0x000000FF);
                        red *=conv[x+7][y+7];
                        int green = (rgb >>8 ) & 0x000000FF;
                        green*=conv[x+7][y+7];
                        int blue = (rgb) & 0x000000FF;
                        blue*=conv[x+7][y+7];
                        rgb = (red << 16 | green << 8 | blue);
                        
                        total+=rgb;
                        
                    }
                }
                nimg.setRGB(i, j, (int)total);
            }
        }
        /*JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.getContentPane().add(new JLabel(new ImageIcon(nimg)));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
*/
        File outputfile = new File(w+".jpg");
        ImageIO.write(nimg, "jpg", outputfile);
        }
    }
    
}
