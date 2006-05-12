package poppitplayer.ecj.func;

import ec.*;
import poppitplayer.ecj.PoppitData;
import poppitplayer.ecj.PoppitProblem;
import ec.gp.*;
import ec.util.*;

/* 
 * Add.java
 */

public class Div extends GPNode
    {
    public String toString() { return "/"; }

    public void checkConstraints(final EvolutionState state,
                                 final int tree,
                                 final GPIndividual typicalIndividual,
                                 final Parameter individualBase)
        {
        super.checkConstraints(state,tree,typicalIndividual,individualBase);
        if (children.length!=2)
            state.output.error("Incorrect number of children for node " + 
                               toStringForError() + " at " +
                               individualBase);
        }

    public void eval(final EvolutionState state,
                     final int thread,
                     final GPData input,
                     final ADFStack stack,
                     final GPIndividual individual,
                     final Problem problem)
        {
        int result;
        PoppitData pd = ((PoppitData)(input));

        children[0].eval(state,thread,input,stack,individual,problem);
        result = pd.number;

        children[1].eval(state,thread,input,stack,individual,problem);
        if (pd.number == 0){
            pd.number = 1;
            ((PoppitProblem) problem).penalty += 999;
        }
        pd.number = result / pd.number;
        }
    }