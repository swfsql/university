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
  
  // images found in the stages folder
  Image wall, // for collision tests
        ground, // drawn under the bar
        roof; // drawn above the bar

  public String [][] stages; // every stages found in the stages folder
  // [0] = stage name (from the .txt file)
  // [1] = wall image extension
  // [2] = roof image extension. if there is no roof image, this is null.
  // [3] = ground image extension. if there is no ground image, this is null. 

  public boolean test_collision(int i, int j) {
    return bimg.getRGB(i, j) != -1; // if black, returns true. otherwise, false.
  }

  public void searchStages() {
    String[] files = new File("./stages").list(); // a list containing every file inside stages folder.
    Arrays.sort(files, new AlphabeticComparator()); // sort that list alphabetically
    java.util.List<String[]> list = new ArrayList<String[]>(); // we will add each available new stage in this temporary list.

    for (int i = 0; i < files.length; i++) { // for each file in the file list
      if (!files[i].matches("^.+\\.txt$")) continue; // if this is not a txt file, skip.
      String name = files[i].replaceFirst("^(.+)\\.txt$", "$1"); // we grab the file name (without the extension)
      
      // when we sorted the files, the order is:
      // a-ground.png
      // a-roof.png
      // a-wall.png
      // a.txt
      // so if we found a .txt file, "a.txt", for example, we want to check "back" if there is corresponding image files.
      
      if (i < 1) continue; // if there is no space for image, there is no image, so this .txt file cant be a stage.
      if (!files[i - 1].matches("^" + name + "-wall\\.png$")) continue; // we surely need a image for wall collision. If we dont find it, a.txt must not be a stage.
      
      // from now on we know that there is at least a.txt and a-wall.png, so this IS a new stage. But we will check for extra information, like ground and/or roof images.
      
      String[] st = new String[4];
      st[0] = name;
      st[1] = "png";
      st[2] = i > 1 && files[i - 2].matches("^" + name + "-roof\\.png$")? "png" : null;
      st[3] = (st[2] == null ? (i > 1 && files[i - 2].matches("^" + name + "-ground\\.png$")? "png" : null) : (i > 2 && files[i - 3].matches("^" + name + "-ground\\.png$")? "png" : null));
      list.add(st);
      System.out.println(files[i]); // we show the name of this available stage
    }

    // actually add every found stages to the stages array
    stages = new String[list.size()][4];
    for (int i = 0; i < list.size(); i++) {
      stages[i] = list.get(i);
    }
  }

  // we already have the filenames inside the stages array, so we just need to access some index.
  public void load(int i, Bar bar) throws Exception {
    ground = roof = null;

    // change bar initial conditions. Maybe we should move this function to the bar, idk, since the filename is inside stages[i][0] inside this Stage object.
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

    // now we actually read the image file into wall, roof and ground objects.
    wall = ImageIO.read(new File("stages/" + stages[i][0] + "-wall." + stages[i][1]));
    bimg = (BufferedImage) wall;
    w = bimg.getWidth(); // we suppose that all images have the same size
    h = bimg.getHeight();

    if (stages[i][2] != null) {
      roof = ImageIO.read(new File("stages/" + stages[i][0] + "-roof." + stages[i][2]));
    }
    if (stages[i][3] != null) {
      ground = ImageIO.read(new File("stages/" + stages[i][0] + "-ground." + stages[i][3]));

    }

  }

  // draw image bellow the camera
  public void drawBelow(Camera cam) {
    Image img = ground != null ? ground : roof != null ? roof : wall;
    ((Graphics2D) cam.gv).drawImage(img, cam.w / 2 - cam.x, cam.h / 2 - cam.y, cam);
  }
  // if there is no ground but there is a roof, the roof is drawn as a ground image.

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
