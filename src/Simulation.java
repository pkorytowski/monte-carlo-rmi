import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;

import static java.lang.Math.cos;
import static java.lang.Math.log;

//import java.net.InetAddress;

import static java.lang.Math.PI;

public class Simulation extends UnicastRemoteObject implements SimulationInterface {

    public Simulation() throws RemoteException {
        super();
    }

    private int simulate_single_neutron(double c, SecureRandom generator, double pAbsor, int h) throws RemoteException {
        double x = 0;
        double direction = 0;

        while (true) {
            x += -1/ c * log(generator.nextDouble()) * cos(direction);
            if (x < 0) return State.REFLECTED.getNum();
            if (x >= h) return State.TRANSMITTED.getNum();
            if (generator.nextDouble() < pAbsor) return State.ABSORBED.getNum();
            else direction = generator.nextDouble() * PI;
        }
    }

    @Override
    public Parameters simulate_neutrons(Parameters parameters) throws RemoteException {

        
        long localIterations = parameters.getLocalIterations();
        int H = parameters.getH();
        for (int h = 1; h<=H; h++) {
            int[] local_results = {0, 0, 0};
            while (localIterations > 0) {
                local_results[simulate_single_neutron(parameters.getC(), parameters.generator, parameters.getPAbsor(), h)]++;
                localIterations--;
            }

            for (int i = 0; i < 3; i++) {
                parameters.results[h-1][i] = local_results[i];
            }
        }
        return parameters;
    }

    public static void main( String[] args) throws Exception {
		if( args.length < 1 )
		{              
			System.out.println("specify servername" );
			return;
		}
		//String hostName = InetAddress.getLocalHost().getHostName();
		if( System.getSecurityManager() == null ){
            System.setSecurityManager( new SecurityManager() );
        }	
//		Naming.rebind( "//" + hostName + "/" + args[0],
		Naming.rebind( "//" + args[0],
								   new Simulation() );
	}

}
