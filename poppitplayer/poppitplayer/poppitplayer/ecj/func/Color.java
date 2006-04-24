package poppitplayer.ecj.func;

import poppitplayer.ecj.PoppitData;
import poppitplayer.ecj.PoppitProblem;
import simpoppit.gameboard.Coord;
import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;

public class Color extends GPNode {

    public String toString() {
        return "color";
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

        Coord coord1 = PoppitProblem.convertIntCoord(((PoppitData) input).number, state);
        ((PoppitData) input).number = ((PoppitProblem) problem).game.getColor(coord1);

    }

}
