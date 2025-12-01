package edu.bhscs;

public class CakeRendering {
  // Just the shift for the projection. Make sure everything lands in camera
  private int shiftX = 0;
  private int shiftY = 10;
  // height of the cake (for drawing)
  float height = 10f;
  // radius of the cake
  private float radius = 40.0f;
  // the cake's points
  float[][] verts;
  // the cake's faces
  int[][] faces;
  // what the cake is drawn on
  char[][] matrix;
  // weight of the cake
  double weightPounds;
  // original weight of the cake
  final double WEIGHTOG;

  public CakeRendering(double weight, double WEIGHTOG) {
    this.WEIGHTOG = WEIGHTOG;
    float angle = (float) (3 * Math.PI / 4);
    setCakeDrawing(weight, angle);
  }

  // Sets the radius of the cake
  public void setRadius(float x) {
    radius = x;
  }

  // Puts a bunch of candles in the matrix, as specified by age. Size will be the size of the cake,
  // so the candles can be correctly spaced.
  public void putCandlesInMatrix(char[][] matrix, int age, int size) {
    float radius = size / 2f;
    // Create a base candle model once
    float[][] baseVerts = DrawingHelpers.createBaseCandleVertices();
    int[][] faces = DrawingHelpers.createBaseCandleFaces(baseVerts);

    // Place candles evenly around a circle
    for (int i = 0; i < age; i++) {
      float angle = (float) (i * 2 * Math.PI / age);
      float offsetX = (float) (radius * Math.cos(angle));
      float offsetY = (float) (radius * Math.sin(angle));

      float[][] candleVerts = DrawingHelpers.translateVertices(baseVerts, offsetX, offsetY);
      DrawingHelpers.putInMatrix(candleVerts, faces, matrix, faces.length, shiftX, shiftY);
    }
  }

  // Puts the name on the cake.
  public void putNameOnCake(char[][] matrix, String name, int width, int height) {
    for (int i = width; i < width + name.length(); i++) {
      matrix[i][height] = name.charAt(i - width);
    }
  }

  // This sets up the cakes Verts and Faces with a certain radius
  public void setCakeDrawing(double weight, float angle) {
    // Properties of how you draw the cake
    this.weightPounds = weight;
    matrix = DrawingHelpers.generateMatrix(60, 50);
    int slices = 10;
    float thetaStart = (float) ((3f / 4f) * Math.PI);
    float dTheta = (float) ((weightPounds / WEIGHTOG) * 2f * Math.PI);
    float thetaEnd = thetaStart + dTheta;

    // Generates the mesh of the Cake, with correcting rotation and zSorting.
    verts =
        DrawingHelpers.generateCylinderSliceVertices(radius, height, slices, thetaStart, thetaEnd);
    int[][] facesOG = DrawingHelpers.generateCylinderSliceIndices(slices, thetaEnd, thetaStart);
    DrawingHelpers.rotateVertices(verts, angle, 0.0f, 0.0f);
    faces = DrawingHelpers.zSortTriangles(facesOG, verts);
  }

  public void setAngle(float angle) {
    DrawingHelpers.rotateCenter(verts, angle, 0.0f, 0.0f);
  }

  // Drawing the Cake
  public void draw(String name, String ageString, boolean goneBad) {
    int age = Integer.parseInt(ageString);

    // Ensures Cake is drawn from edge of screen
    shiftX += -1 * DrawingHelpers.getMin(verts);
    DrawingHelpers.putInMatrix(verts, faces, matrix, faces.length, shiftX, shiftY);

    // Only put candles and name on Cake if it has not been eaten.
    if (weightPounds == WEIGHTOG) {
      // print("This is happening with age " + age + " and the name is " + name);
      putCandlesInMatrix(matrix, age, (int) radius);
      putNameOnCake(matrix, name, 40, 20);
    }
    DrawingHelpers.drawCakeOnScreen(matrix, goneBad, shiftX + DrawingHelpers.getMax(verts));
    shiftX -= -1 * DrawingHelpers.getMin(verts);
  }

  public void drawCakeWithAngle(float angle) {
    setCakeDrawing(weightPounds, 0f);
    setAngle(angle);
    draw(" ", "0", false);
  }

  public void drawRotating() {
    // angle < (float) (2f * Math.PI)
    for (float angle = 0f; true; angle += 0.1) {
      drawCakeWithAngle(angle);
      System.out.flush();
      waitSomeTime(80);
      clearTerminal();
      if (angle >= (float) (2f * Math.PI)) {
        angle = 0f;
      }
      float angle2 = (float) (3 * Math.PI / 4);
      setCakeDrawing(weightPounds, angle2);
    }
  }

  public void clearTerminal() {
    System.out.print("\033[2J");
    System.out.print("\033[9999;1H");
  }

  public void setShiftX(int x) {
    shiftX = x;
  }

  public void waitSomeTime(int time) {
    try {
      Thread.sleep(time);
    } catch (Exception e) {
      // catching the exception
      System.out.println(e);
    }
  }

  public static void print(String x) {
    System.out.print(x);
  }
}
