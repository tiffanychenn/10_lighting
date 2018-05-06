/*  
    REMEMBER!!!
    EACH POINT IS A COLUMN, NOT A ROW!
    CHANGE THINGS ACCORDINGLY SHOULD MORE THINGS BE IMPLEMENTED
*/

import java.lang.*;

public class Matrix {

    private double[][] matrix;
    private int point_number;

    public Matrix(int rows, int cols){
        matrix = new double[rows][cols];
    }

    public void ident(){
        if (matrix.length != matrix[0].length){
            System.out.println("This ain't a square matrix!");
            return;
        }
        for (int i = 0; i < matrix.length; i ++){
            for (int j = 0; j < matrix[0].length; j ++){
                if (i == j){
                    matrix[i][j] = 1.0;
                }
                else {
                    matrix[i][j] = 0.0;
                }
            }
        }
        point_number = matrix.length;
    }

    public void addPoint(double[] point){
        if (point.length != matrix.length){
            System.out.println("Point isn't the same size as the matrix.");
            return;
        }
        if (point_number >= matrix[0].length){
            resize();
        }
        for (int i = 0; i < point.length; i ++){
            matrix[i][point_number] = point[i];
        }
        point_number ++;
    }

    private void resize(){
        int new_size = point_number + 10;
        double[][] temp = new double[matrix.length][new_size];
        for (int i = 0; i < matrix.length; i ++){
            for (int j = 0; j < matrix[0].length; j ++){
                temp[i][j] = matrix[i][j];
            }
        }
        matrix = temp;
    }

    public double getNum(int row, int col){
        return matrix[row][col];
    }

    public int[] getPoint(int col){
        int[] temp = new int[matrix.length];
        for (int i = 0; i < matrix.length; i ++){
            temp[i] = (int)(Math.round(matrix[i][col]));
        }
        return temp;
    }

    public double[] getPointDouble(int col){
        double[] temp = new double[matrix.length];
        for (int i = 0; i < matrix.length; i ++){
            temp[i] = matrix[i][col];
        }
        return temp;
    }

    public int getRows(){
        return matrix.length;
    }

    public int getCols(){
        return point_number;
    }

    public String toString(){
        String temp = new String();
        for (int i = 0; i < matrix.length; i ++){
            for (int j = 0; j < point_number; j ++){
                String tempp = String.valueOf(matrix[i][j]);
                temp += tempp + " ";
            }
            temp += "\n";
        }
        return temp;
    }

    public static Matrix generateSphere(double cx, double cy, double cz, double r){
        Matrix points = new Matrix(4, 4);
        for (double phi = 0; phi < 1; phi += 0.05){
            for (double theta = 0; theta < 1.01; theta += 0.05){
                double x = r * Math.cos(Math.PI * theta) + cx;
                double y = r * Math.sin(Math.PI * theta) * Math.cos(phi * 2 * Math.PI) + cy;
                double z = r * Math.sin(Math.PI * theta) * Math.sin(phi * 2 * Math.PI) + cz;
                points.addPoint(new double[] {x, y, z, 1});
            }
        }
        return points;
    }

    public static Matrix generateTorus(double cx, double cy, double cz, double r1, double r2){
        Matrix points = new Matrix(4, 4);
        for (double phi = 0; phi < 1; phi += 0.05){
            for (double theta = 0; theta < 1; theta += 0.05){
                double x = Math.cos(2 * Math.PI * phi) * (r1 * Math.cos(2 * Math.PI * theta) + r2) + cx;
                double y = r1 * Math.sin(2 * Math.PI * theta) + cy;
                double z = -1 * Math.sin(2 * Math.PI * phi) * (r1 * Math.cos(2 * Math.PI * theta) + r2) + cz;
                points.addPoint(new double[] {x, y, z, 1});
            }
        }
        return points;
    }

    public static Matrix multi(Matrix m0, Matrix m1){
        Matrix temp = new Matrix(m0.getRows(), m1.getCols());
        int n = m0.getCols();
        //System.out.println("Multiplying\n" + m0 + " with\n" + m1);
        for (int i = 0; i < m1.getCols(); i ++){
            double[] nums = new double[m0.getRows()];
            for (int j = 0; j < m0.getRows(); j ++){
                double sum = 0;
                for (int k = 0; k < n; k ++){
                    //System.out.println(i + " " + j + " " + k);
                    sum += m0.getNum(j, k) * m1.getNum(k, i);
                }
                nums[j] = sum;
                //System.out.println(sum);
            }
            temp.addPoint(nums);
            //System.out.println("Stage " + i + "\n" + temp + "\n");
        }
        return temp;
    }

    public static PolygonMatrix multi(Matrix m0, PolygonMatrix m1){
        PolygonMatrix temp = new PolygonMatrix(m0.getRows(), m1.getCols());
        int n = m0.getCols();
        //System.out.println("Multiplying\n" + m0 + " with\n" + m1);
        for (int i = 0; i < m1.getCols(); i ++){
            double[] nums = new double[m0.getRows()];
            for (int j = 0; j < m0.getRows(); j ++){
                double sum = 0;
                for (int k = 0; k < n; k ++){
                    //System.out.println(i + " " + j + " " + k);
                    sum += m0.getNum(j, k) * m1.getNum(k, i);
                }
                nums[j] = sum;
                //System.out.println(sum);
            }
            temp.addPoint(nums);
            //System.out.println("Stage " + i + "\n" + temp + "\n");
        }
        return temp;
    }

    public static EdgeMatrix multi(Matrix m0, EdgeMatrix m1){
        EdgeMatrix temp = new EdgeMatrix(m0.getRows(), m1.getCols());
        int n = m0.getCols();
        //System.out.println("Multiplying\n" + m0 + " with\n" + m1);
        for (int i = 0; i < m1.getCols(); i ++){
            double[] nums = new double[m0.getRows()];
            for (int j = 0; j < m0.getRows(); j ++){
                double sum = 0;
                for (int k = 0; k < n; k ++){
                    //System.out.println(i + " " + j + " " + k);
                    sum += m0.getNum(j, k) * m1.getNum(k, i);
                }
                nums[j] = sum;
                //System.out.println(sum);
            }
            temp.addPoint(nums);
            //System.out.println("Stage " + i + "\n" + temp + "\n");
        }
        return temp;
    }

    public static double dotproduct(double[] v0, double[] v1){
        return v0[0] * v1[0] + v0[1] * v1[1] + v0[2] * v1[2];
    }

    public static double[] crossproduct(int[] v0, int[] v1){
        double[] returned = new double[3];
        returned[0] = v0[1] * v1[2] - v0[2] * v1[1];
        returned[1] = -1 * (v0[0] * v1[2] - v0[2] * v1[0]);
        returned[2] = v0[0] * v1[1] - v0[1] * v1[0];
        return returned;
    }

    public static double[] normalize(double[] v){
        double mag = Math.sqrt(Math.pow(v[0], 2) + Math.pow(v[1], 2) + Math.pow(v[2], 2));
        return new double[] {v[0] / mag, v[1] / mag, v[2] / mag};
    }

}