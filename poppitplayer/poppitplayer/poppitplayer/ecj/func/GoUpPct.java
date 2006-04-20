package poppitplayer.ecj.func;

import ec.EvolutionState;
import ec.Problem;
import ec.gp.ADFStack;
import ec.gp.GPData;
import ec.gp.GPIndividual;
import ec.gp.GPNode;
import ec.util.Parameter;
import poppitplayer.ecj.PoppitData;
import simpoppit.gameboard.Coord;

public class GoUpPct extends GPNode {
    
    private int pct;
    
    public String toString() {
        return "GoUpPct:" + pct;
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
        
        pct = state.random[thread].nextInt(101);
        int x = ((PoppitData) input).point.getX();
        int y = ((PoppitData) input).point.getY();
        int newY = y - (Math.round(((float)pct/100) * y));
        if (newY < 0) newY = 0;
        Coord newPoint = new Coord(x, newY);
        
        ((PoppitData) input).point = newPoint;
    }

}
