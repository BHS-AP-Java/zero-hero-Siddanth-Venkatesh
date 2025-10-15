package edu.bhscs;

public class DrawingHelpers {
  public static void plotLine(int x0, int y0, int x1, int y1, char[][] things){
    int dx = Math.abs(x1 - x0);
    int sx = x0 < x1 ? 1 : -1;
    int dy = 0 - Math.abs(y1 - y0);
    int sy = y0 < y1 ? 1 : -1;
    int error = dx + dy;

    while (true){
        plot(x0, y0, things);
        int e2 = 2 * error;
        if (e2 >= dy){
            if (x0 == x1){
                break;
            }
            error = error + dy;
            x0 = x0 + sx;
        }
        if (e2 <= dx){
            if (y0 == y1) {
              break;
            }
            error = error + dx;
            y0 = y0 + sy;
        }
    }
  }

  public static void plot(int x, int y, char[][] things){
    things[x][y] = 'Q';
  }

}
