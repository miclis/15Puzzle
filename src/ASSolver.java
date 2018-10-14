import java.util.*;
/**
* A* combines the advantages of Best-first Search and Uniform Cost Search.
* F(n) = g(n) + h(n)
* h(n) - an estimated distance between any random vertex n and target vertex
* g(n) - the actual distance between the start point and any vertex n
* */
public class ASSolver extends PuzzleSolver {

    // For comparing manhattan distance
    private Comparator<PuzzleState> statePriorityComparator = Comparator.comparingInt(PuzzleState::getValue);

    private static ASSolver instance = new ASSolver();
    private PriorityQueue<PuzzleState> stateQueue = new PriorityQueue<>(statePriorityComparator);
    private Set<PuzzleState> stateSet = new HashSet<>();

    public ASSolver() {
    }

    protected int calculateValue (int heuristicID, PuzzleState newState){
        int value;
        switch (heuristicID) {
            case 1:
                value = newState.heuristic()+newState.getLevel();
                break;
            case 2:
                value = newState.manhattan()+newState.getLevel();
                break;
            default:
                value = 0;
        }
        return value;
    }

    public static ASSolver getInstance() {
        return instance;
    }

    private void ass(PuzzleState state, int heuristicID){

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
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move down
            newState = PuzzleState.moveDown(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move left
            newState = PuzzleState.moveLeft(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setValue(calculateValue(heuristicID, newState));

                stateSet.add(newState);
                stateQueue.add(newState);
            }

            // Move right
            newState = PuzzleState.moveRight(state);
            if (newState != null && !stateSet.contains(newState)) {
                newState.setValue(calculateValue(heuristicID, newState));
                stateSet.add(newState);
                stateQueue.add(newState);
            }
        }
    }

    public String solve(Puzzle puzzle, int heuristicID) {

        long startTime = System.currentTimeMillis();    // starts timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        ass(state, heuristicID); // starts ass search

        time = System.currentTimeMillis() - startTime;  // calculates time
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        return getSequence();   // returns sequence of moves
    }
}
