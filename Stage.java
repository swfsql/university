import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Stage {
  int w, h; // stage (image) width, height
  BufferedImage bimg; // used mostly for collision tests
  Image stage, // loaded stage image in stage folder
        ground,
        roof;

  String [][] stages;

  public boolean test_collision(int i, int j) {
    return bimg.getRGB(i, j) != -1; 
  }

  public void temp() {
    stages = new String [1][4];
    stages[0][0] = "test";
    stages[0][1] = "png";
    stages[0][2] = "png";
    stages[0][3] = "png";

  }

  public void load(int i) throws Exception {
    ground = roof = null;
    stage = ImageIO.read(new File("stages/" + stages[i][0] + "-wall." + stages[i][1]));
    bimg = (BufferedImage) stage;
    w = bimg.getWidth();
    h = bimg.getHeight();

    if (stages[i][2] != null) {
      ground = ImageIO.read(new File("stages/" + stages[i][0] + "-ground." + stages[i][2]));
    }
    if (stages[i][3] != null) {
      roof = ImageIO.read(new File("stages/" + stages[i][0] + "-roof." + stages[i][3]));

    }

  }

  // stage drawing
  public void drawBelow(Camera cam) {
    Image img = ground != null ? ground : roof != null ? roof : stage;
    ((Graphics2D) cam.gv).drawImage(img, cam.w / 2 - cam.x, cam.h / 2 - cam.y, cam);
  }

  public void drawAbove(Camera cam) {
    if (roof == null || ground == null) return;
    ((Graphics2D) cam.gv).drawImage(roof, cam.w / 2 - cam.x, cam.h / 2 - cam.y, cam);
  }

}



