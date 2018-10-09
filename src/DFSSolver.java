import java.util.HashSet;
import java.util.Set;


// Depth-First Search (dfs).
// Queue is used to approach the depth-first search tree.

public class DFSSolver extends PuzzleSolver{

    private static DFSSolver instance = new DFSSolver();
    protected Set<PuzzleState> stateSet = new HashSet<PuzzleState>();
    public static final int MAX_DEPTH = 30;

    private DFSSolver(){

    }

    public static DFSSolver getInstance(){
        return instance;
    }

    void iterativeDeepening(PuzzleState state, int depthLimit){

        stateSet.clear();   // clears memory
        stateSet.add(state);    // adds this state to state set

        // Checks states until depth limit is reached
        for(int i = 1; i <= depthLimit; i++){
            dfs(state, i);  // depth-first search at this level

            // If goal state
            if(goal != null)
                return;

            // Memory check
            if (Runtime.getRuntime().freeMemory() < (.0001) * Runtime.getRuntime().totalMemory()){
                return;
            }
        }
    }

    void dfs(PuzzleState state,int depth){

        // End recursion - depth
        if(depth < 0)
            return;

        // Save state
        if(state.isGoalState()){
            goal = state;
        }

        // If goal state
        if(goal != null)
            return;

        PuzzleState newState;   // new puzzle state for moves
        /**  CASES */
        // Move up
        newState = PuzzleState.moveUp(state);
        // Checks if exist and not already visited
        Call(newState, depth);

        // Move down
        newState = PuzzleState.moveDown(state);
        // Checks if exist and not already visited
        Call(newState, depth);

        // Move left
        newState = PuzzleState.moveLeft(state);
        // Checks if exist and not already visited
        Call(newState, depth);

        // Move right
        newState = PuzzleState.moveRight(state);
        // Checks if exist and not already visited
        Call(newState, depth);

    }

    void Call(PuzzleState newState, int depth){

        // Checks if exist and not already visited
        if(newState != null && !stateSet.contains(newState)){
            stateSet.add(newState);
            dfs(newState, depth - 1);
            // If goal state
            if(goal != null)
                return;

            stateSet.remove(newState);
        }
    }

    public String solve(Puzzle puzzle) {

        long startTime = System.currentTimeMillis();    // starts the timer
        goal = null;    // goal state is not found at the beginning (null)

        PuzzleState state = new PuzzleState(puzzle);    // creates state to begin with
        iterativeDeepening(state, MAX_DEPTH);   // starts dfs search

        time = System.currentTimeMillis() - startTime;  // calculate time

        return getSequence();   // returns sequence of moves
    }
}
