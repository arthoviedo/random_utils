import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.filter.PlugInFilter;

public class Plugin_ColorToGray implements PlugInFilter {
    ImagePlus imp;

    public void run(ImageProcessor ip) {
        /**ip.invert();
        imp.updateAndDraw();
        IJ.wait(500);
        ip.invert();
        imp.updateAndDraw();*/
        for(int i=0;i<ip.getWidth();i++){
            for(int j=0;j<ip.getHeight();j++){
                int color[]=ip.getPixel(i, j,null);
                int gray=gray(color[0],color[1],color[2]);
                ip.setColor(gray+256*gray+256*256*gray);
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
