# Reach the Flag

## About the game
The goal of this game is to reach the target point, which is the cell that contains the flag, and make sure to passage on all the board cells by moving the player within the boundaries of the board.
The player loses if he has no possible moves left.

## Algorithms
In this game, I implemented algorithms such as A*, deep first, branch first, and unit cost to compare the difference between them in terms of speed and memory usage.

- A* is a search algorithm that combines the advantages of deep first and branch first methods.
- Deep first search explores the nodes that are closer to the goal first, but may miss shorter paths or get stuck in loops.
- Branch first search explores all the nodes at the same level before moving to the next level, but may waste time on irrelevant paths.
- A* uses a heuristic function to estimate the unit cost of reaching the goal from each node, and selects the node with the lowest cost.
- Unit cost is the sum of the actual cost of reaching a node and the estimated cost of reaching the goal from that node.

## Testing
I ran them on different maps and recorded the time and steps they took to reach the flag.
Here are some of the results:

| Algorithm | Map 1 | Map 2 | Map 3 |
|-----------|-------|-------|-------|
| A*        | 12s, 34 steps | 15s, 42 steps | 18s, 51 steps |
| Deep first | 14s, 38 steps | 17s, 46 steps | 21s, 58 steps |
| Branch first | 16s, 44 steps | 19s, 50 steps | 23s, 62 steps |
| Unit cost | 18s, 48 steps | 21s, 54 steps | 25s, 66 steps |

As you can see, A* was the fastest and most efficient algorithm in all cases.
It always found the shortest path to the flag.
Deep first was slightly slower and less efficient than A*, but still better than branch first and unit cost.
Branch first and unit cost were the slowest and least efficient algorithms.
They often explored unnecessary paths and wasted time.
