import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SimulationInterface extends Remote {
    Parameters simulate_neutrons(Parameters parameters) throws RemoteException;
}
