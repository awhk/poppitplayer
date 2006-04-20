package poppitplayer.ecj;

import ec.EvolutionState;
import ec.Individual;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Parameter;
import simpoppit.gameboard.GameInterface;

public class PoppitProblem extends GPProblem implements SimpleProblemForm {

    public GameInterface game;
    public PoppitData gamedata;
    public int perfect;
    public int penalty;

    public Object clone() {
        PoppitProblem myobj = (PoppitProblem) (super.clone());
        myobj.game = (GameInterface) (game.clone());
        myobj.gamedata = (PoppitData) (gamedata.clone());
        return myobj;
    }

    public void setup(final EvolutionState state, final Parameter base) {
        // very important, remember this
        super.setup(state, base);
        
        game = new GameInterface(6, 6);
        game.loadGame("c:\\simpoppit.sav");

        gamedata = (PoppitData) state.parameters.getInstanceForParameterEq(
                base.push(P_DATA), null, PoppitData.class);
        gamedata.setup(state, base.push(P_DATA));
    }

    public void evaluate(final EvolutionState state, final Individual ind,
            final int threadnum) {
        if (!ind.evaluated) // don't bother reevaluating
        {
            perfect = 0;
            
            ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,gamedata,stack,((GPIndividual)ind),this);
            
            int max = game.getMaxScore();
            int popped = game.getScore();

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness) ind.fitness);
            f.setStandardizedFitness(state, (max - popped));
            f.hits = perfect;
            ind.evaluated = true;
        }
    }
    
    public void describe(final Individual ind, 
            final EvolutionState state, 
            final int threadnum, final int log,
            final int verbosity)

{
state.output.println("\n\nBest Individual\n",
                verbosity,log);

// evaluate the individual
((GPIndividual)ind).trees[0].child.eval(
state,threadnum,gamedata,stack,((GPIndividual)ind),this);

// print out the map
state.output.println(game.toString(),verbosity,log);

}

}
