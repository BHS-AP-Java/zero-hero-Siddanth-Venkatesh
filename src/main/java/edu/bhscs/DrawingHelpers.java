package edu.bhscs;

public class DrawingHelpers {
  public static void plotLine(int x0, int y0, int x1, int y1, char[][] things) {
    int dx = Math.abs(x1 - x0);
    int sx = x0 < x1 ? 1 : -1;
    int dy = 0 - Math.abs(y1 - y0);
    int sy = y0 < y1 ? 1 : -1;
    int error = dx + dy;

    while (true) {
      plot(x0, y0, things, 'Q');
      int e2 = 2 * error;
      if (e2 >= dy) {
        if (x0 == x1) {
          break;
        }
        error = error + dy;
        x0 = x0 + sx;
      }
      if (e2 <= dx) {
        if (y0 == y1) {
          break;
        }
        error = error + dx;
        y0 = y0 + sy;
      }
    }
  }

  public static void fillTriangle(
      int x0, int y0, int x1, int y1, int x2, int y2, char[][] things, char ch) {
    // Sort vertices by y-coordinate
    if (y0 > y1) {
      int t;
      t = y0;
      y0 = y1;
      y1 = t;
      t = x0;
      x0 = x1;
      x1 = t;
    }
    if (y1 > y2) {
      int t;
      t = y1;
      y1 = y2;
      y2 = t;
      t = x1;
      x1 = x2;
      x2 = t;
    }
    if (y0 > y1) {
      int t;
      t = y0;
      y0 = y1;
      y1 = t;
      t = x0;
      x0 = x1;
      x1 = t;
    }

    int totalHeight = y2 - y0;
    if (totalHeight == 0) return;

    for (int i = 0; i < totalHeight; i++) {
      boolean secondHalf = i > y1 - y0 || y1 == y0;
      int segmentHeight = secondHalf ? y2 - y1 : y1 - y0;
      float alpha = (float) i / totalHeight;
      float beta = (float) (i - (secondHalf ? y1 - y0 : 0)) / segmentHeight;

      int ax = (int) (x0 + (x2 - x0) * alpha);
      int bx = secondHalf ? (int) (x1 + (x2 - x1) * beta) : (int) (x0 + (x1 - x0) * beta);

      if (ax > bx) {
        int t = ax;
        ax = bx;
        bx = t;
      }

      int y = y0 + i;
      for (int x = ax; x <= bx; x++) {
        plot(x, y, things, ch);
      }
    }
  }

  public static void plot(int x, int y, char[][] things, char ch) {
    if (x >= 0 && x < things.length && y >= 0 && y < things[0].length) things[x][y] = ch;
  }
}
