public class EdgeMatrix extends Matrix {

    public EdgeMatrix(int rows, int cols){
        super(rows, cols);
    }

    public void addEdge(double[] point0, double[] point1){
        addPoint(point0);
        addPoint(point1);
    }

    public void addCircle(double cx, double cy, double cz, double r){
        double step = 0.01;
        for (double t = 0; t < 1; t += step){
            double x0 = r * Math.cos(2 * Math.PI * t) + cx;
            double y0 = r * Math.sin(2 * Math.PI * t) + cy;
            double x1 = r * Math.cos(2 * Math.PI * (t + step)) + cx;
            double y1 = r * Math.sin(2 * Math.PI * (t + step)) + cy;
            addEdge(new double[] {x0, y0, cz, 1}, new double[] {x1, y1, cz, 1});
        }
    }

    public void addCurve(double x0, double y0, double x1, double y1, double x2, double y2, double x3, double y3, String type){
        Matrix curve = new Matrix(4, 4);
        if (type.equals("hermite")){
            curve.addPoint(new double[] {2, -3, 0, 1});
            curve.addPoint(new double[] {-2, 3, 0, 0});
            curve.addPoint(new double[] {1, -2, 1, 0});
            curve.addPoint(new double[] {1, -1, 0, 0});
        }
        else {
            curve.addPoint(new double[] {-1, 3, -3, 1});
            curve.addPoint(new double[] {3, -6, 3, 0});
            curve.addPoint(new double[] {-3, 3, 0, 0});
            curve.addPoint(new double[] {1, 0, 0, 0});
        }
        Matrix x = new Matrix(4, 4);
        x.addPoint(new double[] {x0, x1, x2, x3});
        Matrix y = new Matrix(4, 4);
        y.addPoint(new double[] {y0, y1, y2, y3});
        Matrix xcoefs = multi(curve, x);
        Matrix ycoefs = multi(curve, y);
        double step = 0.01;
        for (double t = 0; t < 1; t += step){
            double x4 = xcoefs.getNum(0, 0) * Math.pow(t, 3) + xcoefs.getNum(1, 0) * Math.pow(t, 2) + xcoefs.getNum(2, 0) * t + xcoefs.getNum(3, 0);
            double y4 = ycoefs.getNum(0, 0) * Math.pow(t, 3) + ycoefs.getNum(1, 0) * Math.pow(t, 2) + ycoefs.getNum(2, 0) * t + ycoefs.getNum(3, 0);
            double x5 = xcoefs.getNum(0, 0) * Math.pow(t + step, 3) + xcoefs.getNum(1, 0) * Math.pow(t + step, 2) + xcoefs.getNum(2, 0) * (t + step) + xcoefs.getNum(3, 0);
            double y5 = ycoefs.getNum(0, 0) * Math.pow(t + step, 3) + ycoefs.getNum(1, 0) * Math.pow(t + step, 2) + ycoefs.getNum(2, 0) * (t + step) + ycoefs.getNum(3, 0);
            addEdge(new double[] {x4, y4, 0, 1}, new double[] {x5, y5, 0, 1});
        }
    }

}