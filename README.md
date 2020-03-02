# OOAD Project 3

##### Maura Winstanley, Kara Wallace, Abbigail Nicholson

**To Run:** `javac *.java ` `java Main.java`
**to run tests** mvn tests

**Language and Environment:** Java, IntelliJ

**Description/ Assumptions:**

Store is where the majority of the work is being done. We make use of the factory pattern by creating two factories, one to create 12 customers and one to create 24 cars. To add options to the cars we use a decorator pattern that adds any of the three features to the car by giving a description and the rate added on. We use a strategy pattern for the Customer class so that each type of customer will have different operations for getting the number of nights rented and the number of cars rented. In a single day the store will rent out cars, return cars, and return information at the end of the day. 
Assumptions: if there were not enough cars for the customer to rent all of the cars that they chose - then they would not rent the cars that day. 
Note: the text output is in the output file for organizational purposes.
Note: UML diagram is also in separate file. 
Note: created a new repo, so git commits are different: old git repo - https://github.com/maurawinstanley/OOAD3.git






