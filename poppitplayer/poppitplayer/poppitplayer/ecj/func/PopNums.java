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

public class PopNums extends GPNode {

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

        Coord popcoord;
        popcoord = PoppitProblem.convertIntCoord(((PoppitData) input).number,
                state, problem);

        PoppitProblem myproblem = (PoppitProblem) problem;

        if (myproblem.summarize) {
            // System.out.println(myproblem.game);
            System.out.print("Trying to pop " + popcoord + "...");
        }

        if (((PoppitProblem) problem).game.pop(popcoord)) {
            ((PoppitData) input).result = true;
            if (myproblem.summarize) {
                System.out.println("succeeded.");
                System.out.println(myproblem.game);
            }
//            if (myproblem.penalty > 5) {
//                myproblem.penalty = myproblem.penalty - 5;
//            } else {
//                myproblem.penalty = 0;
//            }
        } else {
            ((PoppitData) input).result = false;
            if (myproblem.summarize) {
                System.out.println("failed.");
                // System.out.println(myproblem.game);
            }
//            myproblem.penalty = myproblem.penalty + 5;
        }
        if (((PoppitProblem) problem).game.getScore() == ((PoppitProblem) problem).game
                .getMaxScore()) {
            myproblem.perfect++;
        }
    }

}
