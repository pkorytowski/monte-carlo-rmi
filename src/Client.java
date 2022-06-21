import java.rmi.*;
import java.security.SecureRandom;			
import java.io.*;

class SideBarrier {
	int stoppedCount;		

	SideBarrier() {
		stoppedCount = 0;	
	}
	public synchronized void waitForTheOthers() throws Exception {
		stoppedCount++;
		if( stoppedCount == 2 ) {
			stoppedCount = 0;
			notifyAll();	
		}
		else {
            wait();		
        }
				
	}
}

class SideThread extends Thread {		
	String      hostName;
	String      serverName;			
	SideBarrier barrier;
    int h;
    long iter;
    double pAbsor;
    double C;
    long[][] results;
    SecureRandom generator;

	SideThread( String hostName, String serverName,
				SideBarrier barrier,
                int h,
                long iter,
                double pAbsor,
                double C, 
                long[][] results, 
                SecureRandom generator) {
		this.hostName = hostName;
		this.serverName = serverName;
		this.barrier  = barrier;
        this.h = h;
        this.iter = iter;
        this.pAbsor = pAbsor;
        this.C = C;
        this.results = results;
        this.generator = generator;
	}

	public void run() {
		try {
			SimulationInterface obj = ( SimulationInterface )
				Naming.lookup( "//" + hostName + "/" + serverName );
			long x[][] = new long[results.length][results[0].length];	
			Parameters parameters = new Parameters(C, iter, pAbsor, h, x, generator);
				
            parameters = obj.simulate_neutrons(parameters);	
            synchronized ( results ) {	
                System.out.println( "Thread " + hostName + ":" );
                for(int i=0; i<results.length; i++) {
                    for(int j=0; j<results[0].length; j++) {
                        results[i][j] += parameters.results[i][j];
                    }
                }
            }
            barrier.waitForTheOthers();
		}
		catch( Exception e ) {
			e.printStackTrace();
			return;
		}
	}
}


public class Client {
	public static void main( String arg[] ) throws Exception {

	if( arg.length < 2 ) {					
			System.out.println(
					"specify hostname1 servername1 hostname2 servername2" );
			return;
		}

		Params params = Params.fromFile("../params.txt");

		double CC = params.getCC();
		double CS = params.getCS();
		int H = params.getH();
		long iter = params.getIter();
		double C = CC + CS;
		double pAbsor = CC / C;

		long[][] results = new long[H][3];		
			
		SideBarrier barrier = new SideBarrier();
		Thread thread1 = new SideThread( arg[0], arg[1], barrier, H, iter/2, 
							pAbsor, C, results, new SecureRandom(SecureRandom.getSeed(32)));
		thread1.start();
		Thread thread2 = new SideThread( arg[2], arg[3], barrier, H, iter/2, 
							pAbsor, C, results, new SecureRandom(SecureRandom.getSeed(32)));
		thread2.start();
		thread1.join();	
		thread2.join();

		System.out.println("H,Absorption ratio,Transmission ratio,Reflection ratio");
		for (int h = 0; h < H; h++) {
			System.out.println((h + 1) + ", " + (float) results[h][1]/iter + ", " + (float) results[h][0]/iter + ", " + (float) results[h][2]/iter);
		}
	}
}