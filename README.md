How to run

1. Build code:
javac Simulation.java SimulationInterface.java State.java Parameters.java Client.java
2. Run rmi registry:
java_path/bin/rmiregistry 1099
3. Run two servers (e.g separate terminals)
java -Djava.security.policy=java.policy Simulation localhost:1099/hostname1

java -Djava.security.policy=java.policy Simulation localhost:1099/hostname2

4. Run client in separate terminal (it will execute our functionality)

java Client localhost:1099 hostname1 localhost:1099 hostname2   
