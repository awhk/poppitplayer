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
    public CoordData pointval;
    public int perfect;

    public Object clone() {
        PoppitProblem myobj = (PoppitProblem) (super.clone());
        myobj.game = (GameInterface) (game.clone());
        myobj.pointval = (CoordData) (pointval.clone());
        return myobj;
    }

    public void setup(final EvolutionState state, final Parameter base) {
        // very important, remember this
        super.setup(state, base);
        
        game = new GameInterface(8, 8);

        pointval = (CoordData) state.parameters.getInstanceForParameterEq(
                base.push(P_DATA), null, CoordData.class);
        pointval.setup(state, base.push(P_DATA));
    }

    public void evaluate(final EvolutionState state, final Individual ind,
            final int threadnum) {
        if (!ind.evaluated) // don't bother reevaluating
        {
            int max = game.getMaxScore();
            int popped = game.getScore();
            perfect = 0;
            
            ((GPIndividual)ind).trees[0].child.eval(
                    state,threadnum,pointval,stack,((GPIndividual)ind),this);

            // the fitness better be KozaFitness!
            KozaFitness f = ((KozaFitness) ind.fitness);
            f.setStandardizedFitness(state, (max - popped));
            f.hits = perfect;
            ind.evaluated = true;
        }
    }

}
