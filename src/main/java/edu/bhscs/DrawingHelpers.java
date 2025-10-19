// Siddanth Venkatesh
// P2
// Cake
// 10/19

/*
 * DESCRIPTION: Collection of loose static functions that allow drawing in the terminal
 * INPUT: Will require a static array of characters
 * OUTPUT: Returns a matrix of values with filled in values
 * EDGE CASE: Putting values outside the bounds of the matrix will cause them not to be drawn.
 */
package edu.bhscs;

import java.util.Arrays;
import java.util.Comparator;

// Information on how these work is avaliable on Wikipedia
// (https://en.wikipedia.org/wiki/Triangle_mesh)
public class DrawingHelpers {
  // Shading (Taken from:
  // https://stackoverflow.com/questions/30097953/ascii-art-sorting-an-array-of-ascii-characters-by-brightness-levels-c-c)
  private static final String SHADING =
      "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'.";
  // Amount of characters in shading
  private static final int SHADINGAMOUNT = 70;

  // Plots a line
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

  // Draws a filled in triangle
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
    if (totalHeight == 0) return; // Triangle won't be drawn if it has no height

    // Scans each line and draws in the triangle
    // Implementation has inspiration from wikipedia (made by me though)
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

  // Puts a point in an array in needed
  public static void plot(int x, int y, char[][] things, char ch) {
    if (x >= 0 && x < things.length && y >= 0 && y < things[0].length) things[x][y] = ch;
  }

  // Rotates a set of verticies
  // Implementation taken from Wikipedia (https://en.wikipedia.org/wiki/Rotation_matrix)
  public static void rotateVertices(float[][] vertices, float pitch, float yaw, float roll) {
    float x = 0f;
    float y = 0f;
    float z = 0f;

    // // Find the center to rotate them around
    // for (int i = 0; i < vertices.length; i++) {
    //   x += vertices[i][0];
    //   y += vertices[i][1];
    //   z += vertices[i][2];
    // }
    // float avgX = x / vertices.length;
    // float avgY = y / vertices.length;
    // float avgZ = z / vertices.length;

    // For now, making it so that it is not rotated around center
    float avgX = 0f;
    float avgY = 0f;
    float avgZ = 0f;



    // Breaks down the rotation into their cos and sin components
    float cp = (float) Math.cos(pitch);
    float sp = (float) Math.sin(pitch);
    float cy = (float) Math.cos(yaw);
    float sy = (float) Math.sin(yaw);
    float cr = (float) Math.cos(roll);
    float sr = (float) Math.sin(roll);

    for (int i = 0; i < vertices.length; i++) {
      x = vertices[i][0] - avgX;
      y = vertices[i][1] - avgY;
      z = vertices[i][2] - avgZ;

      // Apply pitch (x-axis)
      float y1 = y * cp - z * sp;
      float z1 = y * sp + z * cp;
      y = y1;
      z = z1;

      // Apply yaw (y-axis)
      float x2 = x * cy + z * sy;
      float z2 = -x * sy + z * cy;
      x = x2;
      z = z2;

      // Apply roll (z-axis)
      float x3 = x * cr - y * sr;
      float y3 = x * sr + y * cr;

      vertices[i][0] = x3 + avgX;
      vertices[i][1] = y3 + avgY;
      vertices[i][2] = z + avgZ;
    }
  }

  // Sorts the triangle by their average z cordinate. This makes it so that triangles are draw in
  // the correct order
  public static int[][] zSortTriangles(int[][] indices, float[][] vertices) {
    return Arrays.stream(indices)
        .sorted(
            Comparator.comparingDouble(
                tri -> {
                  float z0 = vertices[tri[0]][2];
                  float z1 = vertices[tri[1]][2];
                  float z2 = vertices[tri[2]][2];
                  return ((z0 + z1 + z2)); // negative for descending (back to front)
                }))
        .toArray(int[][]::new);
  }

  // Printing the verticies for debugging
  public static void printVertices(float[][] vertices) {
    System.out.println("Vertices (" + vertices.length + "):");
    System.out.print("[");
    for (int i = 0; i < vertices.length; i++) {
      float[] v = vertices[i];
      if (i == vertices.length - 1) {
        System.out.printf("(%.4f, %.4f, %.4f)] ", v[0], v[1], v[2]);
        break;
      }
      System.out.printf("(%.4f, %.4f, %.4f), ", v[0], v[1], v[2]);
    }
    System.out.println();
  }

  // Prints the indicies for debugigng
  public static void printIndices(int[][] indices) {
    System.out.println("Triangles (" + indices.length + "):");
    System.out.print("[");
    for (int i = 0; i < indices.length; i++) {
      int[] tri = indices[i];
      if (i == indices.length - 1) {
        System.out.printf("(%d, %d, %d)]", tri[0], tri[1], tri[2]);
        break;
      }
      System.out.printf("(%d, %d, %d), ", tri[0], tri[1], tri[2]);
    }
    System.out.println();
  }

  // Generates vertices
  public static float[][] generateCylinderSliceVertices(
      float radius, float height, int slices, float thetaStart, float thetaEnd) {

    // +2 extra rings for top & bottom cap centers
    int vertexCount = slices * 2 + 2;
    float[][] vertices = new float[vertexCount][3];

    float dTheta = (thetaEnd - thetaStart) / slices;
    float theta = (float) thetaStart;

    // Loops through a circle and adds the points (x, y, z) on the circle
    // Also adds the points (x, y, z + height) on the cirlce
    for (int i = 0; i < slices; i += 1) {
      float x = (float) (radius * Math.cos(theta));
      float y = (float) (radius * Math.sin(theta));
      vertices[i] = new float[] {x, y, 0};
      vertices[i + slices] = new float[] {x, y, height};
      theta += dTheta;
    }

    // Adds two points, one in center of the top cap, and one in the bottom
    vertices[2 * slices] = new float[] {0.0f, 0.0f, 0.0f};
    vertices[2 * slices + 1] = new float[] {0.0f, 0.0f, height};

    return vertices;
  }

  // Generate indices for sides + caps
  public static int[][] generateCylinderSliceIndices(int slices, float thetaEnd, float thetaStart) {
    // If dtheta is 2pi, the triangle should connect back up to itself
    // setting a to 1 achieves that.
    int a = 0;
    if (thetaEnd - thetaStart > 2 * Math.PI - 0.001f) {
      a = 1;
    }
    // Sides + caps
    int bottomTriCount = slices;
    int topTriCount = slices;

    int totalTriCount = 2 * slices + bottomTriCount + topTriCount + 4;
    int[][] indices = new int[totalTriCount][3];

    // Side triangles
    for (int i = 0; i < slices - 1 + a; i++) {
      indices[i] = new int[] {i, (i + 1) % slices, (i % slices) + slices};
      indices[i + slices] =
          new int[] {(i + 1) % slices, (i + 1) % slices + slices, (i % slices) + slices};
    }
    // Top and bottom caps
    for (int i = 0; i < slices - 1 + a; i++) {
      indices[2 * slices + i] = new int[] {(i % slices), ((i + 1) % slices), 2 * slices};
      indices[3 * slices + i] =
          new int[] {(i % slices) + slices, ((i + 1) % slices) + slices, 2 * slices + 1};
    }

    indices[4 * slices + 0] = new int[] {0, slices, 2 * slices};
    indices[4 * slices + 1] = new int[] {2 * slices + 1, slices, 2 * slices};
    indices[4 * slices + 2] = new int[] {slices - 1, slices + slices - 1, 2 * slices};
    indices[4 * slices + 3] = new int[] {slices - 1 + slices, 2 * slices, 2 * slices + 1};
    return indices;
  }

  public static char findShading(
      int x0, int y0, int z0, int x1, int y1, int z1, int x2, int y2, int z2) {
    // Face normal via cross product
    float ux = x1 - x0, uy = y1 - y0, uz = z1 - z0;
    float vx = x2 - x0, vy = y2 - y0, vz = z2 - z0;

    float nx = uy * vz - uz * vy;
    float ny = uz * vx - ux * vz;
    float nz = ux * vy - uy * vx;

    // Normalize normal
    float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
    if (len != 0) {
      nx /= len;
      ny /= len;
      nz /= len;
    }

    // Light direction (towards viewer)
    float lx = 0, ly = 0, lz = -1;

    // Dot product = cosine of angle between light and normal
    float intensity = nx * lx + ny * ly + nz * lz;
    intensity = Math.max(0, intensity); // clamp to [0,1]

    // Convert to shading index
    int shadeIndex = Math.round(intensity * (SHADING.length() - 1));
    char shade = SHADING.charAt(shadeIndex);
    return shade;
  }

  // Main method to debug this class
  public static void main(String[] args) {
    float radius = 20f;
    float height = 1.0f;
    int slices = 4;
    float thetaStart = (float) 2;
    float thetaEnd = (float) 3;

    float[][] verts = generateCylinderSliceVertices(radius, height, slices, thetaStart, thetaEnd);
    int[][] facesOG = generateCylinderSliceIndices(slices, thetaEnd, thetaStart);
    rotateVertices(verts, (float) (3 * Math.PI / 4), 0.0f, 0.0f);
    int[][] faces = zSortTriangles(facesOG, verts);

    printVertices(verts);
    printIndices(faces);
  }
}
