import java.io.*;

public class Params {
	double CC;
	double CS;
	int H;
	long iter;

	Params(double CC, double CS, int H, long iter) {
		this.CC = CC;
		this.CS = CS;
		this.H = H;
		this.iter = iter;
	}

	public double getCC() {
		return this.CC;
	}

	public double getCS() {
		return this.CS;
	}

	public int getH() {
		return this.H;
	}

	public long getIter() {
		return this.iter;
	}


	public static Params fromFile(String filename) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		String currentLine = reader.readLine();
		String[] partsCC = currentLine.split("=");
    currentLine = reader.readLine();
		String[] partsCS = currentLine.split("=");
    currentLine = reader.readLine();
		String[] partsH = currentLine.split("=");
    currentLine = reader.readLine();
		String[] partsIter = currentLine.split("=");
		reader.close();

    double CC = Double.parseDouble(partsCC[1]);
    double CS = Double.parseDouble(partsCS[1]);
    int H = Integer.parseInt(partsH[1]);
    long iter = Long.parseLong(partsIter[1]);


    return new Params(CC, CS, H, iter);
	}
}
