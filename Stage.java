import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Comparator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;

public class Stage {
  int w, h; // stage (image) width, height
  BufferedImage bimg; // used mostly for collision tests
  Image stage, // loaded stage image in stage folder
        ground,
        roof;

  public String [][] stages;

  public boolean test_collision(int i, int j) {
    return bimg.getRGB(i, j) != -1; 
  }

  public void searchStages() {
    String[] files = new File("./stages").list();
    Arrays.sort(files, new AlphabeticComparator()); 
    java.util.List<String[]> list = new ArrayList<String[]>();

    for (int i = 0; i < files.length; i++) {
      if (!files[i].matches("^.+\\.txt$")) continue;
      String name = files[i].replaceFirst("^(.+)\\.txt$", "$1");
      if (i < 1) continue;
      if (!files[i - 1].matches("^" + name + "-wall\\.png$")) continue;
      String[] st = new String[4];
      st[0] = name;
      st[1] = "png";
      st[2] = i > 1 && files[i - 2].matches("^" + name + "-roof\\.png$")? "png" : null;
      st[3] = (st[2] == null ? (i > 1 && files[i - 2].matches("^" + name + "-ground\\.png$")? "png" : null) : (i > 2 && files[i - 3].matches("^" + name + "-ground\\.png$")? "png" : null));
      list.add(st);
      System.out.println(files[i]);
    }

    stages = new String[list.size()][4];
    for (int i = 0; i < list.size(); i++) {
      stages[i] = list.get(i);
    }
  }

  public void temp() {
    stages = new String [1][4];
    stages[0][0] = "test";
    stages[0][1] = "png";
    stages[0][2] = "png";
    stages[0][3] = "png";

  }

  public void load(int i, Bar bar) throws Exception {
    ground = roof = null;

    java.util.List<String> lines = Files.readAllLines(Paths.get("stages/" + stages[i][0] + ".txt"), StandardCharsets.UTF_8);
    bar.x = Integer.parseInt(lines.get(1));
    bar.y = Integer.parseInt(lines.get(2));
    bar.vx = Integer.parseInt(lines.get(4));
    bar.vy = Integer.parseInt(lines.get(5));
    bar.rot = Integer.parseInt(lines.get(7));
    bar.spin = Integer.parseInt(lines.get(8));
    bar.len = Integer.parseInt(lines.get(10));
    bar.thick = Integer.parseInt(lines.get(11));
    bar.collision_points = Integer.parseInt(lines.get(13));
    bar.collided_max = Integer.parseInt(lines.get(14));

    stage = ImageIO.read(new File("stages/" + stages[i][0] + "-wall." + stages[i][1]));
    bimg = (BufferedImage) stage;
    w = bimg.getWidth();
    h = bimg.getHeight();

    if (stages[i][2] != null) {
      roof = ImageIO.read(new File("stages/" + stages[i][0] + "-roof." + stages[i][2]));
    }
    if (stages[i][3] != null) {
      ground = ImageIO.read(new File("stages/" + stages[i][0] + "-ground." + stages[i][3]));

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


class AlphabeticComparator implements Comparator {
  public int compare(Object o1, Object o2) {
    String s1 = (String) o1;
    String s2 = (String) o2;
    return s1.toLowerCase().compareTo(s2.toLowerCase());
  }
}
