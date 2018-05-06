import java.util.*;

public class Main {

    public static void main(String[] args){
        Image i = new Image(new int[] {255, 255, 255});
        ArrayList<String> commands = Parser.parse("lighting_test");
        Stack<Matrix> s = new Stack<Matrix>();
        Matrix identy = new Matrix(4, 4);
        identy.ident();
        s.push(identy);
        int ambientcolor = 50;
        double[] color = new double[] {255, 0, 255};
        double[] location = new double[] {0.5, 0.75, 1};
        double[] view = new double[] {0, 0, 1};
        double[] areflection = new double[] {0.1, 0.1, 0.1};
        double[] dreflection = new double[] {0.5, 0.5, 0.5};
        double[] sreflection = new double[] {0.5, 0.5, 0.5};
        Parser.execute(commands, s, i, ambientcolor, color, location, view, areflection, dreflection, sreflection);
    }

}
