# Bank Simulator

A program that simulates a bank with multiple tellers and collects statistical information of the bank's customers and tellers.

The bank consists of several tellers and customer line (queue). Each teller will help a customer and the customers will have pre-assigned transaction times. Each customer will be helped by a teller until their transaction time has elapsed. Additional customers will enter the bank during the simulation, if the line (queue) is full, the customer will leave the bank, otherwise, they will get in the line. The simulation will run for a specified number of time steps before terminating. Upon termination, the program will return a snapshot of the queues, customers, and tellers as well as print the statistical data it had collected from the simulation.

Learned how to use queues, priority queues, getters, and setters.

Provided code:
- BankSimulator main() function
- Customer main() function
- ServiceArea main() function
- Teller toString() and main() functions

How to run (assuming in BSIM directory):
- javac BankSimulator.java
- java BankSimulator

How to use:
- The program will prompt to ask for:
	- Simulation Time: How many time steps the simulation will run for
	- Number of Tellers: The amount of tellers available to help customers
	- New Customer Chance: The probability that a new customer will walk into the bank
	- Max Transaction Time: The maximum time that a customer could take to complete their transaction
	- Queue Limit: The maximum number of customers than can stand in line
	- Data Location: (USE RANDOM) Whether to use data from a file to control the simulation or to have a random simulation
- During the execution of the simulation, a summary of every time step will be printed.
- At the end of the simulation, a summary of the statistical data will be displayed.