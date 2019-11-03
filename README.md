# Problem Statement

I own a parking lot that can hold up to 'n' cars at any given point in time. Each slot is given a number starting at 1 increasing with increasing distance from the entry point in steps of one. I want to create an automated ticketing system that allows my customers to use my parking lot without human intervention.
When a car enters my parking lot, I want to have a ticket issued to the driver. The ticket issuing process includes us documenting the registration number (number plate) and the colour of the car and allocating an available parking slot to the car before actually handing over a ticket to the driver (we assume that our customers are nice enough to always park in the slots allocated to them). The customer should be allocated a parking slot which is nearest to the entry. At the exit the customer returns the ticket which then marks the slot they were using as being available.
Due to government regulation, the system should provide me with the ability to find
out:

•	Registration numbers of all cars of a particular colour.
•	Slot number in which a car with a given registration number is parked.
•	lot numbers of all slots where a car of a particular colour is parked.

# Project Requirement

•	Latest version of JDK.
•	Latest version of maven.
# Assumptions

•	Parking Lot App is not multithreading
•       Parking Lot App can have vehicle name and color as any string
•       Parking Lot App has capacity of creating 1000 Slots 
•	Parking Area’s Slot size is fixed for all type of vehicles
•	Vehicle Color is case insensitive
•	Parking Lot can support multiple parking locations.
•	We can not park one vehicle in two places at time.
•	Vehicle can be un parked only by providing slot number where its parked.

# Limitations

•	Current implementation does not support vehicle size but can be easily extended to add that support.
•       Parking Lot do not support multiple parking locations .
•	Current implementation does not support Park Location managementBut can be easily extended to add that support.
•	Current implementation does not support Parking Token and Billing Management but can be extended easily.
•	Current implementation does not support multitenancy but can be extended easily


# Build Instruction

Go to parking_lot/bin folder and execute “setup” command.

# Running the project

•	Run from CLI
    Go to parking_lot/bin folder and execute “parking_lot” command
•	Run using input file
•	Go to parking_lot/bin folder and execute “parking_lot <command file path>” command

#Sample Run
•	To install all dependencies, compile and run tests:
$ bin/setup
•	To run the program and launch the shell:
     $ bin/parking_lot
Assuming a parking lot with 6 slots, the following commands should be run in sequence by typing them in at a prompt and should produce output as described below the command. Note that exit terminates the process and returns control to the shell.
$ create_parking_lot 6
Created a parking lot with 6 slots

$ park KA-01-HH-1234 White
Allocated slot number: 1

$ park KA-01-HH-9999 White
Allocated slot number: 2

$ park KA-01-BB-0001 Black
Allocated slot number: 3

$ park KA-01-HH-7777 Red
Allocated slot number: 4

$ park KA-01-HH-2701 Blue
Allocated slot number: 5

$ park KA-01-HH-3141 Black
Allocated slot number: 6

$ leave 4
Slot number 4 is free

$ status
Slot No.  Registration No
1.	1          KA-01-HH-1234
2.
3.	2          KA-01-HH-9999
4.
5.	3          KA-01-BB-0001
6.
5.	5          KA-01-HH-2701
6.
7.	6          KA-01-HH-3141
8.
$ park KA-01-P-333 White
Allocated slot number: 4

$ park DL-12-AA-9999 White
Sorry, parking lot is full

$ registration_numbers_for_cars_with_colour White
KA-01-HH-1234, KA-01-HH-9999, KA-01-P-333

$ slot_numbers_for_cars_with_colour White
1, 2, 4

$ slot_number_for_registration_number KA-01-HH-3141
6

$ slot_number_for_registration_number MH-04-AY-1111
Not found
$ exit
