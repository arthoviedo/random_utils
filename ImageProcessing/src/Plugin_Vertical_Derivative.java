
import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.filter.PlugInFilter;

public class Plugin_Vertical_Derivative implements PlugInFilter {
    ImagePlus imp;

    
    public void run(ImageProcessor ip) {
        /**ip.invert();
        imp.updateAndDraw();
        IJ.wait(500);
        ip.invert();
        imp.updateAndDraw();*/
        short dx[][]=new short[ip.getWidth()][ip.getHeight()];
        short dy[][]=new short[ip.getWidth()][ip.getHeight()];
        short g[][] =new short[ip.getWidth()][ip.getHeight()];
        for(int i=0;i<ip.getWidth()-1;i++){
            for(int j=0;j<ip.getHeight()-1;j++){
//                int color[]=ip.getPixel(i, j,null);
//                int val=color[0];
//                ip.setColor(gray+256*gray+256*256*gray);
                dx[i][j]=(short)(ip.getPixel(i+1,j,null)[0]-ip.getPixel(i,j,null)[0]);
                dy[i][j]=(short)(ip.getPixel(i,j+1,null)[0]-ip.getPixel(i,j,null)[0]);
                int value=dy[i][j];
                ip.setColor(value+256*value+256*256*value);
                
                
                ip.drawPixel(i, j);
            }
        }
        
        
    }
    
    int gray(int r,int g, int b){
            return (int)( 0.2989*r + 0.5870*g+ 0.1140*b);
    }

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL;
    }
}
