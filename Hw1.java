/*


*/

import framebuffer.FrameBuffer;

import javax.swing.text.View;
import java.awt.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

/**
   Outline of CS 45500 Assignment 1.
*/
public class Hw1
{
   public static int distance(int x1, int y1, int x2, int y2) {
      int distance = 0;
      distance = (int) Math.sqrt(Math.pow(x2-x1, 2) + Math.pow(y2-y1, 2));
      return distance;
   }
   public static void drawCheckBoard(FrameBuffer fb, int y, int width, Color checkerColor, int leadingColor) {
      if (leadingColor == 0) {
         for (int i = 50; i < width - 50; i++) {
            if (i == 150) {
               i += 100;
            } else if (i == 250 || i == 350 || i == 450 || i == 550 || i == 650 || i == 750 || i == 850 || i == 950) {
               i += 100;
            }
            for (int j = y; j < y + 100; j++) {
               fb.setPixelFB(i, j, checkerColor);
            }
         }
      }
   else {
         for (int i = 150; i < width - 50; i++) {
            if (i == 250) {
               i += 100;
            } else if (i == 350 || i == 450 || i == 550 || i == 650 || i == 750 || i == 850 || i == 950) {
               i += 100;
            }
            for (int j = y; j < y + 100; j++) {
               fb.setPixelFB(i, j, checkerColor);
            }
         }
      }
}
public static void drawDiagonals(FrameBuffer.Viewport vp,Color color, int start) {
   for (int j = 0; j < 130; j++) {
      for (int i = start; i < start+30; i++) {
         vp.setPixelVP(i+j, j, color);
      }
   }
}
public static void drawRing(FrameBuffer.Viewport vp, Color color, int distance) {
   for(int i = 0; i < 300; i++) {
      for(int j = 0; j < 300; j++) {
         if (distance(150, 150, i, j) >= distance && distance(150, 150, i, j) < distance+30) {
            vp.setPixelVP(i, j, color);
         }
      }
   }
}
public static boolean isWhiteEnough(Color c) {
      float[] currColor = new float[3];
      c.getRGBColorComponents(currColor);
      return (currColor[0] > 0.985 && currColor[1] > 0.985 && currColor[2] > 0.985 );
}
   public static void main(String[] args) {
      // Use a properties file to find out
      // which PPM files to use as assets.
      final Properties properties = new Properties();
      try {
         properties.load(
                 new FileInputStream(
                         new File("assets.properties")));
      } catch (IOException e) {
         e.printStackTrace(System.err);
         System.exit(-1);
      }
      final String file_1 = properties.getProperty("file1"); // 1st ppm file
      final String file_2 = properties.getProperty("file2"); // 2nd ppm file

      // This framebuffer holds the image that will be embedded
      // into two viewports of the larger framebuffer.
      final FrameBuffer fbEmbedded = new FrameBuffer(file_1);

      // Your code goes here.

      // Color for the background
      final int width = 1100;
      final int height = 700;
      Color bgColor = new Color(192, 56, 14);
      Color checkerColor = new Color(255, 189, 96);
      //  1. Create a 1100-by-700 framebuffer with the appropriate background color.
      final FrameBuffer fb = new FrameBuffer(width, height, bgColor);
      //  2. Fill the framebuffer with the checkerboard pattern.
      drawCheckBoard(fb, 50, width, checkerColor, 0);
      drawCheckBoard(fb, 150, width, checkerColor, 1);
      drawCheckBoard(fb, 250, width, checkerColor, 0);
      drawCheckBoard(fb, 350, width, checkerColor, 1);
      drawCheckBoard(fb, 450, width, checkerColor, 0);
      drawCheckBoard(fb, 550, width, checkerColor, 1);
      //  3. Put a one pixel wide border around the checkerboard.
      /*
      ********************************************************************
      ********************************************************************
      ********************************************************************
      // REMINDER : FIX THIS HORRENDOUS MESS OF FOR LOOPS INTO FUNCTIONS
      ********************************************************************
      ********************************************************************
      ********************************************************************
      */
      for (int i = 50; i < width - 50; i++) {
         fb.setPixelFB(i, 49, checkerColor);
      }
      for (int i = 50; i < height - 50; i++) {
         fb.setPixelFB(49, i, checkerColor);
      }
      for (int i = 50; i < height - 50; i++) {
         fb.setPixelFB(width - 50, i, checkerColor);
      }
      for (int i = 50; i < width - 50; i++) {
         fb.setPixelFB(i, height - 50, checkerColor);
      }
      //  4. Put four one pixel wide diagonals at the four coners of the checkerboard.
      for (int i = 0; i < 50; i++) {
         fb.setPixelFB(i, i, checkerColor);
      }
      for (int i = 0; i < 50; i++) {
         fb.setPixelFB(width - i - 1, height - i - 1, checkerColor);
      }
      for (int i = 0; i < 50; i++) {
         fb.setPixelFB(i, height - i - 1, checkerColor);
      }
      for (int i = 0; i < 50; i++) {
         fb.setPixelFB(width - i, i, checkerColor);
      }

      //  5. Create a viewport and put in it the striped pattern.
      // Pink green blue order
      Color pinkish = new Color(241, 95, 116);
      Color neonGreen = new Color(152, 203, 74);
      Color aquaBlue = new Color(84, 129, 230);
      final FrameBuffer.Viewport vp1 = fb.new Viewport(650, 475, 270, 130);
         drawDiagonals(vp1, pinkish, 0);
         drawDiagonals(vp1, neonGreen, 30);
         drawDiagonals(vp1, aquaBlue, 60);
         drawDiagonals(vp1, pinkish, 90);
         drawDiagonals(vp1, neonGreen, 120);
         drawDiagonals(vp1, aquaBlue, 150);
         drawDiagonals(vp1, pinkish, 180);
         drawDiagonals(vp1, neonGreen, 210);
         drawDiagonals(vp1, aquaBlue, 240);

      //  6. Create a viewport and put in it the striped disk pattern.
      // CENTER OF CIRCLE IS 350 550
      // First pixel of inner green is 350 490
      // Each disk 30 pixels wide so blue starts at 350 460
      // Each disk 30 pixels wide so pink starts at 350 430
      // Top left corner therefore is 230 430
      final FrameBuffer.Viewport vp2 = fb.new Viewport(200, 400, 300, 300);
      drawRing(vp2, neonGreen, 60);
      drawRing(vp2, pinkish, 90);
      drawRing(vp2, aquaBlue, 120);

      //  7. Create a viewport and put in it a flipped copy of the 1st ppm file.
      int fbEWidth = fbEmbedded.getWidthFB();
      int fbEHeight = fbEmbedded.getHeightFB();
      final FrameBuffer.Viewport vp3 = fb.new Viewport(125, 175, fbEmbedded.getWidthFB(), fbEmbedded.getHeightFB());
      for (int y = 0; y < fbEHeight; ++y)
      {
         for(int x = 0; x < fbEWidth; ++x)
         {
            final Color c = fbEmbedded.getPixelFB(x, y);
            if(isWhiteEnough(c) == false) {
               vp3.setPixelVP(x, fbEWidth - 1 - y, c);
            }
         }
      }

      //  8. Create another viewport put in it another flipped copy of the 1st ppm file.
      final FrameBuffer.Viewport vp4 = fb.new Viewport(385, 175, fbEmbedded.getWidthFB(), fbEmbedded.getHeightFB());
      for(int i = 0; i < fbEWidth; i++) {
         for(int j = 0; j < fbEHeight; j++) {
               Color c = fbEmbedded.getPixelFB(fbEWidth-1-i, j);
               if (isWhiteEnough(c) == false) {
                  vp4.setPixelVP(i, j, c);
               }
         }
      }
      //  9. Create a viewport that covers the 6 checkerboard squares that need to be copied.
      final FrameBuffer.Viewport vb5 = fb.new Viewport(800, 100, 200, 300);
      // 550 250
      for(int i = 0; i < 200; i++) {
         for(int j = 0; j < 300; j++) {
            Color c = fb.getPixelFB(550+i, 250+j);
            vb5.setPixelVP(i, j, c);
         }
      }
      // 10. Create another viewport to hold a "framed" copy of the previous viewport.
      //     Give this viewport a grayish background color.
      final FrameBuffer.Viewport vb6 = fb.new Viewport(775, 75, 250, 350, Color.GRAY);
      for(int i = 0; i < 250; i++) {
         for(int j = 0; j < 350; j++) {
            if (i < 25 || i > 224 || j < 25 || j > 324) {
               vb6.setPixelVP(i, j, Color.GRAY);
            }
         }
      }
      for(int i = 0; i < 200; i++) {
         for(int j = 0; j < 300; j++) {
               Color c = vb5.getPixelVP(i, j);
               vb6.setPixelVP(25+i, 25+j, c);
         }
      }
      // 11. Create another viewport within the last, gray viewport, and initialize
      //     it to hold a copy of the viewport from step 9.
      // 12. Load Dumbledore (the 2nd ppm file) into another FrameBuffer.
      FrameBuffer dumbledoreFB = new FrameBuffer(file_2);
      // 13. Create a viewport to hold Dumbledore's ghost.
      final FrameBuffer.Viewport dumbledoreVP = fb.new Viewport(450, 150, dumbledoreFB.getWidthFB(), dumbledoreFB.getHeightFB());
      // 14. Blend Dumbledore from its framebuffer into the viewport.
      for(int i = 0; i < dumbledoreFB.getWidthFB(); i++) {
         for(int j = 0; j < dumbledoreFB.getHeightFB(); j++) {
            Color c = dumbledoreFB.getPixelFB(i, j);
            Color c2 = dumbledoreVP.getPixelVP(i, j);
            Color new_red = new Color(0, 0, 0);
            Color new_green = new Color(0, 0, 0);
            Color new_blue = new Color(0, 0, 0);
            if (isWhiteEnough(c) == false) {
               dumbledoreVP.setPixelVP(i, j, c);
            }
         }
      }


       // Save the resulting image in a file.
      final String savedFileName = "Hw1.ppm";
      fb.dumpFB2File( savedFileName );
      System.err.println("Saved " + savedFileName);
   }
}

