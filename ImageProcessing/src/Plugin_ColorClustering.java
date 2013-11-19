import ij.*;
import ij.measure.ResultsTable;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.TreeSet;

import ij.plugin.*;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.PlugInFilter;

public class Plugin_ColorClustering implements PlugInFilter {
    ImagePlus imp;

    public int toColorValue(int red, int green, int blue){
        return blue+green*256+red*256*256;
    }
    
    public void run(ImageProcessor ip) {
        /**ip.invert();
        imp.updateAndDraw();
        IJ.wait(500);
        ip.invert();
        imp.updateAndDraw();*/
        long initTime=System.currentTimeMillis();
        
        final GenericDialog gd = new GenericDialog("Create image");  
        gd.addNumericField("Threshold", 50, 0);
        int THRESHOLD=(int)gd.getNextNumber();
        gd.showDialog();  
        if (gd.wasCanceled()) return ;  
  
        
        
        boolean [] colors=new boolean[256*256*256];
        LinkedList<myColor> colorList=new LinkedList<myColor>();
        int count=0;
        for(int i=0;i<ip.getWidth();i++){
            for(int j=0;j<ip.getHeight();j++){
                int color[]=ip.getPixel(i, j,null);
                int red     =color[0];
                int green   =color[1];
                int blue    =color[2];
                
            
            
            
        
                int colorValue=toColorValue(red,green,blue);
                
                if(!colors[colorValue])
                {
                    count++;
                    colors[colorValue]=true;
                }
                myColor closest=null;
                int minDistance=Integer.MAX_VALUE;
                for(myColor c:colorList){
                    if(Math.sqrt(   (red-c.red)*(red-c.red) + 
                                    (green-c.green)*(green-c.green) + 
                                    (blue-c.blue)*(blue-c.blue) )<minDistance){
                        minDistance=(int)Math.sqrt( (red-c.red)*(red-c.red) + 
                                                     (green-c.green)*(green-c.green) + 
                                                     (blue-c.blue)*(blue-c.blue)) ;
                        closest=c;
                    }
                }
                if(closest==null || Math.sqrt(   (red-closest.red)*(red-closest.red) + 
                                    (green-closest.green)*(green-closest.green) + 
                                    (blue-closest.blue)*(blue-closest.blue))> THRESHOLD ) {
                    myColor newColor=new myColor();
                    newColor.red=red;newColor.green=green;newColor.blue=blue;
                    colorList.add(newColor);
                }
                else{
                    ip.setColor(toColorValue(closest.red, closest.green, closest.blue));
                    ip.drawPixel(i, j);
                }
                
            }
        }
        long finishTime=System.currentTimeMillis();
        
        //System.out.println(count);
        ResultsTable rt = Analyzer.getResultsTable();
        if (rt == null) {
            rt = new ResultsTable();
            Analyzer.setResultsTable(rt);
        }
            rt.incrementCounter();
            rt.addValue("PixelCount", count);
            rt.addValue("Total Possible Pixel Count", 256*256*256);
            rt.addValue("Total time", (finishTime-initTime)/1000);
        rt.show("Results");
        
    }
    
    int gray(int r,int g, int b){
            return (int)( 0.2989*r + 0.5870*g+ 0.1140*b);
    }

    public int setup(String arg, ImagePlus imp) {
        this.imp = imp;
        return DOES_ALL;
    }
    
    class myColor implements Comparable<myColor>{
        int red,green,blue;
        int colorVal;
        @Override
        public int compareTo(myColor o) {
            return (red+green<<8+blue<<16) - (o.red+o.green<<8+o.blue<<16);
        }
        
    }
}
