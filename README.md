# Closest-pair-algorithm
Closest pair algorithm

Written in Java.
Uncomment the code between the horizontal dotted lines for corresponding solutions in Java file.
 
Change the length variable to change the number of input objects 
All solutions are written in seperate functions. To execute each uncomment the correponding section.

Folders -

Raw Data - Inputs used for testing all the algorithms

Java Code - Code used for the algos

Graphs - Graphs with different parameters to measure the efficiency of the algorithm

Report - Detailed information about graphs and observations.

1) Implemented a problem generator for the closest pair problem.
    On a domain of 100 by 100, generated n points with different distributions: 
    
    a) random, 
    b) uniform (hexagonal so that all nearby distances are the same), 
    c) mixed, 95% points are laid out in the hexagonal pattern, the rest are randomly placed).

2) Implemented the simple n times n algorithm for closest pair

3) Implemented divide and conquer Slow algorithm: sort by x first, then in the solution construction computed the best distance as min or left and right, 
    then extract points in the slice that lie within +/- best distance, then used the naive algorithm over the points in the slice to determine the distance to be returned. 
    Note that we expect this algorithm to be slow.

4) Implemented divide and conquer Fast algorithm: as above but used this technique: sorted the strip by y then linear scan looking back 6 points

5) Did a randomized study on the three algorithms vs. three types of problems. Used a semi-log plot to display the results and a doubling scheme to increase problem sizes. 
    Assumed the problem size is a power of two. Made sure algorithms are run for as larger problem size as possible. Each point on the graph should be the result of at least 10 distinct runs (when the problems are randomized). 
    Fitted lines to the results and provided a table of the slopes.


 
