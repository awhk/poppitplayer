package poppitplayer.ecj.func;

import poppitplayer.ecj.PoppitData;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;
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
        
        children[0].eval(state, thread, input, stack, individual, problem);
        
        Coord popcoord = ((PoppitData) input).point;
        PoppitProblem myproblem = (PoppitProblem) problem;
        
        if (((PoppitProblem) problem).game.pop(popcoord)) {
            ((PoppitData) input).result = true;
            if(myproblem.penalty > 2){
                myproblem.penalty = myproblem.penalty - 2;
            }else{
            myproblem.penalty = 0;
            }
        } else {
            ((PoppitData) input).result = false;
            myproblem.penalty = myproblem.penalty + 2;
        }
        if (((PoppitProblem) problem).game.getScore() == ((PoppitProblem) problem).game.getMaxScore() ){
            myproblem.perfect++;
        }
    }

}
