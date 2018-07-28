Sai K. Padmanaban

8 Puzzle Solver with A* using 2 different heuristics. 

Description
----
This program solves any solvable 8-puzzle configuration. 

The goal is assumed to be: 

0 1 2

3 4 5

6 7 8

where 0 is the empty tile. 

How to compile and run the program:
----
1. Compile the program using the command 'javac Solver.java' within the directory. Once it compiles, type 'java Solver' and the program should run as expected.

2. Alternatively, the program can be run on any Java IDE. 
----
The following menu is displayed initially.

Initial state configuration:

1.Random configuration

2.Enter the configuration

3.Read from file test.txt 

----

Choose 1 to randomly retrive a solvable 8-puzzle configuration. This will output a step-by-step solution. 

Choose 2 to enter the configuration manually. Enter the initial configuration as a sequence of number with no spaces. (ex. 350621748). This will output a step-by-step solution. 

Choose 3 to read multiple sample cases from test.txt file and output average search costs and average times. Make sure to create file in the same directory and enter sequence of numbers separated by new line. 
