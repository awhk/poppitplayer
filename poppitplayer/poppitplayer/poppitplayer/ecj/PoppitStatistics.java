package poppitplayer.ecj;
/*
Copyright 2006 by Sean Luke
Licensed under the Academic Free License version 3.0
See the file "LICENSE" for more information
*/


import ec.*;
import ec.gp.koza.*;
import ec.util.*;
import ec.simple.*;

/* 
 * LawnmowerStatistics.java
 * 
 * Created: Fri Nov  5 16:03:44 1999
 * By: Sean Luke
 */

/**
 * @author Sean Luke
 * @version 1.0 
 */

public class PoppitStatistics extends KozaStatistics
    {
    
//    private static float bestFitness = Float.POSITIVE_INFINITY;
//    private static Individual bestInd;
    
    public void finalStatistics(final EvolutionState state, final int result)
        {
        // print out the other statistics
        super.finalStatistics(state,result);

        // we have only one population, so this is kosher
        ((SimpleProblemForm)(state.evaluator.p_problem.clone())).describe(
                best_of_run[0], state, 0, statisticslog,Output.V_NO_GENERAL);
        }
//    public void postEvaluationStatistics(final EvolutionState state)
//    {
//        // call the super's method to do the actual statistics
//        super.postEvaluationStatistics(state);
//
//        // if you extend SimpleStatistics, you have the best_of_run vector
//        // available.
//        if( ((KozaFitness)best_of_run[0].fitness).rawFitness() < bestFitness){
//            System.out.println("Found new best individual (old/new): " + bestFitness + "/" + ((KozaFitness)best_of_run[0].fitness).rawFitness());
//            bestFitness = ((KozaFitness)best_of_run[0].fitness).rawFitness();
//            bestInd = (Individual)best_of_run[0].clone();
//        }
//
//        
//    }

    }
