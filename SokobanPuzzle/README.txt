This Project is a solver of a Sokoban Puzzle. It uses the A* algorithm with two different heuristics to solve the problem. 
It was used to test between 15 different puzzles with different difficulties the two following heuristics in terms of speed 
performance and storage needed. 

Input: 
The program gets as input a path were a txt file with the puzzle is located. Then you can give as input either the number '1' 
or number '2' to solve the problem with the first or with the second heuristic.

Heuristics:
If you give as input the number '1' the puzzle is going to be solved with the heuristic that calculates the Manhattan Distance 
between the boxes and the targets plus the number of steps already covered by the player. The first heuristic performs better 
in terms of time and space than the second one. 

If you give as input the number '2' the puzzle is going to be solved with the heuristic that counts the number of misplaced 
boxes plus the number of steps already covered by the player. The performance of the second heuristic is worse than the first 
heuristic.

File Format:
An input file would have the following format:

7
####
# .#
#  ###
#*@  #
# $  #
#    #
######

The number 7 represents the number of rows. The character '#' represents a wall. The '.' represents a target position for a box. 
The '@' is our player. The player has the character '+' when is standing on top of a target position. The character '$' is a box. 
When the box is placed in a target position, the character will be '*'. The space ' ' represents an empty cell.

Output:
In the case that the puzzle is unsolvable, then the message "No solution found" will be displayed. 

In the case that the puzzle is solvable the algorithm is going to print the time needed to find the solution, the number of nodes 
created, and the steps from the beginning until the solution. s going to print every state with the movement made to find the 
solution for the puzzle. The letters 'r', 'l', 'd', 'u' represents the movement of the player to the left, right, down, or up 
respectively. If the letters are in capital 'R', 'L', 'D', 'U' represent the movement of the player to the left, right, down, or up 
respectively but also, that the player pushed a box on that direction.


Note: This algorithm has its limitations. Even finding the optimal heuristic at some puzzles with higher difficulty than the one 
tested is going to run out of memory and throw a StackOverflowError.  

Author: Marios Pafitis (mpafit02)
