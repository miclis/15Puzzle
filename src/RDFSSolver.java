import java.util.HashSet;
import java.util.Set;


// Recursive Depth-First Search (rdfs).
// Queue is used to approach the depth-first search tree.

public class RDFSSolver extends PuzzleSolver{

    private static RDFSSolver instance = new RDFSSolver();
    protected Set<PuzzleState> stateSet = new HashSet<>();
    private static boolean flag = true;

    private RDFSSolver(){

    }

    public static RDFSSolver getInstance(){
        return instance;
    }

    void rdfs(PuzzleState state){
        if(!flag)
        {
            return;
        }
        if(!stateSet.contains(state))
        {
            stateSet.add(state);
        }
        // Save state
        if(state.isGoalState()){
            goal = state;
            return;
        }
        PuzzleState newState = null;   // new puzzle state for moves
        /** CASES */
        // Move left
        if(flag) {
            newState = PuzzleState.moveLeft(state);
            // Checks if exist and not already visited
            Call(newState);
        }
        // If goal state
        if (newState != null && newState.isGoalState()) {
            goal = newState;
            flag = false;
        }
        // Move down
        if (flag) {
            newState = PuzzleState.moveDown(state);

            // Checks if exist and not already visited
            Call(newState);
        }
        // If goal state
        if (newState != null && newState.isGoalState()) {
            goal = newState;
            flag = false;
        }
        // Move right
        if(flag) {
            newState = PuzzleState.moveRight(state);
            // Checks if exist and not already visited
            Call(newState);
        }
        // If goal state
        if (newState != null && newState.isGoalState()) {
            goal = newState;
            flag = false;
        }
        // Move up
        if (flag) {
            newState = PuzzleState.moveUp(state);
            // Checks if exist and not already visited
            Call(newState);
        }
        // If goal state
        if (newState != null && newState.isGoalState()) {
            goal = newState;
            flag = false;
        }
    }

    void Call(PuzzleState newState){
        // Checks if exist and not already visited
        if(newState != null && !stateSet.contains(newState) && !newState.isGoalState()){
            stateSet.add(newState);
            rdfs(newState);
        }


    }

    public String solve(Puzzle puzzle) {

        long startTime = System.currentTimeMillis();    // starts the timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        rdfs(state);
        time = System.currentTimeMillis() - startTime;  // calculate time
        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        memory = runtime.totalMemory() - runtime.freeMemory();
        return getSequence();   // returns sequence of moves
    }
}
