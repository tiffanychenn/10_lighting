import java.io.*;

public class Image {

	private int[][][] pixels;
	private int[] background;
	private double[][] zbuffer;

	public Image(){
		pixels = new int[500][500][3];
		background = new int[3];
		zbuffer = new double[500][500];
		fillImage();
	}

	public Image(int[] backgroundColor){
		pixels = new int[500][500][3];
		background = backgroundColor;
		zbuffer = new double[500][500];
		fillImage();
	}

	public Image(int x, int y){
		pixels = new int[y][x][3];
		background = new int[3];
		zbuffer = new double[y][x];
		fillImage();
	}

	public Image(int x, int y, int[] backgroundColor){
		pixels = new int[y][x][3];
		background = backgroundColor;
		zbuffer = new double[y][x];
		fillImage();
	}

	private void fillImage(){
		for (int i = 0; i < pixels.length; i ++){
			for (int j = 0; j < pixels[0].length; j ++){
				pixels[i][j] = background;
				zbuffer[i][j] = Double.NEGATIVE_INFINITY;
			}
		}
	}

	public void setPixel(int x, int y, double z, int[] color){
		/*System.out.println(x);
		System.out.println(y);
		System.out.println();*/
		y = pixels.length - y - 1;
		if (x >= 500 || x < 0 || y >= 500 || y < 0) return;
		if (z < zbuffer[y][x]) return;
		pixels[y][x] = color;
		zbuffer[y][x] = z;
	}

	public void draw(){
        try {
            File f = new File("image.ppm");
            FileWriter photo = new FileWriter(f);
            BufferedWriter w = new BufferedWriter(photo);
            w.write("P3\n" + pixels.length + " " + pixels[0].length + "\n255\n");
            for (int i = 0; i < pixels.length; i ++){
            	for (int j = 0; j < pixels[0].length; j ++){
            		int[] pixel = pixels[i][j];
            		w.write(pixel[0] + " " + pixel[1] + " " + pixel[2] + " ");
            	}
            	w.newLine();
            }
            w.close();
        }
        catch (Exception e) {
            System.out.println(e);
        }
	}

}