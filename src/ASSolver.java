import java.util.*;

public class ASSolver extends PuzzleSolver {

    // For comparing manhattan distance
    private Comparator<PuzzleState> statePriorityComparator = Comparator.comparingInt(PuzzleState::getManDistance);

    private static ASSolver instance = new ASSolver();
    private PriorityQueue<PuzzleState> stateQueue = new PriorityQueue<>(statePriorityComparator);
    private Set<PuzzleState> stateSet = new HashSet<>();
    private int statePathLength = 0;

    public ASSolver() {
    }

    public static ASSolver getInstance() {
        return instance;
    }

    /** COST CALCULATION */
    // Calculates the cost based on manhattan distance to a goal state
    private int manhattan(PuzzleState s){

        int manhattanDistanceSum = 0;
        int[][] sArray = s.getPuzzleArray();

        for (int x = 0; x < s.getSize(); x++){
            for (int y = 0; y < s.getSize(); y++){
                int value = sArray[x][y];
                if (value != 0){    // ommits 0 tile
                    int goalX = (value - 1) / s.getSize();
                    int goalY = (value - 1) % s.getSize();
                    int dx = x - goalX;
                    int dy = y - goalY;
                    manhattanDistanceSum += Math.abs(dx) + Math.abs(dy);
                }
            }
        }
        return manhattanDistanceSum;
    }
    // Calculates the cost based on number of misplaced tiles
    private int heuristic(PuzzleState s){

        int pathLength = 0;
        int[][] sArray = s.getPuzzleArray();

        for (int i = 0; i < s.getSize(); i++){
            for (int j = 0; j < s.getSize(); j++){
                if (sArray[i][j] != (i * s.getSize() + j)){
                    pathLength += 1;
                }
            }
        }
        return pathLength;
    }

    private void ass(PuzzleState state){

        // Clears memory
        stateQueue.clear();
        stateSet.clear();

        // Adds input state
        stateQueue.add(state);
        stateSet.add(state);

        PuzzleState newState;   // new puzzle state for moves

        while (!stateQueue.isEmpty()){
             state = stateQueue.poll();
                    // If goal state
                    if(state.isGoalState()){
                        goal = state;
                        break;
                    }
            // Memory limit check
            if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){
                break;
            }

            /** CASES */
            // Move up
            newState = PuzzleState.moveUp(state);
            // Checks if exist and not already visited
            if (newState != null && !stateSet.contains(newState)) {
                newState.setManDistance(manhattan(newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move down
            newState = PuzzleState.moveDown(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setManDistance(manhattan(newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move left
            newState = PuzzleState.moveLeft(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setManDistance(manhattan(newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move right
            newState = PuzzleState.moveRight(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setManDistance(manhattan(newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

        }
    }

    public String solve(Puzzle puzzle) {

        long startTime = System.currentTimeMillis();    // starts timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        ass(state); // starts bfs search

        time = System.currentTimeMillis() - startTime;  // calculates time

        return getSequence();   // returns sequence of moves
    }
}
