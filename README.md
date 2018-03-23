# CS 205 Artificial Intelligence - Winter 2018
## Project 1: 8 Puzzle
"puzzleGenerator" is used to generate sliding puzzles. It can generate an M-by-N solvable puzzle. We can set the diameter to determine a random number of movements, which is not larger than the diameter.

There are 2 branches, which were built by different logic.
1. master: Only record the `previous step`. It can prevent duplicated states stored in the queue. Use less memory.
2. hash: Use a `HashSet` to store all states which have ever emerged in the process. Use more memory, but can used to prove whether a puzzle is solvable.

## Project 2: Feature selection
1. Forward Selection
2. Backward Elimination
3. Always remove one
4. Resampling
5. Recursive go through all features
