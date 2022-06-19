JAVA = /usr/lib/jvm/java-8-oracle/bin

run:
	cd src; $(JAVA)/java Client localhost:1099 $(name1) localhost:1099 $(name2)  

build:
	cd src; $(JAVA)/javac Simulation.java SimulationInterface.java State.java Parameters.java Client.java

registry:
	cd src; $(JAVA)/rmiregistry 1099

host:
	cd src; $(JAVA)/java -Djava.security.policy=java.policy Simulation localhost:1099/$(name)

clean: 
	cd src; rm *.class
