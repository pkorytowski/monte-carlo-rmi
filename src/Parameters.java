import java.io.Serializable;
import java.security.SecureRandom;

public class Parameters implements Serializable {
    private final double c;
    private long localIterations;
    private final double pAbsor;
    private final int h;
    SecureRandom generator;
    long[][] results;

    public Parameters(double c, 
                      long localIterations, 
                      double pAbsor, 
                      int h, 
                      long[][] results, 
                      SecureRandom generator) {
        this.c = c;
        this.localIterations = localIterations;
        this.pAbsor = pAbsor;
        this.h = h;
        this.results = results;
        this.generator = generator;
    }

    public double getC() {
        return c;
    }

    public long getLocalIterations() {
        return localIterations;
    }

    public double getPAbsor() {
        return pAbsor;
    }

    public int getH() {
        return h;
    }

}
