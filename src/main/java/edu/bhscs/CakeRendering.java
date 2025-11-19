package edu.bhscs;

public class CakeRendering {
  // Just the shift for the projection. Make sure everything lands in camera
  private int shiftX = 0;
  private int shiftY = 50;
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
    setCakeDrawing(weight);
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
  public void setCakeDrawing(double weight) {
    // Properties of how you draw the cake
    this.weightPounds = weight;
    matrix = DrawingHelpers.generateMatrix(140);
    int slices = 10;
    float thetaStart = (float) ((3f / 4f) * Math.PI);
    float dTheta = (float) ((weightPounds / WEIGHTOG) * 2f * Math.PI);
    float thetaEnd = thetaStart + dTheta;

    // Generates the mesh of the Cake, with correcting rotation and zSorting.
    verts =
        DrawingHelpers.generateCylinderSliceVertices(radius, height, slices, thetaStart, thetaEnd);
    int[][] facesOG = DrawingHelpers.generateCylinderSliceIndices(slices, thetaEnd, thetaStart);
    DrawingHelpers.rotateVertices(verts, (float) (3 * Math.PI / 4), 0.0f, 0.0f);
    faces = DrawingHelpers.zSortTriangles(facesOG, verts);
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
      putNameOnCake(matrix, name, (int) radius + 40, 20);
    }
    DrawingHelpers.drawCakeOnScreen(matrix, goneBad, shiftX + DrawingHelpers.getMax(verts));
    shiftX -= -1 * DrawingHelpers.getMin(verts);
  }

  public void setShiftX(int x) {
    shiftX = x;
  }

  public static void print(String x) {
    System.out.print(x);
  }
}
