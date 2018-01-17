import java.util.Random;

public class WTA {

    private int noi;
    private double[] w;

    public WTA ( int numbers_of_inputs ) {
        noi = numbers_of_inputs;
        w = new double[noi];

        for ( int i = 0; i < noi; i++ )
            w[i] = new Random().nextDouble();
    }

    public void learn ( double[] x, double lr ) {

        for ( int i = 0; i < noi; i++ )
            w[i] += lr * ( x[i] - w[i] );
    }


    public double[] getW () {
        return w;
    }
}