package poppitplayer.ecj;

import ec.EvolutionState;
import ec.Individual;
import ec.Problem;
import ec.gp.GPIndividual;
import ec.gp.GPProblem;
import ec.gp.koza.KozaFitness;
import ec.simple.SimpleProblemForm;
import ec.util.Output;
import ec.util.Parameter;
import ec.simple.SimpleEvolutionState;
import ec.Evolve;
import simpoppit.gameboard.GameInterface;
import simpoppit.gameboard.Coord;

public class PoppitProblem extends GPProblem implements SimpleProblemForm {

    public GameInterface game;

    public PoppitData gamedata;

    public int perfect;
    
    public boolean summarize = false;

    public int penalty;
    
    private static int x;
    private static int y;
    
    public static Coord convertIntCoord(int index, final EvolutionState state, final Problem problem){
        if (index <= 0){
            //state.output.warning("Index can't be zero or less!  Setting to 1.");
            index = 1;
            ((PoppitProblem) problem).penalty += 99;
        }
        if (index > (x*y)){
            //state.output.warning("Index can't exceed board size!  Setting to maximum.");
            index = (x*y);
            ((PoppitProblem) problem).penalty += 99;
        }
        int myIndex = index;
        int myY = 0;
        int myX = 0;
        while (myIndex > x){
            myIndex -= x;
            myY++;
        }
        myX = myIndex - 1;
        //System.out.println("For " + index + " computed coord (" + myX + "," + myY + ")");
        return new Coord(myX, myY);
    }

    public Object clone() {
        PoppitProblem myobj = (PoppitProblem) (super.clone());
        myobj.game = (GameInterface) (game.clone());
        myobj.gamedata = (PoppitData) this.gamedata.clone();
        return myobj;
    }

    public void setup(final EvolutionState state, final Parameter base) {
        // very important, remember this
        super.setup(state, base);

        x = state.parameters.getInt(base.push("x"), null,
                1);
        y = state.parameters.getInt(base.push("y"), null, 1);
        game = new GameInterface(x, y);
        game.loadGame("c:\\simpoppit.sav");

        gamedata = (PoppitData) state.parameters.getInstanceForParameterEq(base
                .push(P_DATA), null, PoppitData.class);
        gamedata.setup(state, base.push(P_DATA));
    }

    public void evaluate(final EvolutionState state, final Individual ind,
            final int threadnum) {
        if (!ind.evaluated) // don't bother reevaluating
        {
            perfect = 0;
            penalty = 0;
            game.fastRestartGame();
            int loopCount = 0;
            //GameInterface temp = (GameInterface) game.clone();
            //game.restartGame();

//            while ((!(game.isGameOver())) && loopCount < (4 * game.getMaxScore())) {
//                ((GPIndividual) ind).trees[0].child.eval(state, threadnum,
//                        gamedata, stack, ((GPIndividual) ind), this);
//                loopCount++;
//            }
            ((GPIndividual) ind).trees[0].child.eval(state, threadnum,
                  gamedata, stack, ((GPIndividual) ind), this);

            int max = game.getMaxScore();
            int popped = game.getScore();
            int fitness = ((max - popped) + penalty);
            if (!game.isGameOver()){
                fitness += max;
            }

            // the fitness better be KozaFitness!
            // System.out.println("Setting fitness to " + (max - popped));
            KozaFitness f = ((KozaFitness) ind.fitness);
            f.setStandardizedFitness(state, fitness);
            f.hits = perfect;
//            if (fitness <= 3 ){
//                System.out.println(game.toString());
//                state.output.println(game.toString(), 3000, 2);
//                ind.printIndividualForHumans(state, 2, Output.V_NO_GENERAL);
//                game.replayGameGUI();
//            }
            // System.out.println("Fitness is " + ind.fitness.fitnessToStringForHumans());
            ind.evaluated = true;
            // if ((max - popped) == 0) {
            // System.out.println(temp.toString());
            // System.out.println("Fitness is " + (max - popped));
            // System.out.println(game.toString());
            // game.replayGameGUI();
            // }
            //game.restartGame();
            //stack.reset();
        }
    }

    public void describe(final Individual ind, final EvolutionState state,
            final int threadnum, final int log, final int verbosity)

    {
        state.output.println("\n\nBest Individual:\n", verbosity, log);

        state.output.println(game.toString(), verbosity, log);
        //state.output.println(game.startBoard.toString(), verbosity, log);
        
        game.fastRestartGame();
//        game.resetGame();
//        state.output.println(game.toString(), verbosity, log);
        summarize = false;

        int loopCount = 0;
        // evaluate the individual
//        while ((!(game.isGameOver())) && loopCount < (4 * game.getMaxScore())) {
//            ((GPIndividual) ind).trees[0].child.eval(state, threadnum,
//                    gamedata, stack, ((GPIndividual) ind), this);
//            loopCount++;
//        }
        ((GPIndividual) ind).trees[0].child.eval(state, threadnum,
              gamedata, stack, ((GPIndividual) ind), this);

        // print the fitness
        state.output.println("Fitness is: " + ind.fitness.fitnessToStringForHumans(), verbosity, log);
        state.output.println("Individual is: ", verbosity, log);
        ind.printIndividualForHumans(state, log, Output.V_NO_GENERAL);
        // print out the game
        state.output.println(game.toString(), verbosity, log);

        game.replayGameGUI();

        try {
            Thread.sleep(10000);
        } catch (Exception e) {
            System.out.println("Failed to sleep - " + e);
        }

    }
    
    public static void main(String[] args) {
        SimpleEvolutionState state = (SimpleEvolutionState)(Evolve.initialize(Evolve.loadParameterDatabase( new String[] {"-file", "C:\\Documents and Settings\\datacomm\\workspace\\poppitplayer\\poppitplayer\\ecj\\ecjplayernoadf.params"}), 0));
        state.parameters.setProperty("generations", "25");
        state.startFresh();
        while(state.evolve() == EvolutionState.R_NOTDONE){}
    }

}
