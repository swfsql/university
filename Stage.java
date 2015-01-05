import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Stage {
  int w, h; // stage (image) width, height
  BufferedImage bimg; // used mostly for collision tests
  Image stage; // loaed stage image in stage folder

  public boolean test_collision(int i, int j) {
    return bimg.getRGB(i, j) != -1; 
  }

  public void load(String filename) throws Exception {
    stage = ImageIO.read(new File(filename));
    bimg = (BufferedImage) stage;
    w = bimg.getWidth();
    h = bimg.getHeight();
  }

  // stage drawing
  public void draw(Camera cam) {
    ((Graphics2D) cam.gv).drawImage(stage, cam.w / 2 - cam.x, cam.h / 2 - cam.y, cam);
  }

}



