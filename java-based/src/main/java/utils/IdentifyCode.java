package utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;
import java.util.Random;
import lombok.Data;
import lombok.Generated;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

/**
 * 验证码
 *
 * @author jensen_deng
 */
@Data
@Generated
public class IdentifyCode {

  static class TestIdentifyCode {
    @Test
    void test() {
      IdentifyCode identifyCode = new IdentifyCode();
      BufferedImage code = identifyCode.getIdentifyImage();
      System.out.println(code);
    }
  }

  public static final int MAX_VALUE = 255;
  public static final int RANDOM_LINE = 100;
  public static final int CODE_NUMBER = 4;
  private String code;
  private Graphics g;
  private String ctmp;
  private int itmp;

  @SneakyThrows
  public BufferedImage getIdentifyImage() {
    int width = 100;
    int height = 28;
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics gs = image.getGraphics();
    Graphics2D g2d = (Graphics2D) gs;
    Random random = SecureRandom.getInstanceStrong();
    Font font = new Font("华文宋体", Font.BOLD, 19);
    gs.setColor(Color.white);
    gs.fillRect(0, 0, width, height);
    gs.setFont(font);
    gs.setColor(Color.white);

    for (int i = 0; i < RANDOM_LINE; i++) {
      int x = random.nextInt(width - 1);
      int y = random.nextInt(height - 1);
      int x1 = random.nextInt(6) + 1;
      int y1 = random.nextInt(12) + 1;
      BasicStroke bs = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
      Line2D line = new Double(x, y, (double) x1 + x, (double) y1 + y);
      g2d.setStroke(bs);
      g2d.draw(line);
    }
    StringBuilder sRand = new StringBuilder();
    for (int i = 0; i < CODE_NUMBER; i++) {
      switch (random.nextInt(3)) {
        case 1 -> {
          itmp = random.nextInt(26) + 65;
          ctmp = String.valueOf((char) itmp);
        }
        case 2 -> {
          itmp = random.nextInt(25) + 65;
          ctmp = String.valueOf((char) itmp);
        }
        default -> {
          itmp = random.nextInt(10) + 48;
          ctmp = String.valueOf((char) itmp);
        }
      }
      sRand.append(ctmp);
      Color color =
          new Color(20 + random.nextInt(110), 20 + random.nextInt(110), random.nextInt(100));
      gs.setColor(color);
      gs.drawString(ctmp, 19 * i + 19, 19);
    }
    this.setCode(sRand.toString());
    this.setG(gs);
    return image;
  }
}
