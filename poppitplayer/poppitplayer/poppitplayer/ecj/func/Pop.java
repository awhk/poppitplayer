package poppitplayer.ecj.func;

import poppitplayer.ecj.BooleanData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;
import poppitplayer.ecj.CoordData;
import poppitplayer.ecj.PoppitProblem;
import simpoppit.gameboard.Coord;

public class Pop extends GPNode {

    public String toString() {
        return "pop";
    }

    public void checkConstraints(final EvolutionState state, final int tree,
            final GPIndividual typicalIndividual, final Parameter individualBase) {
        super.checkConstraints(state, tree, typicalIndividual, individualBase);
        if (children.length != 1)
            state.output.error("Incorrect number of children for node "
                    + toStringForError() + " at " + individualBase);
    }

    public void eval(final EvolutionState state, final int thread,
            final GPData input, final ADFStack stack,
            final GPIndividual individual, final Problem problem) {
        
        Coord popcoord = ((CoordData) input).point;
        PoppitProblem myproblem = (PoppitProblem) problem;
        
        if (((PoppitProblem) problem).game.pop(popcoord)) {
            ((BooleanData) input).result = true;
        } else {
            ((BooleanData) input).result = false;
        }
        if (((PoppitProblem) problem).game.getScore() == ((PoppitProblem) problem).game.getMaxScore() ){
            myproblem.perfect++;
        }
    }

}
