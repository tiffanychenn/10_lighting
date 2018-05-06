import java.util.*;
import java.lang.*;

public class Drawing {

    public static void drawpolygons(Matrix m, Image i, int ambient, double[] color, double[] location, double[] view, double[] areflection, double[] dreflection, double[] sreflection){
        for (int j = 0; j + 2 < m.getCols(); j += 3){
            Random rand = new Random();
			int[] point0 = m.getPoint(j);
			int[] point1 = m.getPoint(j + 1);
			int[] point2 = m.getPoint(j + 2);
            if (viewable(point0, point1, point2, view)){
                int[] colorr = generatecolor(point0, point1, point2, ambient, color, location, view, areflection, dreflection, sreflection);
			    scanline(point0, point1, point2, i, colorr);
            }
        }
    }

    public static void scanline(int[] point0, int[] point1, int[] point2, Image i, int[] color){
        int[][] order = sort(point0, point1, point2);
        int[] pointb = order[0];
        int[] pointm = order[1];
        int[] pointt = order[2];
        double x0 = (double)pointb[0];
        double x1 = (double)pointb[0];
        double z0 = (double)pointb[2];
        double z1 = (double)pointb[2];
        double delta, deltaz;
        if (pointm[1] == pointb[1]) {
            delta = ((double)(pointt[0] - pointm[0])) / ((double)(pointt[1] - pointm[1]));
            deltaz = ((double)(pointt[2] - pointm[2])) / ((double)(pointt[1] - pointm[1]));
        }
        else {
            delta = ((double)(pointm[0] - pointb[0])) / ((double)(pointm[1] - pointb[1]));
            deltaz = ((double)(pointm[2] - pointb[2])) / ((double)(pointm[1] - pointb[1]));
        }
        for (int y = pointb[1]; y < pointm[1]; y ++){
            horizontal((int)(Math.round(x0)), (int)(Math.round(x1)), y, z0, z1, i, color);
            x0 += ((double)(pointt[0] - pointb[0])) / ((double)(pointt[1] - pointb[1]));
            x1 += delta;
            z0 += ((double)(pointt[2] - pointb[2])) / ((double)(pointt[1] - pointb[1]));
            z1 += deltaz;
        }
        x1 = (double)pointm[0];
        z1 = (double)pointm[2];
        delta = ((double)(pointt[0] - pointm[0])) / ((double)(pointt[1] - pointm[1]));
        deltaz = ((double)(pointt[2] - pointm[2])) / ((double)(pointt[1] - pointm[1]));
        for (int y = pointm[1]; y <= pointt[1]; y ++){
            horizontal((int)(Math.round(x0)), (int)(Math.round(x1)), y, z0, z1, i, color);
            x0 += ((double)(pointt[0] - pointb[0])) / ((double)(pointt[1] - pointb[1]));
            x1 += delta;
            z0 += ((double)(pointt[2] - pointb[2])) / ((double)(pointt[1] - pointb[1]));
            z1 += deltaz;
        }
    }

	public static void drawlines(Matrix m, Image i, int[] color){
		for (int j = 0; j + 1 < m.getCols(); j += 2){
			int[] point0 = m.getPoint(j);
			int[] point1 = m.getPoint(j + 1);
			drawline(point0[0], point0[1], point0[2], point1[0], point1[1], point1[2], i, color);
		}
	}

	public static void drawline(int x0, int y0, double z0, int x1, int y1, double z1, Image i, int[] color){
        if (x0 > x1){
            int temp = x1;
            x1 = x0;
            x0 = temp;
            temp = y1;
            y1 = y0;
            y0 = temp;
            double tempp = z1;
            z1 = z0;
            z0 = tempp;
        }
        if (x0 == x1){
            vertical(x0, y0, y1, (double)z0, (double)z1, i, color);
        } 
        else if (y0 == y1){
            horizontal(x0, x1, y0, (double)z0, (double)z1, i, color);
        }
        else {
            double slope = -1.0 * (y1 - y0) / (x1 - x0);
            //System.out.println(slope);
            if (slope == 1){
                slope1(x0, y0, x1, y1, (double)z0, (double)z1, i, color);
            }
            else if (slope == -1){
                slopenegative1(x0, y0, x1, y1, (double)z0, (double)z1, i, color);
            }
            else if (slope < -1){
                octant7(x0, y0, x1, y1, (double)z0, (double)z1, i, color);
            }
            else if (slope < 0){
                octant8(x0, y0, x1, y1, (double)z0, (double)z1, i, color);
            }
            else if (slope < 1){
                octant1(x0, y0, x1, y1, (double)z0, (double)z1, i, color);
            }
            else {
                octant2(x0, y0, x1, y1, (double)z0, (double)z1, i, color);
            }
        }
    }

    private static void horizontal(int x0, int x1, int y, double z0, double z1, Image im, int[] color){
        if (x0 > x1){
            int temp = x0;
            x0 = x1;
            x1 = temp;
            double tempp = z0;
            z0 = z1;
            z1 = tempp;
        }
        double slope = (z1 - z0) / ((double)(x1 - x0 + 1));
        for (int i = x0; i <= x1; i ++){
        	im.setPixel(i, y, z0, color);
            z0 += slope;
        }
    }

    private static void vertical(int x, int y0, int y1, double z0, double z1, Image im, int[] color){
        if (y0 > y1){
            int temp = y0;
            y0 = y1;
            y1 = temp;
            double tempp = z0;
            z0 = z1;
            z1 = tempp;
        }
        double slope = (z1 - z0) / ((double)(y1 - y0 + 1));
        for (int i = y0; i <= y1; i ++){
           	im.setPixel(x, i, z0, color);
            z0 += slope;
        }
    }

    private static void slope1(int x0, int y0, int x1, int y1, double z0, double z1, Image im, int[] color){
        double slope = (z1 - z0) / ((double)(y1 - y0 + 1));
        while (x0 <= x1){
        	im.setPixel(x0, y0, z0, color);
            x0 ++;
            y0 --;
            z0 += slope;
        }
    }

    private static void slopenegative1(int x0, int y0, int x1, int y1, double z0, double z1, Image im, int[] color){
        double slope = (z1 - z0) / ((double)(y1 - y0 + 1));
        while (x0 <= x1){
            im.setPixel(x0, y0, z0, color);
            x0 ++;
            y0 ++;
            z0 += slope;
        }
    }

    private static void octant1(int x0, int y0, int x1, int y1, double z0, double z1, Image im, int[] color){
        int a = y0 - y1;
        int b = -1 * (x1 - x0);
        int d = 2 * a + b;
        double slope = (z1 - z0) / ((double)(x1 - x0 + 1));
        //System.out.println(a + " " + b);
        while (x0 < x1){
            im.setPixel(x0, y0, z0, color);
            x0 ++;
            if (d > 0){
                y0 --;
                d += 2 * b;
            }
            z0 += slope;
            d += 2 * a;
        }
        return;
    }

    private static void octant2(int x0, int y0, int x1, int y1, double z0, double z1, Image im, int[] color){
        int a = y0 - y1;
        int b = -1 * (x1 - x0);
        int d = a + 2 * b;
        double slope = (z1 - z0) / ((double)(y1 - y0 + 1));
        //System.out.println(a + " " + b);
        while (y0 > y1){
            im.setPixel(x0, y0, z0, color);
            y0 --;
            if (d < 0){
                x0 ++;
                d += 2 * a;
            }
            z0 += slope;
            d += 2 * b;
        }
        return;
    }

    private static void octant7(int x0, int y0, int x1, int y1, double z0, double z1, Image im, int[] color){
        int a = y1 - y0;
        int b = -1 * (x1 - x0);
        int d = a - 2 * b;
        double slope = (z1 - z0) / ((double)(y1 - y0 + 1));
        //System.out.println(a + " " + b);
        while (y0 < y1){
            im.setPixel(x0, y0, z0, color);
            y0 ++;
            if (d < 0){
                x0 ++;
                d += 2 * a;
            }
            z0 += slope;
            d += 2 * b;
        }
        return;
    }

    private static void octant8(int x0, int y0, int x1, int y1, double z0, double z1, Image im, int[] color){
        int a = y1 - y0;
        int b = -1 * (x1 - x0);
        int d = 2 * a - b;
        double slope = (z1 - z0) / ((double)(x1 - x0 + 1));
        //System.out.println(a + " " + b);
        while (x0 < x1){
            im.setPixel(x0, y0, z0, color);
            x0 ++;
            if (d > 0){
                y0 ++;
                d += 2 * b;
            }
            z0 += slope;
            d += 2 * a;
        }
        return;
    }

    private static boolean viewable(int[] point0, int[] point1, int[] point2, double[] view){
        int[] a = new int[] {point1[0] - point0[0], point1[1] - point0[1], point1[2] - point0[2]};
        int[] b = new int[] {point2[0] - point0[0], point2[1] - point0[1], point2[2] - point0[2]};
        double[] cross = Matrix.crossproduct(a, b);
        double dot = Matrix.dotproduct(cross, view);
        return dot > 0;
    }

    private static int[] generatecolor(int[] point0, int[] point1, int[] point2, int ambient, double[] color, double[] location, double[] view, double[] areflection, double[] dreflection, double[] sreflection){
        int[] a = calculateambient(ambient, areflection);
        int[] d = calculatediffuse(point0, point1, point2, color, location, dreflection);
        int[] s = calculatespecular(point0, point1, point2, color, location, sreflection, view);
        int[] returned = new int[3];
        for (int i = 0; i < 3; i ++){
            int c = a[i] + d[i] + s[i];
            if (c > 255){
                c = 255;
            }
            if (c < 0){
                c = 0;
            }
            returned[i] = c;
        }
        return returned;
    }

    private static int[] calculateambient(int ambient, double[] areflection){
        return new int[] {(int)(Math.round((double)ambient * areflection[0])), (int)(Math.round((double)ambient * areflection[1])), (int)(Math.round((double)ambient * areflection[2]))};
    }

    private static int[] calculatediffuse(int[] point0, int[] point1, int[] point2, double[] color, double[] location, double[] dreflection){
        int[] a = new int[] {point1[0] - point0[0], point1[1] - point0[1], point1[2] - point0[2]};
        int[] b = new int[] {point2[0] - point0[0], point2[1] - point0[1], point2[2] - point0[2]};
        double[] normal = Matrix.normalize(Matrix.crossproduct(a, b));
        double dot = Matrix.dotproduct(Matrix.normalize(location), normal);
        if (dot < 0) dot = 0;
        //System.out.println(dot);
        return new int[] {(int)(Math.round(color[0] * dreflection[0] * dot)), (int)(Math.round(color[1] * dreflection[1] * dot)), (int)(Math.round(color[2] * dreflection[2] * dot))};
    }

    private static int[] calculatespecular(int[] point0, int[] point1, int[] point2, double[] color, double[] location, double[] sreflection, double[] view){
        int[] a = new int[] {point1[0] - point0[0], point1[1] - point0[1], point1[2] - point0[2]};
        int[] b = new int[] {point2[0] - point0[0], point2[1] - point0[1], point2[2] - point0[2]};
        double[] normal = Matrix.normalize(Matrix.crossproduct(a, b));
        location = Matrix.normalize(location);
        double dot = Matrix.dotproduct(location, normal);
        double[] temp = new double[] {(2 * normal[0] * dot) - location[0], (2 * normal[1] * dot) - location[1], (2 * normal[2] * dot) - location[2]};
        //System.out.println(temp[0] + " " + temp[1] + " " + temp[2]);
        dot = Matrix.dotproduct(Matrix.normalize(temp), Matrix.normalize(view));
        if (dot < 0) dot = 0;
        return new int[] {(int)(Math.round(color[0] * sreflection[0] * Math.pow(dot, 3))), (int)(Math.round(color[1] * sreflection[1] * Math.pow(dot, 3))), (int)(Math.round(color[2] * sreflection[2] * Math.pow(dot, 3)))};
    }

    private static int[][] sort(int[] point0, int[] point1, int[] point2){
        int[][] returned = new int[3][4];
        int[] temp = new int[] {point0[1], point1[1], point2[1]};
        Arrays.sort(temp);
        ArrayList<Integer> ar = new ArrayList<Integer>();
        Integer zero = new Integer(0);
        Integer one = new Integer(1);
        Integer two = new Integer(2);
        for (int i = 0; i < 3; i ++){
            if (point0[1] == temp[i] && ar.indexOf(zero) == -1){
                returned[i] = point0;
                ar.add(zero);
            }
            else if (point1[1] == temp[i] && ar.indexOf(one) == -1){
                returned[i] = point1;
                ar.add(one);
            }
            else {
                returned[i] = point2;
                ar.add(two);
            }
        }
        //System.out.println(returned[0][1] + " " + returned[1][1] + " " + returned[2][1]);
        return returned;
    }

}
