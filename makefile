JAVA = /usr/lib/jvm/java-8-oracle/bin

run:
	cd src; $(JAVA)/java Client $(addr):$(port) $(name1) $(addr):$(port) $(name2)  

build:
	cd src; $(JAVA)/javac Simulation.java SimulationInterface.java State.java Parameters.java Client.java

registry:
	cd src; $(JAVA)/rmiregistry $(port)

host:
	cd src; $(JAVA)/java -Djava.security.policy=java.policy Simulation $(addr):$(port)/$(name)

clean: 
	cd src; rm *.class
