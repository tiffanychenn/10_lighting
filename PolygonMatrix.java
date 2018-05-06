public class PolygonMatrix extends Matrix {

	public PolygonMatrix(int rows, int cols){
		super(rows, cols);
	}

	public void addPolygon(double[] point0, double[] point1, double[] point2){
        addPoint(point0);
        addPoint(point1);
        addPoint(point2);
    }

    public void addSphere(Matrix points){
        System.out.println(points.getCols());
        int step = 20;
        int lat_start = 0;
        int lat_stop = step;
        int longt_start = 0;
        int longt_stop = step;
        step ++;
        for (int lat = lat_start; lat < lat_stop; lat ++){
            for (int longt = longt_start; longt < longt_stop; longt ++){
                int p0 = lat * step + longt;
                int p1 = p0 + 1;
                int p2 = (p1 + step) % (step * (step - 1));
                int p3 = (p0 + step) % (step * (step - 1));
                if (longt != step - 2){
                    addPolygon(points.getPointDouble(p0), points.getPointDouble(p1), points.getPointDouble(p2));
                }
                if (longt != 0){
                    addPolygon(points.getPointDouble(p0), points.getPointDouble(p2), points.getPointDouble(p3));
                }
            }
        }
    }

    public void addTorus(Matrix points){
        //System.out.println(points.getCols());
        int step = 20;
        int lat_start = 0;
        int lat_stop = step;
        int longt_start = 0;
        int longt_stop = step;
        for (int lat = lat_start; lat < lat_stop; lat ++){
            for (int longt = longt_start; longt < longt_stop; longt ++){
                int p0 = lat * step + longt;
                int p1;
                if (longt == step - 1){
                    p1 = p0 - longt;
                }
                else p1 = p0 + 1;
                int p2 = (p1 + step) % (step * step);
                int p3 = (p0 + step) % (step * step);
                addPolygon(points.getPointDouble(p0), points.getPointDouble(p3), points.getPointDouble(p2));
                addPolygon(points.getPointDouble(p0), points.getPointDouble(p2), points.getPointDouble(p1));
            }
        }
    }

}